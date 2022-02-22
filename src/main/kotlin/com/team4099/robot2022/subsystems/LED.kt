package com.team4099.robot2022.subsystems

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.LEDConstants
import edu.wpi.first.wpilibj.motorcontrol.Spark
import edu.wpi.first.wpilibj2.command.SubsystemBase

object LED : SubsystemBase() {
  private val ledController = Spark(Constants.LED.LED_CONTROLLER_ID)

  var ballCount = 0
    set(newCount) {
      if (intakeReady == true && 0 <= newCount && newCount <= 2) {
        field = newCount
        if (newCount > ballCount) {
          intakeReady = false
        }
      }
    }
  var intakeReady = true

  var ledState = LEDConstants.LEDState.IDLE
    set(newState) {
      ledController.set(newState.pwmValue)
      field = newState
    }

  init {
    Logger.addSource(Constants.LED.TAB, "LED Status") { ledState.toString() }
  }
}
