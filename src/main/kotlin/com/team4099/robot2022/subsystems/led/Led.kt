package com.team4099.robot2022.subsystems.led

import com.team4099.robot2022.config.constants.LEDConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class Led(val io: LedIO) : SubsystemBase() {
  val inputs = LedIO.LedIOInputs()

  var state = LEDConstants.BlinkinLEDState.IDLE
    set(newState) {
      io.setMode(newState.blinkinMode)
      field = newState
    }

  init {
    state = state
  }

  override fun periodic() {
    io.updateInputs(inputs)

    Logger.getInstance().processInputs("LED", inputs)
    Logger.getInstance().recordOutput("LED/state", state.name)
  }
}
