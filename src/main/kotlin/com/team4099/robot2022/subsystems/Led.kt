package com.team4099.robot2022.subsystems

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.Constants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj.motorcontrol.Spark


object Led : SubsystemBase(){
  private val ledController = Spark(Constants.Led.LED_CONTROLLER_ID)

  var ledState = Constants.Led.LedState.IDLE
    set(value) {
      ledController.set(value.pwmValue)
      field = value
    }

  init {
    Logger.addSource(Constants.Led.TAB, "LED PWM Status") { ledState.toString() }
  }
}
