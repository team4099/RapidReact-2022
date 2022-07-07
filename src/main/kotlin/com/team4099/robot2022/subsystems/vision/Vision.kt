package com.team4099.robot2022.subsystems.vision

import com.team4099.lib.logging.TunableNumber
import com.team4099.lib.units.base.Length
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inDegrees
import com.team4099.lib.units.derived.inRadians
import com.team4099.robot2022.config.constants.VisionConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger
import org.photonvision.PhotonUtils

class Vision(val io: VisionIO) : SubsystemBase() {
  val inputs = VisionIO.VisionIOInputs()

  private val kP = TunableNumber("Vision/kP", VisionConstants.TurnGains.KP)
  private val kI = TunableNumber("Vision/kI", VisionConstants.TurnGains.KI)
  private val kD = TunableNumber("Vision/kD", VisionConstants.TurnGains.KD)

  init {}

  override fun periodic() {
    io.updateInputs(inputs)

    Logger.getInstance().processInputs("Vision", inputs)
    Logger.getInstance().recordOutput("Vision/distanceFromTargetInMeters", distanceMeters.inMeters)
    Logger.getInstance().recordOutput("Vision/yaw", yawToUse.inDegrees)
    Logger.getInstance().recordOutput("Vision/onTarget", onTarget)

    if (kP.hasChanged() || kI.hasChanged() || kD.hasChanged()) {
      io.configPID(kP.value, kI.value, kD.value)
    }
  }

  private val distanceMeters: Length
    get() =
      PhotonUtils.calculateDistanceToTargetMeters(
        VisionConstants.CAMERA_HEIGHT.inMeters,
        VisionConstants.UPPER_HUB_TARGET_HEIGHT.inMeters,
        VisionConstants.CAMERA_ANGLE.inRadians,
        inputs.pitch.inRadians
      )
        .meters

  var yawToUse = 0.0.degrees
    get() =
      when {
        inputs.hasTargets -> inputs.yaw
        else -> VisionConstants.MAX_ANGLE_ERROR
      }

  var onTarget = false
    get() = yawToUse.absoluteValue < VisionConstants.MAX_ANGLE_ERROR
}
