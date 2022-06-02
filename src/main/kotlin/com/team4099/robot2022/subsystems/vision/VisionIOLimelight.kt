package com.team4099.robot2022.subsystems.vision

import com.team4099.robot2022.subsystems.vision.VisionIO.VisionIOInputs
import edu.wpi.first.networktables.EntryListenerFlags
import edu.wpi.first.networktables.EntryNotification
import edu.wpi.first.networktables.NetworkTableInstance
import org.littletonrobotics.junction.Logger


class VisionIOLimelight : VisionIO {
  private var captureTimestamp = 0.0
  private var cornerX = doubleArrayOf()
  private var cornerY = doubleArrayOf()
  private var simpleValid = false
  private var simpleAngle = 0.0
  private val ledEntry = NetworkTableInstance.getDefault()
    .getTable("limelight").getEntry("ledMode")
  private val pipelineEntry = NetworkTableInstance
    .getDefault().getTable("limelight").getEntry("pipeline")
  private val validEntry = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv")
  private val latencyEntry = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tl")
  private val dataEntry = NetworkTableInstance.getDefault()
    .getTable("limelight").getEntry("tcornxy")
  private val simpleAngleEntry =
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx")

  @Synchronized
  override fun updateInputs(inputs: VisionIOInputs?) {
    inputs!!.captureTimestamp = captureTimestamp
    inputs.cornerX = cornerX
    inputs.cornerY = cornerY
    inputs.simpleValid = simpleValid
    inputs.simpleAngle = simpleAngle
  }

  override fun setLeds(enabled: Boolean) {
    ledEntry.forceSetDouble(if (enabled) 3.0 else 1.0)
  }

  override fun setPipeline(pipeline: Int) {
    pipelineEntry.forceSetNumber(pipeline)
  }

  init {
    latencyEntry.addListener({ event: EntryNotification? ->
      val timestamp: Double = (Logger.getInstance().realTimestamp
        - latencyEntry.getDouble(0.0) / 1000.0)
      val cornerXList: MutableList<Double> =
        ArrayList()
      val cornerYList: MutableList<Double> =
        ArrayList()
      if (validEntry.getDouble(0.0) == 1.0) {
        var isX = true
        for (coordinate in dataEntry.getDoubleArray(doubleArrayOf())) {
          if (isX) {
            cornerXList.add(coordinate)
          } else {
            cornerYList.add(coordinate)
          }
          isX = !isX
        }
      }
      synchronized(this@VisionIOLimelight) {
        captureTimestamp = timestamp
        cornerX = cornerXList.stream()
          .mapToDouble { obj: Double -> obj }
          .toArray()
        cornerY = cornerYList.stream()
          .mapToDouble { obj: Double -> obj }
          .toArray()
        simpleValid = validEntry.getDouble(0.0) == 1.0
        simpleAngle = simpleAngleEntry.getDouble(0.0)
      }
    }, EntryListenerFlags.kUpdate)
  }
}
