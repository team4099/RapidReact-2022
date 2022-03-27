package com.team4099.robot2022.subsystems.orchestra

import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.ctre.phoenix.music.Orchestra

object OrchestraIOReal : OrchestraIO {
  private val orchestra: Orchestra = Orchestra()

  override fun updateInputs(inputs: OrchestraIO.OrchestraIOInputs) {
    inputs.isPlaying = orchestra.isPlaying
  }

  override fun setFilePath(filePath: String) {
    orchestra.loadMusic(filePath)
  }

  override fun setIsPlaying(isPlaying: Boolean) {
    if (isPlaying) {
      orchestra.play()
    } else {
      orchestra.stop()
    }
  }

  fun addInstrument(instrument: TalonFX) {
    orchestra.addInstrument(instrument)
  }
}
