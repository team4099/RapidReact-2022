package com.team4099.robot2022.subsystems.vision

import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface VisionIO {
  class VisionIOInputs : LoggableInputs {

    override fun fromLog(table: LogTable?) {}

    override fun toLog(table: LogTable?) {}
  }
  fun updateInputs(inputs: VisionIOInputs) {}
}
