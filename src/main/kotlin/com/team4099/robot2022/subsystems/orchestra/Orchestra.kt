package com.team4099.robot2022.subsystems.orchestra

import com.team4099.robot2022.config.constants.OrchestraConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class Orchestra(val io: OrchestraIO) : SubsystemBase() {
  val inputs = OrchestraIO.OrchestraIOInputs()

  var state = OrchestraConstants.OrchestraState.TEST
    set(newState) {
      io.setFilePath(newState.filePath)
      field = newState
    }
  var isPlaying = false
    set(state) {
      io.setIsPlaying(state)
      field = state
    }

  init {
    state = state
  }

  override fun periodic() {
    io.updateInputs(inputs)

    Logger.getInstance().processInputs("Orchestra", inputs)
    Logger.getInstance().recordOutput("Orchestra/state", state.name)
  }
}
