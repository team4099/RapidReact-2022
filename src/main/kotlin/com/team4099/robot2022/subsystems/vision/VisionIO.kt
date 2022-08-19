package com.team4099.robot2022.subsystems.vision

import com.team4099.lib.units.base.inMilliseconds
import com.team4099.lib.units.base.seconds
import com.team4099.lib.units.derived.Angle
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inDegrees
import com.team4099.lib.units.milli
import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface VisionIO {
  class VisionIOInputs : LoggableInputs {
    var captureTimestamp = 0.0.milli.seconds
    var cornerX = doubleArrayOf()
    var cornerY = doubleArrayOf()
    var validTarget: Boolean = false // tv
    var horizontalLLAngle: Angle = 0.0.degrees // tx

    override fun toLog(table: LogTable?) {
      table?.put("captureTimestamp", captureTimestamp.inMilliseconds)
      table?.put("cornerX", cornerX)
      table?.put("cornerY", cornerY)
      table?.put("validTargetRead", validTarget)
      table?.put("horizontalLLAngle", horizontalLLAngle.inDegrees)
    }
    override fun fromLog(table: LogTable?) {
      table?.getDouble("captureTimestamp", captureTimestamp.inMilliseconds)?.let {
        captureTimestamp = it.milli.seconds
      }
      table?.getDoubleArray("cornerX", cornerX)?.let { cornerX = it }
      table?.getDoubleArray("cornerY", cornerY)?.let { cornerY = it }
      table?.getBoolean("validTargetRead", validTarget)?.let { validTarget = it }
      table?.getDouble("horizontalLLAngle", horizontalLLAngle.inDegrees)?.let {
        horizontalLLAngle = it.degrees
      }
    }
  }

  fun updateInputs(inputs: VisionIOInputs) {}

  fun setLeds(enabled: Boolean) {}
}
