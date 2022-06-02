package com.team4099.robot2022.subsystems.vision

import com.team4099.robot2022.subsystems.vision.VisionIO.VisionIOInputs
import edu.wpi.first.networktables.EntryListenerFlags
import edu.wpi.first.networktables.EntryNotification
import edu.wpi.first.networktables.NetworkTableInstance
import org.littletonrobotics.junction.Logger
import org.photonvision.PhotonCamera
import org.photonvision.common.hardware.VisionLEDMode


class VisionIOPhotonVision : VisionIO {
  private val camera = PhotonCamera(cameraName)
  private var captureTimestamp = 0.0
  private var cornerX = doubleArrayOf()
  private var cornerY = doubleArrayOf()

  @Synchronized
  override fun updateInputs(inputs: VisionIOInputs?) {
    inputs!!.captureTimestamp = captureTimestamp
    inputs.cornerX = cornerX
    inputs.cornerY = cornerY
  }

  override fun setLeds(enabled: Boolean) {
    camera.setLED(if (enabled) VisionLEDMode.kOn else VisionLEDMode.kOff)
  }

  companion object {
    private const val cameraName = "limelight"
  }

  init {
    NetworkTableInstance.getDefault()
      .getEntry("/photonvision/$cameraName/latencyMillis")
      .addListener({ event: EntryNotification? ->
        val result = camera.latestResult
        val timestamp: Double = (Logger.getInstance().realTimestamp
          - result.latencyMillis / 1000.0)
        val cornerXList: MutableList<Double> =
          ArrayList()
        val cornerYList: MutableList<Double> =
          ArrayList()
        for (target in result.getTargets()) {
          for (corner in target.corners) {
            cornerXList.add(corner.x)
            cornerYList.add(corner.y)
          }
        }
        synchronized(this@VisionIOPhotonVision) {
          captureTimestamp = timestamp
          cornerX = cornerXList.stream()
            .mapToDouble { obj: Double -> obj }
            .toArray()
          cornerY = cornerYList.stream()
            .mapToDouble { obj: Double -> obj }
            .toArray()
        }
      }, EntryListenerFlags.kUpdate)
  }
}
