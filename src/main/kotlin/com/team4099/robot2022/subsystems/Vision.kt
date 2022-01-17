package com.team4099.robot2022.subsystems

import com.team4099.lib.units.base.inMeters
import com.team4099.robot2022.config.Constants.VisionConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.photonvision.PhotonCamera
import org.photonvision.PhotonPipelineResult
import org.photonvision.PhotonTrackedTarget
import org.photonvision.PhotonUtils

object Vision : SubsystemBase() {
  private val camera = PhotonCamera(VisionConstants.CAMERA_NAME)

  private val latestResult: PhotonPipelineResult?
    get() = camera.latestResult

  val hasTargets
    get() = latestResult?.hasTargets()

  val bestTarget
    get() = latestResult?.bestTarget

  fun calculateDistance(target: PhotonTrackedTarget): Double {
    return PhotonUtils.calculateDistanceToTargetMeters(
      VisionConstants.CAMERA_HEIGHT.inMeters,
      VisionConstants.UPPER_HUB_TARGET_HEIGHT.inMeters,
      0.0,
      0.0
    )
  }
}
