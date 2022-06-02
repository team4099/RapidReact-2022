package com.team4099.robot2022.subsystems.vision

import org.photonvision.PhotonCamera
import org.photonvision.PhotonTrackedTarget
import com.team4099.robot2022.config.constants.VisionConstants
import com.team4099.lib.units.derived.radians

class Vision : SubsystemBase(){
  val closeCamera: PhotonCamera = PhotonCamera("gloworm-near")
  private val closeCameraResult
    get() = closeCamera.getLatestResult()
  private val target: PhotonTrackedTarget?
    get() = closeCameraResult.bestTarget

  override fun periodic(){

  }

  fun newTarget() {
    if (results.hasTargets()) {
      var range: Double = PhotonUtils.calculateDistanceToTargetMeters(
        VisionConstants.CAMERA_HEIGHT,
        VisionConstants.TARGET_HEIGHT,
        VisionConstants.CAMERA_PITCH,
        closeCamera.getBestTarget().getPitch().radians)

    }
  }
}
