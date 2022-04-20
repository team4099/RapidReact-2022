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
        mapBlinkinStateToCandle(LEDConstants.CandleState.IDLE)
      LEDConstants.BlinkinLEDState.AUTO.blinkinMode ->
        mapBlinkinStateToCandle(LEDConstants.CandleState.AUTO)
      LEDConstants.BlinkinLEDState.STANDING_ZERO.blinkinMode ->
        mapBlinkinStateToCandle(LEDConstants.CandleState.STANDING_ZERO)
      LEDConstants.BlinkinLEDState.STANDING_ONE.blinkinMode ->
        mapBlinkinStateToCandle(LEDConstants.CandleState.STANDING_ONE)
      LEDConstants.BlinkinLEDState.STANDING_TWO.blinkinMode ->
        mapBlinkinStateToCandle(LEDConstants.CandleState.STANDING_TWO)
      LEDConstants.BlinkinLEDState.INTAKING.blinkinMode ->
        mapBlinkinStateToCandle(LEDConstants.CandleState.INTAKING)
      LEDConstants.BlinkinLEDState.OUTTAKING.blinkinMode ->
        mapBlinkinStateToCandle(LEDConstants.CandleState.OUTTAKING)
      LEDConstants.BlinkinLEDState.CLIMBER_READY.blinkinMode ->
        mapBlinkinStateToCandle(LEDConstants.CandleState.CLIMBER_READY)
      LEDConstants.BlinkinLEDState.CLIMBING.blinkinMode ->
        mapBlinkinStateToCandle(LEDConstants.CandleState.CLIMBING)
      LEDConstants.BlinkinLEDState.DISALLOW_CLIMB.blinkinMode ->
        mapBlinkinStateToCandle(LEDConstants.CandleState.DISALLOW_CLIMB)
      else -> mapBlinkinStateToCandle(LEDConstants.CandleState.IDLE)
    }
  }

  private fun mapBlinkinStateToCandle(candleState: LEDConstants.CandleState) {
    if (candleState.animation == null) {
      ledController.setLEDs(candleState.r, candleState.g, candleState.b)
    } else {
      ledController.animate(candleState.animation)
    }
  }
}
