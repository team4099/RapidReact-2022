package com.team4099.robot2022.subsystems.vision

import com.team4099.robot2022.config.constants.VisionConstants
import org.photonvision.PhotonCamera

object VisionIOAprilTagsReal : VisionIO {
  var frontFacingCamera = PhotonCamera(VisionConstants.CAMERA_NAME)

  override fun updateInputs(inputs: VisionIO.VisionIOInputs) {}
}
