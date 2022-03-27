package com.team4099.robot2022.subsystems.orchestra

import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface OrchestraIO {
  class OrchestraIOInputs : LoggableInputs {
    var isPlaying: Boolean = false
    var filePath: String = "music/never.chrp"

    override fun toLog(table: LogTable?) {
      table?.put("isPlaying", isPlaying)
      table?.put("filePath", filePath)
    }

    override fun fromLog(table: LogTable?) {}
  }

  fun updateInputs(inputs: OrchestraIOInputs) {}

  fun setIsPlaying(isPlaying: Boolean) {}
  fun setFilePath(filePath: String) {}
}
