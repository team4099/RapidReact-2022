package com.team4099.robot2022.subsystems

import com.team4099.lib.drivers.BlinkinLedDriver
import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.LEDConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase

object LED : SubsystemBase() {
  private val ledController = BlinkinLedDriver(Constants.LED.LED_CONTROLLER_ID)

  var ledState = LEDConstants.LEDState.IDLE
    set(newState) {
      ledController.setMode(newState.blinkinMode)
      field = newState
    }

  init {
    Logger.addSource(Constants.LED.TAB, "LED Status") { ledState.toString() }
  }

  override fun periodic() {
    // when (Climber.telescopeState) {
    //  ClimberConstants.TelescopeState.OFF -> {
    //    insert current code
    //  }
    //  else ->
    // }
  }
}
