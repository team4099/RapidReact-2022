package com.team4099.robot2022.subsystems.led

import com.ctre.phoenix.led.CANdle
import com.team4099.lib.drivers.BlinkinLedDriver
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.LEDConstants

object LedIOReal : LedIO {
  private val ledController = CANdle(Constants.LED.LED_CONTROLLER_ID)

  override fun updateInputs(inputs: LedIO.LedIOInputs) {
    inputs.pwmValue = ledController.getPWMOutput
  }

  override fun setMode(mode: LEDConstants.LEDState) {
    ledController.setLEDs()
    ledController.setMode(mode)
  }
}
