package com.team4099.robot2022.subsystems.led

import com.ctre.phoenix.led.CANdle
import com.team4099.lib.drivers.BlinkinLedDriver
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.LEDConstants

object LedIOCandle : LedIO {
  private val ledController = CANdle(Constants.LED.LED_CONTROLLER_ID)

  private var lastLedState: BlinkinLedDriver.BlinkinLedMode =
    LEDConstants.BlinkinLEDState.IDLE.blinkinMode

  override fun updateInputs(inputs: LedIO.LedIOInputs) {
    inputs.ledState = lastLedState.name
  }

  override fun setMode(mode: BlinkinLedDriver.BlinkinLedMode) {
    lastLedState = mode
    when (mode) {
      LEDConstants.BlinkinLEDState.IDLE.blinkinMode ->
        convertCandleStateToRGB(LEDConstants.CandleState.IDLE)
      LEDConstants.BlinkinLEDState.AUTO.blinkinMode ->
        convertCandleStateToRGB(LEDConstants.CandleState.AUTO)
      LEDConstants.BlinkinLEDState.STANDING_ZERO.blinkinMode ->
        convertCandleStateToRGB(LEDConstants.CandleState.STANDING_ZERO)
      LEDConstants.BlinkinLEDState.STANDING_ONE.blinkinMode ->
        convertCandleStateToRGB(LEDConstants.CandleState.STANDING_ONE)
      LEDConstants.BlinkinLEDState.STANDING_TWO.blinkinMode ->
        convertCandleStateToRGB(LEDConstants.CandleState.STANDING_TWO)
      LEDConstants.BlinkinLEDState.INTAKING.blinkinMode ->
        convertCandleStateToRGB(LEDConstants.CandleState.INTAKING)
      LEDConstants.BlinkinLEDState.OUTTAKING.blinkinMode ->
        convertCandleStateToRGB(LEDConstants.CandleState.OUTTAKING)
      LEDConstants.BlinkinLEDState.CLIMBER_READY.blinkinMode ->
        convertCandleStateToRGB(LEDConstants.CandleState.CLIMBER_READY)
      LEDConstants.BlinkinLEDState.CLIMBING.blinkinMode ->
        convertCandleStateToRGB(LEDConstants.CandleState.CLIMBING)
      LEDConstants.BlinkinLEDState.DISALLOW_CLIMB.blinkinMode ->
        convertCandleStateToRGB(LEDConstants.CandleState.DISALLOW_CLIMB)
      else -> convertCandleStateToRGB(LEDConstants.CandleState.IDLE)
    }
  }

  private fun convertCandleStateToRGB(candleState: LEDConstants.CandleState) {
    ledController.setLEDs(candleState.r, candleState.g, candleState.b)
  }
}
