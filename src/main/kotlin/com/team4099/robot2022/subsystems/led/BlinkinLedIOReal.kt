package com.team4099.robot2022.subsystems.led

import com.team4099.lib.drivers.BlinkinLedDriver
import com.team4099.robot2022.config.constants.Constants

object BlinkinLedIOReal : BlinkinLedIO {
  private val ledController = BlinkinLedDriver(Constants.LED.LED_CONTROLLER_ID)

  override fun updateInputs(inputs: BlinkinLedIO.LedIOInputs) {
    inputs.pwmValue = ledController.getPWMOutput
  }

  override fun setMode(mode: BlinkinLedDriver.BlinkinLedMode) {
    ledController.setMode(mode)
  }
}
