package com.team4099.robot2022.subsystems.led

import com.ctre.phoenix.led.CANdle
import com.team4099.lib.drivers.BlinkinLedDriver
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.LEDConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase

class LEDIOCandle(blinkinLedMode: BlinkinLedDriver.BlinkinLedMode) : SubsystemBase() {
  private val ledController = CANdle(Constants.LED.LED_CONTROLLER_ID)

  var state = LEDConstants.BlinkinLEDState.IDLE
    set(newState) {
      when(newState) {
        LEDConstants.BlinkinLEDState.STANDING_TWO -> ledController.setLEDs(0, 255, 0)

      }
      field = newState
    }
}
