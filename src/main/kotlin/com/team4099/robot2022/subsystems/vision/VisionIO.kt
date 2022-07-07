package com.team4099.robot2022.subsystems.vision

import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inDegrees
import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface VisionIO {
  class VisionIOInputs : LoggableInputs {
    // tv
    var hasTargets = false
    // tx
    // negative: target on left of screen
    var yaw = 0.0.degrees
    // ty
    // negative: target on bottom of screen
    var pitch = 0.0.degrees
    // ta
    // area is 0-100%
    var area = 0.0

    override fun toLog(table: LogTable?) {
      table?.put("hasTargets", hasTargets)
      table?.put("yaw", yaw.inDegrees)
      table?.put("pitch", pitch.inDegrees)
      table?.put("area", area)
    }

    override fun fromLog(table: LogTable?) {
      table?.getBoolean("hasTargets", hasTargets)?.let { hasTargets = it }
      table?.getDouble("yaw", yaw.inDegrees)?.let { yaw = it.degrees }
      table?.getDouble("pitch", pitch.inDegrees)?.let { pitch = it.degrees }
      table?.getDouble("area", area)?.let { area = it }
    }
  }

  fun updateInputs(inputs: VisionIO.VisionIOInputs) {}

  fun setLeds(enabled: Boolean) {}

  fun configPID(kP: Double, kI: Double, kD: Double) {}
}
