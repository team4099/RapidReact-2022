package com.team4099.robot2022.subsystems.led

import com.team4099.robot2022.config.constants.LEDConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class CandleLed(val io: CandleLedIO) : SubsystemBase() {
  val inputs = CandleLedIO.LEDIOInputs()

  var state = LEDConstants.CandleState.IDLE
    set(newState) {
      newState.animation?.let { io.setAnimation(it, state.r, state.g, state.b) }
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
