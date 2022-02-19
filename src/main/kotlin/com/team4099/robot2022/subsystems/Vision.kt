package com.team4099.robot2022.subsystems

import com.team4099.lib.units.Value
import com.team4099.lib.units.base.Meter
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.derived.inDegrees
import com.team4099.robot2022.config.constants.VisionConstants
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.wpilibj2.command.SubsystemBase
import kotlin.math.cos
import org.photonvision.PhotonCamera
import org.photonvision.PhotonUtils
import org.photonvision.targeting.PhotonPipelineResult

object Vision : SubsystemBase() {
  private val camera = PhotonCamera(VisionConstants.CAMERA_NAME)

  private val latestResult: PhotonPipelineResult?
    get() = camera.latestResult

  val hasTargets
    get() = latestResult?.hasTargets()

  val bestTarget
    get() = latestResult?.bestTarget

  val yaw
    get() = bestTarget?.yaw

  fun getRangeToBestTarget(): Value<Meter> {
    return (PhotonUtils.calculateDistanceToTargetMeters(
            VisionConstants.CAMERA_HEIGHT.inMeters,
            VisionConstants.UPPER_HUB_TARGET_HEIGHT.inMeters,
            0.0,
            bestTarget!!.pitch) * cos(VisionConstants.CAMERA_ANGLE.inDegrees)).meters
  }

  fun getOffsetToBestTarget(): Translation2d {
    return PhotonUtils.estimateCameraToTargetTranslation(
        getRangeToBestTarget().inMeters, Rotation2d.fromDegrees(-bestTarget!!.yaw))
  }
}
