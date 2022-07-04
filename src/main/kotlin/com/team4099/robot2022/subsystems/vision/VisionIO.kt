package com.team4099.robot2022.subsystems.vision

import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface VisionIO {
  class VisionIOInputs : LoggableInputs {
    //varaibles

    override fun toLog(table: LogTable?) {
      TODO("Not yet implemented")
    }

    override fun fromLog(table: LogTable?) {
      TODO("Not yet implemented")
    }

    fun updateInputs(inputs: VisionIO.VisionIOInputs) {}

    fun setLeds(enabled: Boolean) {}


  }
}
