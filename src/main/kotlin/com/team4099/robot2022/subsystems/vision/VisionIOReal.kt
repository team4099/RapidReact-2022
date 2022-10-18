package com.team4099.robot2022.subsystems.vision

import com.team4099.lib.units.base.meters
import com.team4099.lib.units.base.seconds
import com.team4099.lib.units.milli
import edu.wpi.first.networktables.EntryListenerFlags
import edu.wpi.first.networktables.EntryNotification
import edu.wpi.first.networktables.NetworkTableInstance
import org.littletonrobotics.junction.Logger
import org.photonvision.PhotonCamera

object VisionIOReal : VisionIO {
  val camera: PhotonCamera = PhotonCamera("gloworm")
  var bestXReading = 0.meters
  var bestYReading = 0.meters
  var bestZReading = 0.0.meters
  var latency = 0.milli.seconds
  var hasTargets = false

  init {
    camera.pipelineIndex = 1
    NetworkTableInstance.getDefault()
      .getEntry("/photonvision/" + "gloworm" + "/latencyMillis")
      .addListener(
        { event: EntryNotification? ->
          System.out.println(camera.latestResult.toString())
          val result = camera.latestResult
          val timestamp =
            (Logger.getInstance().realTimestamp - result.latencyMillis / 1000.0).seconds
          var bestX = 0.meters
          var bestY = 0.meters
          var bestZ = 0.0.meters
          if (result.hasTargets()) {
            System.out.println(result.bestTarget.cameraToTarget.z)
            bestX = result.bestTarget.cameraToTarget.x.meters
            bestY = result.bestTarget.cameraToTarget.y.meters
            bestZ = result.bestTarget.cameraToTarget.z.meters
          }
          val existentTargets = result.hasTargets()
          synchronized(this@VisionIOReal) {
            latency = timestamp
            bestXReading = bestX
            bestYReading = bestY
            bestZReading = bestZ
            hasTargets = existentTargets
          }
        },
        EntryListenerFlags.kUpdate
      )
  }

  override fun updateInputs(inputs: VisionIO.VisionIOInputs) {
    inputs.latency = latency
    inputs.bestX = bestXReading
    inputs.bestY = bestYReading
    inputs.bestZ = bestZReading
    inputs.hasTargets = hasTargets
  }
}
