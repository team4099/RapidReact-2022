package com.team4099.robot2022.subsystems.led

import com.ctre.phoenix.led.Animation
import com.ctre.phoenix.led.CANdle
import com.team4099.robot2022.config.constants.Constants

object CandleLedIOReal : CandleLedIO {
  private val ledController = CANdle(Constants.LED.LED_CONTROLLER_ID)

  override fun updateInputs(inputs: CandleLedIO.LEDIOInputs) {}

  override fun setAnimation(animation: Animation?, red: Int, green: Int, blue: Int) {
    if (animation == null) {
      ledController.setLEDs(red, green, blue)
    } else {
      ledController.animate(animation)
    }
  }
}
