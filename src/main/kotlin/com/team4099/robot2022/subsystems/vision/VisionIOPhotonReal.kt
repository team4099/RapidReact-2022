package com.team4099.robot2022.subsystems.vision

import com.team4099.lib.units.base.meters
import com.team4099.lib.units.base.seconds
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.milli
import org.photonvision.PhotonCamera

object VisionIOPhotonReal : VisionIO {
  val camera: PhotonCamera = PhotonCamera("gloworm")
  var bestXReading = 0.0.meters
  var bestYReading = 0.0.meters
  var bestZReading = 0.0.meters
  var bestRollReading = 0.0.degrees
  var bestPitchReading = 0.0.degrees
  var bestYawReading = 0.0.degrees

  var latency = 0.milli.seconds
  var hasTargets = false

  init {
    camera.pipelineIndex = 1
    /*
    NetworkTableInstance.getDefault()
      .getEntry("/photonvision/" + "gloworm" + "/latencyMillis")
      .addListener(
        { event: EntryNotification? ->
          val result = camera.latestResult
          val timestamp =
            (Logger.getInstance().realTimestamp - result.latencyMillis / 1000.0).seconds
          var bestX = 0.meters
          var bestY = 0.meters
          var bestZ = 0.0.meters
          var bestRoll = 0.0.degrees
          var bestPitch = 0.0.degrees
          var bestYaw = 0.0.degrees
          if (result.hasTargets()) {
            bestX = result.bestTarget.bestCameraToTarget.x.meters
            bestY = result.bestTarget.bestCameraToTarget.y.meters
            bestZ = result.bestTarget.bestCameraToTarget.z.meters
            bestRoll = result.bestTarget.bestCameraToTarget.rotation.x.degrees
            bestPitch = result.bestTarget.bestCameraToTarget.rotation.y.degrees
            bestYaw = result.bestTarget.bestCameraToTarget.rotation.z.degrees
          }
          val existentTargets = result.hasTargets()
          synchronized(this@VisionIOPhotonReal) {
            latency = timestamp
            bestXReading = bestX
            bestYReading = bestY
            bestZReading = bestZ
            bestRollReading = bestRoll
            bestPitchReading = bestPitch
            bestYawReading = bestYaw
            hasTargets = existentTargets
          }
        },
        EntryListenerFlags.kUpdate
      )
     */
  }

  override fun updateInputs(inputs: VisionIO.VisionIOInputs) {
    inputs.latency = latency
    inputs.bestX = bestXReading
    inputs.bestY = bestYReading
    inputs.bestZ = bestZReading
    inputs.bestRoll = bestRollReading
    inputs.bestPitch = bestPitchReading
    inputs.bestYaw = bestYawReading
    inputs.hasTargets = hasTargets
  }
}
