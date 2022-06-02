package com.team4099.robot2022.subsystems.vision

import org.littletonrobotics.junction.LogTable

import org.littletonrobotics.junction.inputs.LoggableInputs


interface VisionIO {
  /** The set of loggable inputs for the vision subsystem.  */
  class VisionIOInputs : LoggableInputs {
    var captureTimestamp = 0.0
    var cornerX = doubleArrayOf()
    var cornerY = doubleArrayOf()
    var simpleValid = false
    var simpleAngle = 0.0
    override fun toLog(table: LogTable) {
      table?.put("CaptureTimestamp", captureTimestamp)
      table?.put("CornerX", cornerX)
      table?.put("CornerY", cornerY)
      table?.put("SimpleValid", simpleValid)
      table?.put("SimpleAngle", simpleAngle)
    }

    override fun fromLog(table: LogTable) {
      captureTimestamp = table?.getDouble("CaptureTimestamp", captureTimestamp)
      cornerX = table?.getDoubleArray("CornerX", cornerX)
      cornerY = table?.getDoubleArray("CornerY", cornerY)
      simpleValid = table?.getBoolean("SimpleValid", simpleValid)
      simpleAngle = table?.getDouble("SimpleAngle", simpleAngle)
    }
  }

  /** Updates the set of loggable inputs.  */
  fun updateInputs(inputs: VisionIOInputs?) {}

  /** Enabled or disabled vision LEDs.  */
  fun setLeds(enabled: Boolean) {}

  /** Sets the pipeline number.  */
  fun setPipeline(pipeline: Int) {}
}
