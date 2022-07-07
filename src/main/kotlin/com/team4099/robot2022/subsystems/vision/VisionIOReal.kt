package com.team4099.robot2022.subsystems.vision

import com.team4099.lib.units.derived.degrees
import com.team4099.robot2022.config.constants.VisionConstants
import edu.wpi.first.math.controller.ProfiledPIDController
import edu.wpi.first.math.trajectory.TrapezoidProfile
import org.photonvision.PhotonCamera
import org.photonvision.common.hardware.VisionLEDMode
import org.photonvision.targeting.PhotonTrackedTarget

object VisionIOReal : VisionIO {
  private val camera: PhotonCamera = PhotonCamera(VisionConstants.CAMERA_NAME)
  private val cameraResult
    get() = camera.latestResult
  private val target: PhotonTrackedTarget?
    get() = cameraResult.bestTarget

  init {
    setLeds(true)
  }

  override fun updateInputs(inputs: VisionIO.VisionIOInputs) {
    inputs.hasTargets = camera.hasTargets()
    inputs.yaw = target?.yaw?.degrees ?: 0.0.degrees
    inputs.pitch = target?.pitch?.degrees ?: 0.0.degrees
    inputs.area = target?.area ?: 0.0
  }

  val visionPIDcontroller =
    ProfiledPIDController(
      VisionConstants.TurnGains.KP,
      VisionConstants.TurnGains.KI,
      VisionConstants.TurnGains.KD,
      TrapezoidProfile.Constraints(
        VisionConstants.TurnGains.MAX_VELOCITY.value,
        VisionConstants.TurnGains.MAX_ACCEL.value
      )
    )

  var pipeline = VisionConstants.DRIVER_PIPELINE_ID
    set(value) {
      // this method sets the pipeline index and entry
      camera.setPipelineIndex(value)
      field = value
    }

  override fun configPID(kP: Double, kI: Double, kD: Double) {
    visionPIDcontroller.p = kP
    visionPIDcontroller.i = kI
    visionPIDcontroller.d = kD
  }

  override fun setLeds(enabled: Boolean) {
    if (enabled) {
      camera.setLED(VisionLEDMode.kOn)
    } else {
      camera.setLED(VisionLEDMode.kOff)
    }
  }
}
