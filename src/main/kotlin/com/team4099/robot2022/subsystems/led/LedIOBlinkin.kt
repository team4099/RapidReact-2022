package com.team4099.robot2022.subsystems.led

import com.team4099.lib.drivers.BlinkinLedDriver
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.LEDConstants

object LedIOBlinkin : LedIO {
  private val ledController = BlinkinLedDriver(Constants.LED.LED_CONTROLLER_ID)

  private var lastLedState: BlinkinLedDriver.BlinkinLedMode =
    LEDConstants.BlinkinLEDState.IDLE.blinkinMode

  override fun updateInputs(inputs: LedIO.LedIOInputs) {
    inputs.ledState = lastLedState.name
  }

  override fun setMode(mode: BlinkinLedDriver.BlinkinLedMode) {
    lastLedState = mode
    ledController.setMode(mode)
  }
}
