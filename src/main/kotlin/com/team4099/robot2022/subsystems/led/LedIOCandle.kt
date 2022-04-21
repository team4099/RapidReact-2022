package com.team4099.robot2022.subsystems.led

import com.ctre.phoenix.led.CANdle
import com.team4099.lib.drivers.BlinkinLedDriver
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.LEDConstants.BlinkinLEDState
import com.team4099.robot2022.config.constants.LEDConstants.CandleState

object LedIOCandle : LedIO {
  private val ledController = CANdle(Constants.LED.LED_CONTROLLER_ID)

  private var lastLedState: BlinkinLedDriver.BlinkinLedMode =
    BlinkinLEDState.IDLE.blinkinMode

  override fun updateInputs(inputs: LedIO.LedIOInputs) {
    inputs.ledState = lastLedState.name
  }

  override fun setMode(mode: BlinkinLedDriver.BlinkinLedMode) {
    lastLedState = mode
    when (mode) {
      BlinkinLEDState.IDLE.blinkinMode ->
        setCandleState(CandleState.IDLE)
      BlinkinLEDState.AUTO.blinkinMode ->
        setCandleState(CandleState.AUTO)
      BlinkinLEDState.STANDING_ZERO.blinkinMode ->
        setCandleState(CandleState.STANDING_ZERO)
      BlinkinLEDState.STANDING_ONE.blinkinMode ->
        setCandleState(CandleState.STANDING_ONE)
      BlinkinLEDState.STANDING_TWO.blinkinMode ->
        setCandleState(CandleState.STANDING_TWO)
      BlinkinLEDState.INTAKING.blinkinMode ->
        setCandleState(CandleState.INTAKING)
      BlinkinLEDState.OUTTAKING.blinkinMode ->
        setCandleState(CandleState.OUTTAKING)
      BlinkinLEDState.CLIMBER_READY.blinkinMode ->
        setCandleState(CandleState.CLIMBER_READY)
      BlinkinLEDState.CLIMBING.blinkinMode ->
        setCandleState(CandleState.CLIMBING)
      BlinkinLEDState.DISALLOW_CLIMB.blinkinMode ->
        setCandleState(CandleState.DISALLOW_CLIMB)
      else -> setCandleState(CandleState.IDLE)
    }
  }

  private fun setCandleState(candleState: CandleState) {
    if (candleState.animation == null) {
      ledController.setLEDs(candleState.r, candleState.g, candleState.b)
    } else {
      ledController.animate(candleState.animation)
    }
  }
}
