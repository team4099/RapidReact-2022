package com.team4099.robot2022.config.constants

import com.team4099.lib.units.base.feet
import com.team4099.lib.units.base.inches
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.radians
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Transform2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.math.geometry.Twist2d


object VisionConstants {
  const val CAMERA_NAME = "photoncamera"

  val CAMERA_HEIGHT = 29.413.meters // TODO: Update with correct value
  val UPPER_HUB_TARGET_HEIGHT = (8.feet + 8.inches) // TODO: Make sure this is correct
  val CAMERA_ANGLE = 60.degrees
  val CAMERA_PITCH = 0.radians

  val TARGET_RANGE = 5.inches // TODO: Update with correct value
  val TARGET_HEIGHT = 0.meters
  val RANGE_THRESHOLD = 2.inches // TODO: Update with correct value

  val GOAL_RANGE_METERS = 0.1.meters
  val FOV_VERTICAL = 30.degrees // TODO: ask for a better value
  val FOV_HORIZONTAL = 0.degrees // TODO: Check if its the correct value

  val WIDTH_PIXELS = 500 // TODO: Get the real value
  val HEIGHT_PIXELS = 500 // TODO: Get the real value

  const val crosshairX = 14.4 // TODO: Get the real value
  const val crosshairY = 0.0 // TODO: Get the real value

  enum class VisionLedMode {
    AUTO, ALWAYS_ON, ALWAYS_OFF
  }
  class CameraPosition(
    val cameraHeight: Double, val verticalRotation: Rotation2d,
    val vehicleToCamera: Transform2d
  )

  val vehicleToPivotX: Double = -3.5 // Might have to convert to meters for all fo these
  val vehicleToPivotZ: Double = 32.545
  val pivotToCameraX: Double = 15.288
  val pivotToCameraZ: Double = 2.103
  val cameraVerticalRotation =
    Rotation2d.fromDegrees(41.173) // Measured relative to the flat part of the hood

  val cameraVerticalRotationFudgeDegrees = -0.4

  fun getCameraPosition(hoodAngle: Double): CameraPosition? {
    // Side-on frame of reference (y is used as z)
    val vehicleToPivot = Pose2d(
      vehicleToPivotX, vehicleToPivotZ,
      Rotation2d.fromDegrees(hoodAngle)
    )
    var vehicleToCamera = vehicleToPivot.transformBy(
      Transform2d(
        Translation2d(pivotToCameraX, pivotToCameraZ), Rotation2d()
      )
    )
    vehicleToCamera = vehicleToCamera.transformBy(
      Transform2d(
        Translation2d(),
        Rotation2d.fromDegrees(180.0).minus(cameraVerticalRotation).minus(
          Rotation2d.fromDegrees(cameraVerticalRotationFudgeDegrees)
        )
      )
    )

    // Convert to camera position
    return CameraPosition(
      vehicleToCamera.y,
      Rotation2d.fromDegrees(180.0).minus(vehicleToCamera.rotation),
      Transform2d(
        Translation2d(vehicleToCamera.x, vehicleToCamera.y),
        Rotation2d.fromDegrees(180.0)
      )
    )
  }

  object FieldConstants {
    // Field dimensions
    val fieldLength: Double = 648.0 // TODO : Convert to meters irl
    val fieldWidth: Double = 324.0 // TODO : Convert to meters irl
    val hangarLength: Double = 128.75 // TODO : Convert to meters irl
    val hangarWidth: Double = 116.0 // TODO : Convert to meters irl

    // Vision target
    val visionTargetDiameter: Double = 53.375 // TODO : Convert to meters irl
    val visionTargetHeightLower: Double = 8.0 * 12.0 + 5.625 //  TODO : Convert to meters irl
    val visionTargetHeightUpper: Double = // TODO : Convert to meters irl
      visionTargetHeightLower + 2.0 // Top of tape // TODO : Convert to meters irl

    // Dimensions of hub and tarmac
    val centerLineAngle = Rotation2d.fromDegrees(66.0)
    val hubCenter = Translation2d(fieldLength / 2.0, fieldWidth / 2.0)
    val tarmacInnerDiameter: Double = 219.25 // TODO : Convert to meters irl
    val tarmacOuterDiameter: Double = 237.31 // TODO : Convert to meters irl
    val tarmacFenderToTip: Double = 84.75 // TODO : Convert to meters irl
    val tarmacFullSideLength =
      tarmacInnerDiameter * (Math.sqrt(2.0) - 1.0) // If the tarmac formed a full octagon
    val tarmacMarkedSideLength: Double =
      82.83 // TODO : Convert to meters irl
    val tarmacMissingSideLength =
      tarmacFullSideLength - tarmacMarkedSideLength // Length removed b/c of corner cutoff
    val hubSquareLength = tarmacOuterDiameter - tarmacFenderToTip * 2.0

    // Reference rotations (angle from hub to each reference point and fender side)
    val referenceARotation = Rotation2d.fromDegrees(180.0).minus(centerLineAngle)
      .plus(Rotation2d.fromDegrees(360.0 / 16.0))
    val referenceBRotation = referenceARotation.rotateBy(Rotation2d.fromDegrees(360.0 / 8.0))
    val referenceCRotation = referenceBRotation.rotateBy(Rotation2d.fromDegrees(360.0 / 8.0))
    val referenceDRotation = referenceCRotation.rotateBy(Rotation2d.fromDegrees(360.0 / 8.0))
    val fenderARotation = referenceARotation.rotateBy(Rotation2d.fromDegrees(360.0 / 16.0))
    val fenderBRotation = fenderARotation.rotateBy(Rotation2d.fromDegrees(90.0))

    // Reference points (centered of the sides of the tarmac if they formed a complete octagon, plus
    // edges of fender)
    val referenceA = Pose2d(hubCenter, referenceARotation).transformBy(
      GeomUtil.transformFromTranslation(tarmacInnerDiameter / 2.0, 0.0)
    )
    val referenceB = Pose2d(hubCenter, referenceBRotation).transformBy(
      GeomUtil.transformFromTranslation(tarmacInnerDiameter / 2.0, 0.0)
    )
    val referenceC = Pose2d(hubCenter, referenceCRotation).transformBy(
      GeomUtil.transformFromTranslation(tarmacInnerDiameter / 2.0, 0.0)
    )
    val referenceD = Pose2d(hubCenter, referenceDRotation).transformBy(
      GeomUtil.transformFromTranslation(tarmacInnerDiameter / 2.0, 0.0)
    )
    val fenderA = Pose2d(hubCenter, fenderARotation).transformBy(
      GeomUtil.transformFromTranslation(hubSquareLength / 2.0, 0.0)
    )
    val fenderB = Pose2d(hubCenter, fenderBRotation).transformBy(
      GeomUtil.transformFromTranslation(hubSquareLength / 2.0, 0.0)
    )

    // Cargo points
    val cornerToCargoY: Double = 15.56 // TODO : Convert to meters irl
    val referenceToCargoY = tarmacFullSideLength / 2.0 - cornerToCargoY
    val referenceToCargoX: Double = 40.44 // TODO : Convert to meters irl
    val cargoA = referenceA.transformBy(
      GeomUtil.transformFromTranslation(referenceToCargoX, -referenceToCargoY)
    )
    val cargoB = referenceA.transformBy(
      GeomUtil.transformFromTranslation(referenceToCargoX, referenceToCargoY)
    )
    val cargoC = referenceB.transformBy(
      GeomUtil.transformFromTranslation(referenceToCargoX, referenceToCargoY)
    )
    val cargoD = referenceC.transformBy(
      GeomUtil.transformFromTranslation(referenceToCargoX, -referenceToCargoY)
    )
    val cargoE = referenceD.transformBy(
      GeomUtil.transformFromTranslation(referenceToCargoX, -referenceToCargoY)
    )
    val cargoF = referenceD.transformBy(
      GeomUtil.transformFromTranslation(referenceToCargoX, referenceToCargoY)
    )

    // Terminal cargo point
    val terminalOuterRotation = Rotation2d.fromDegrees(133.75)
    val terminalLength: Double = 67.58
    val terminalWidth = (Math.tan(
      Rotation2d.fromDegrees(180.0).minus(terminalOuterRotation).radians
    )
      * terminalLength)
    val terminalCenter = Pose2d(
      Translation2d(terminalLength / 2.0, terminalWidth / 2.0),
      terminalOuterRotation.minus(Rotation2d.fromDegrees(90.0))
    )
    val terminalCargoOffset: Double = 10.43  // TODO : Convert to meters irl
    val cargoG = terminalCenter
      .transformBy(GeomUtil.transformFromTranslation(terminalCargoOffset, 0.0))

    // Opposite reference points
    val referenceAOpposite = opposite(referenceA)
    val referenceBOpposite = opposite(referenceB)
    val referenceCOpposite = opposite(referenceC)
    val referenceDOpposite = opposite(referenceD)
    val fenderAOpposite = opposite(fenderA)
    val fenderBOpposite = opposite(fenderB)

    // Opposite cargo points
    val cargoAOpposite = opposite(cargoA)
    val cargoBOpposite = opposite(cargoB)
    val cargoCOpposite = opposite(cargoC)
    val cargoDOpposite = opposite(cargoD)
    val cargoEOpposite = opposite(cargoE)
    val cargoFOpposite = opposite(cargoF)
    val cargoGOpposite = opposite(cargoG)

    // Calculate pose mirror on the opposite side of the field
    private fun opposite(pose: Pose2d): Pose2d {
      return Pose2d(fieldLength, fieldWidth, Rotation2d.fromDegrees(180.0))
        .transformBy(GeomUtil.poseToTransform(pose))
    }
  }

  object GeomUtil {
    /**
     * Creates a pure translating transform
     *
     * @param translation The translation to create the transform with
     * @return The resulting transform
     */
    fun transformFromTranslation(
      translation: Translation2d?
    ): Transform2d {
      return Transform2d(translation, Rotation2d())
    }

    /**
     * Creates a pure translating transform
     *
     * @param x The x componenet of the translation
     * @param y The y componenet of the translation
     * @return The resulting transform
     */
    fun transformFromTranslation(x: Double, y: Double): Transform2d {
      return Transform2d(Translation2d(x, y), Rotation2d())
    }

    /**
     * Creates a pure rotating transform
     *
     * @param rotation The rotation to create the transform with
     * @return The resulting transform
     */
    fun transformFromRotation(rotation: Rotation2d?): Transform2d {
      return Transform2d(Translation2d(), rotation)
    }

    /**
     * Creates a pure translated pose
     *
     * @param translation The translation to create the pose with
     * @return The resulting pose
     */
    fun poseFromTranslation(translation: Translation2d?): Pose2d {
      return Pose2d(translation, Rotation2d())
    }

    /**
     * Creates a pure rotated pose
     *
     * @param rotation The rotation to create the pose with
     * @return The resulting pose
     */
    fun poseFromRotation(rotation: Rotation2d?): Pose2d {
      return Pose2d(Translation2d(), rotation)
    }

    /**
     * Converts a Pose2d to a Transform2d to be used in a kinematic chain
     *
     * @param pose The pose that will represent the transform
     * @return The resulting transform
     */
    fun poseToTransform(pose: Pose2d): Transform2d {
      return Transform2d(pose.translation, pose.rotation)
    }

    /**
     * Converts a Transform2d to a Pose2d to be used as a position or as the start of a kinematic
     * chain
     *
     * @param transform The transform that will represent the pose
     * @return The resulting pose
     */
    fun transformToPose(transform: Transform2d): Pose2d {
      return Pose2d(transform.translation, transform.rotation)
    }

    /**
     * Interpolates between two poses based on the scale factor t. For example, t=0 would result in
     * the first pose, t=1 would result in the last pose, and t=0.5 would result in a pose which is
     * exactly halfway between the two poses. Values of t less than zero return the first pose, and
     * values of t greater than 1 return the last pose.
     *
     * @param lhs The left hand side, or first pose to use for interpolation
     * @param rhs The right hand side, or last pose to use for interpolation
     * @param t The scale factor, 0 <= t <= 1
     * @return The pose which represents the interpolation. For t <= 0, the "lhs" parameter is
     * returned directly. For t >= 1, the "rhs" parameter is returned directly.
     */
    fun interpolate(lhs: Pose2d, rhs: Pose2d, t: Double): Pose2d {
      if (t <= 0) {
        return lhs
      } else if (t >= 1) {
        return rhs
      }
      val twist = lhs.log(rhs)
      val scaled = Twist2d(twist.dx * t, twist.dy * t, twist.dtheta * t)
      return lhs.exp(scaled)
    }

    /**
     * Returns the direction that this translation makes with the origin as a Rotation2d
     *
     * @param translation The translation
     * @return The direction of the translation
     */
    fun direction(translation: Translation2d): Rotation2d {
      return Rotation2d(translation.x, translation.y)
    }
  }

  object CircleFitter {
    fun fit(
      radius: Double, points: List<Translation2d>,
      precision: Double
    ): Translation2d {

      // Find starting point
      var xSum = 0.0
      var ySum = 0.0
      for (point in points) {
        xSum += point.x
        ySum += point.y
      }
      var center = Translation2d(xSum / points.size + radius, ySum / points.size)

      // Iterate to find optimal center
      var shiftDist = radius / 2.0
      var minResidual = calcResidual(radius, points, center)
      while (true) {
        val translations = java.util.List.of(
          Translation2d(shiftDist, 0.0), Translation2d(-shiftDist, 0.0),
          Translation2d(0.0, shiftDist),
          Translation2d(0.0, -shiftDist)
        )
        var bestPoint = center
        var centerIsBest = true

        // Check all adjacent positions
        for (translation in translations) {
          val residual = calcResidual(radius, points, center.plus(translation))
          if (residual < minResidual) {
            bestPoint = center.plus(translation)
            minResidual = residual
            centerIsBest = false
            break
          }
        }

        // Decrease shift, exit, or continue
        if (centerIsBest) {
          shiftDist /= 2.0
          if (shiftDist < precision) {
            return center
          }
        } else {
          center = bestPoint
        }
      }
    }

    private fun calcResidual(
      radius: Double, points: List<Translation2d>,
      center: Translation2d
    ): Double {
      var residual = 0.0
      for (point in points) {
        val diff = point.getDistance(center) - radius
        residual += diff * diff
      }
      return residual
    }
  }

}
