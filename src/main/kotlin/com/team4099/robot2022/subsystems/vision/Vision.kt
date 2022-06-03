import com.team4099.lib.units.derived.inRadians
import com.team4099.robot2022.config.constants.VisionConstants
import com.team4099.robot2022.subsystems.vision.VisionIO
import edu.wpi.first.math.MathUtil
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Transform2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.RobotState
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger
import java.util.function.Supplier


class Vision(io: VisionIO) : SubsystemBase() {


  private val io: VisionIO
  private val inputs: VisionIO.VisionIOInputs = VisionIO.VisionIOInputs()
  private var lastCaptureTimestamp = 0.0
  private var modeSupplier: Supplier<VisionConstants.VisionLedMode>? = null
  private var climbModeSupplier: Supplier<Boolean>? = null
  private var robotState: RobotState? = null
  private var lastTranslationsTimestamp = 0.0
  private var lastTranslations: List<Translation2d> = ArrayList()
  private var pipeline = 0
  private var ledsOn = false
  private var forceLeds = false
  private var autoEnabled = false
  private val targetGraceTimer: Timer = Timer()
  fun setSuppliers(
    modeSupplier: Supplier<VisionConstants.VisionLedMode>?,
    climbModeSupplier: Supplier<Boolean>?
  ) {
    this.modeSupplier = modeSupplier
    this.climbModeSupplier = climbModeSupplier
  }

  fun setRobotState(robotState: RobotState?) {
    this.robotState = robotState
  }

  /** Use to enable LEDs continuously while override is "Auto"  */
  fun setForceLeds(on: Boolean) {
    forceLeds = on
  }

  /** Sets the current pipeline number.  */
  fun setPipeline(pipeline: Int) {
    this.pipeline = pipeline
  }

  fun setAutoEnabled(enabled: Boolean) {
    autoEnabled = enabled
  }

  val simpleTargetValid: Boolean
    get() = inputs.simpleValid
  val simpleTargetAngle: Double
    get() = inputs.simpleAngle

  override fun periodic() {
    io.updateInputs(inputs)
    Logger.getInstance().processInputs("Vision", inputs)
    io.setPipeline(pipeline)

    // Update LED idle state
    var targetCount = 0
    var idleOn = false
    if (pipeline == 0) {
      targetCount = if (ledsOn) inputs.cornerX.size / 4 else 0
      if (targetCount > 0) {
        targetGraceTimer.reset()
      }
      idleOn =
        targetGraceTimer.get() < targetGraceSecs || Timer.getFPGATimestamp() % blinkPeriodSecs < blinkLengthSecs || alwaysIdleOn
    }
    ledsOn = when (modeSupplier!!.get()) {
      VisionConstants.VisionLedMode.ALWAYS_OFF -> false
      VisionConstants.VisionLedMode.ALWAYS_ON -> true
      VisionConstants.VisionLedMode.AUTO -> if (forceLeds) {
        true
      } else if (DriverStation.isDisabled()) {
        false
      } else if (DriverStation.isAutonomous()) {
        autoEnabled
      } else if (climbModeSupplier!!.get()) {
        false
      } else {
        idleOn
      }
      else -> false
    }
    io.setLeds(ledsOn)

    // Process vision data
    if (pipeline == 0) {
      processFrame(targetCount)
    }

    // Log individual translations
    val xList: MutableList<Double> = ArrayList()
    val yList: MutableList<Double> = ArrayList()
    xList.add(vizOriginX)
    yList.add(vizOriginY)
    if (Timer.getFPGATimestamp() - lastTranslationsTimestamp < vizMaxNoLog) {
      val pixelsPerMeter = vizFinalHeight / vizHeightMeters
      for (translation in lastTranslations) {
        var x = vizOriginX - translation.y * pixelsPerMeter
        var y = vizOriginY - translation.x * pixelsPerMeter
        x = MathUtil.clamp(x, 0.0, vizFinalWidth)
        y = MathUtil.clamp(y, 0.0, vizFinalHeight)
        xList.add(x)
        yList.add(y)
      }
    }
    Logger.getInstance().recordOutput("Vision/CornersVizX",
      xList.stream().mapToDouble { obj: Double -> obj }.toArray()
    )
    Logger.getInstance().recordOutput("Vision/CornersVizY",
      yList.stream().mapToDouble { obj: Double -> obj }.toArray()
    )
  }

  /** Process the current vision data  */
  private fun processFrame(targetCount: Int) {
    // Exit if no new frame
    if (inputs.captureTimestamp === lastCaptureTimestamp) {
      return
    }
    lastCaptureTimestamp = inputs.captureTimestamp
    val captureTimestamp: Double = inputs.captureTimestamp - extraLatencySecs

    // Get camera constants
    val hoodAngle: Double = 0.0
    if (hoodAngle != 0.0) { // No valid hood data
      return
    }
    val cameraPosition: VisionConstants.CameraPosition? = VisionConstants.getCameraPosition(hoodAngle)

    // Calculate camera to target translation
    if (targetCount >= minTargetCount) {

      // Calculate individual corner translations
      val cameraToTargetTranslations: MutableList<Translation2d> = ArrayList()
      for (targetIndex in 0 until targetCount) {
        var corners: MutableList<VisionPoint> = ArrayList()
        var totalX = 0.0
        var totalY = 0.0
        for (i in targetIndex * 4 until targetIndex * 4 + 4) {
          if (i < inputs.cornerX.size && i < inputs.cornerY.size) {
            corners.add(VisionPoint(inputs.cornerX.get(i), inputs.cornerY.get(i)))
            totalX += inputs.cornerX.get(i)
            totalY += inputs.cornerY.get(i)
          }
        }
        val targetAvg = VisionPoint(totalX / 4, totalY / 4)
        corners = sortCorners(corners, targetAvg)
        for (i in corners.indices) {
          val translation = solveCameraToTargetTranslation(
            corners[i],
            if (i < 2) VisionConstants.FieldConstants.visionTargetHeightUpper else VisionConstants.FieldConstants.visionTargetHeightLower,
            VisionConstants.getCameraPosition(hoodAngle)
          )
          if (translation != null) {
            cameraToTargetTranslations.add(translation)
          }
        }
      }

      // Save individual translations
      lastTranslationsTimestamp = Timer.getFPGATimestamp()
      lastTranslations = cameraToTargetTranslations

      // Combine corner translations to full target translation
      if (cameraToTargetTranslations.size >= minTargetCount * 4) {
        val cameraToTargetTranslation: Translation2d = VisionConstants.CircleFitter.fit(
          VisionConstants.FieldConstants.visionTargetDiameter / 2.0,
          cameraToTargetTranslations, circleFitPrecision
        )

        // Calculate field to robot translation
        val robotRotation: Rotation2d = robotState.getDriveRotation(captureTimestamp)
        val cameraRotation = robotRotation
          .rotateBy(cameraPosition?.vehicleToCamera?.rotation)
        val fieldToTargetRotated = Transform2d(VisionConstants.FieldConstants.hubCenter, cameraRotation)
        val fieldToCamera = fieldToTargetRotated.plus(
          VisionConstants.GeomUtil
            .transformFromTranslation(cameraToTargetTranslation.unaryMinus())
        )
        val fieldToVehicle: Pose2d = VisionConstants.GeomUtil.transformToPose(
          fieldToCamera.plus(cameraPosition?.vehicleToCamera?.inverse())
        )
        if (fieldToVehicle.x > VisionConstants.FieldConstants.fieldLength || fieldToVehicle.x < 0.0 || fieldToVehicle.y > VisionConstants.FieldConstants.fieldWidth || fieldToVehicle.y < 0.0) {
          return
        }

        // Send final translation
        robotState.addVisionData(
          captureTimestamp,
          fieldToVehicle.translation
        )
      }
    }
  }

  private fun sortCorners(
    corners: List<VisionPoint>,
    average: VisionPoint
  ): MutableList<VisionPoint> {

    // Find top corners
    var topLeftIndex: Int? = null
    var topRightIndex: Int? = null
    var minPosRads = Math.PI
    var minNegRads = Math.PI
    for (i in corners.indices) {
      val corner = corners[i]
      val angleRad = Rotation2d(corner.x - average.x, average.y - corner.y)
        .minus(Rotation2d.fromDegrees(90.0)).radians
      if (angleRad > 0) {
        if (angleRad < minPosRads) {
          minPosRads = angleRad
          topLeftIndex = i
        }
      } else {
        if (Math.abs(angleRad) < minNegRads) {
          minNegRads = Math.abs(angleRad)
          topRightIndex = i
        }
      }
    }

    // Find lower corners
    var lowerIndex1: Int? = null
    var lowerIndex2: Int? = null
    for (i in corners.indices) {
      var alreadySaved = false
      if (topLeftIndex != null) {
        if (topLeftIndex == i) {
          alreadySaved = true
        }
      }
      if (topRightIndex != null) {
        if (topRightIndex == i) {
          alreadySaved = true
        }
      }
      if (!alreadySaved) {
        if (lowerIndex1 == null) {
          lowerIndex1 = i
        } else {
          lowerIndex2 = i
        }
      }
    }

    // Combine final list
    val newCorners: MutableList<VisionPoint> = ArrayList()
    if (topLeftIndex != null) {
      newCorners.add(corners[topLeftIndex])
    }
    if (topRightIndex != null) {
      newCorners.add(corners[topRightIndex])
    }
    if (lowerIndex1 != null) {
      newCorners.add(corners[lowerIndex1])
    }
    if (lowerIndex2 != null) {
      newCorners.add(corners[lowerIndex2])
    }
    return newCorners
  }

  private fun solveCameraToTargetTranslation(
    corner: VisionPoint,
    goalHeight: Double, cameraPosition: VisionConstants.CameraPosition?
  ): Translation2d? {
    val halfWidthPixels: Double = VisionConstants.WIDTH_PIXELS / 2.0
    val halfHeightPixels: Double = VisionConstants.HEIGHT_PIXELS / 2.0
    val nY: Double = -((corner.x - halfWidthPixels - VisionConstants.crosshairX)
      / halfWidthPixels)
    val nZ: Double = -((corner.y - halfHeightPixels - VisionConstants.crosshairY)
      / halfHeightPixels)
    val xzPlaneTranslation = Translation2d(1.0, vph / 2.0 * nZ)
      .rotateBy(cameraPosition?.verticalRotation)
    val x = xzPlaneTranslation.x
    val y = vpw / 2.0 * nY
    val z = xzPlaneTranslation.y
    val differentialHeight: Double = (cameraPosition?.cameraHeight?.minus(goalHeight) ?: Double) as Double
    if (z < 0.0 == differentialHeight > 0.0) {
      val scaling = differentialHeight / -z
      val distance = Math.hypot(x, y) * scaling
      val angle = Rotation2d(x, y)
      return Translation2d(
        distance * angle.cos,
        distance * angle.sin
      )
    }
    return null
  }

  class VisionPoint(val x: Double, val y: Double)
  class TimestampedTranslation2d(
    val timestamp: Double,
    val translation: Translation2d
  )

  companion object {
    private const val circleFitPrecision = 0.01
    private const val minTargetCount = 2 // For calculating odometry
    private const val extraLatencySecs = 0.06 // Approximate camera + network latency
    private const val alwaysIdleOn = true // Always light the LEDs during teleop
    private const val targetGraceSecs = 0.5
    private const val blinkPeriodSecs = 3.0
    private const val blinkLengthSecs = 0.5
    private const val vizMaxNoLog = 0.25 // How long to wait with no vision data before

    // clearing log visualization
    private const val vizFinalWidth = 1080.0
    private const val vizFinalHeight = 1920.0
    private const val vizOriginX = 540.0
    private const val vizOriginY = 1536.0
    private const val vizHeightMeters = 12.0

    // FOV constants
    private val vpw = 2.0 * Math.tan(VisionConstants.FOV_HORIZONTAL.inRadians / 2.0)
    private val vph = 2.0 * Math.tan(VisionConstants.FOV_VERTICAL.inRadians / 2.0)
  }

  /** Creates a new Vision.  */
  init {
    this.io = io
    targetGraceTimer.start()
  }
}
