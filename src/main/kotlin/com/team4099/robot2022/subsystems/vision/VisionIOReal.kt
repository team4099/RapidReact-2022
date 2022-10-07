package com.team4099.robot2022.subsystems.vision

import com.team4099.lib.units.base.seconds
import org.photonvision.PhotonCamera

object VisionIOReal : VisionIO {
  val frontFacingCamera: PhotonCamera = PhotonCamera("gloworm")
  init {
    frontFacingCamera.pipelineIndex = 1
  }

  override fun updateInputs(inputs: VisionIO.VisionIOInputs) {
    inputs.hasTargets = frontFacingCamera.latestResult.hasTargets()
    inputs.latency = frontFacingCamera.latestResult.latencyMillis.seconds * 1000
  }
}
