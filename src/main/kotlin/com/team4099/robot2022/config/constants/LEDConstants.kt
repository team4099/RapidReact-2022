package com.team4099.robot2022.config.constants

import com.team4099.lib.drivers.BlinkinLedDriver
import com.team4099.lib.units.base.amps

object LEDConstants {
  val INTAKE_CURRENT_THRESHOLD = 20.amps

  enum class LEDState(val blinkinMode: BlinkinLedDriver.BlinkinLedMode) {
    // Solid Orange
    IDLE(BlinkinLedDriver.BlinkinLedMode.SOLID_ORANGE),

    // Solid Red
    STANDING_ZERO(BlinkinLedDriver.BlinkinLedMode.SOLID_GOLD),

    // Solid Yellow
    STANDING_ONE(BlinkinLedDriver.BlinkinLedMode.SOLID_YELLOW),

    // Solid Green
    STANDING_TWO(BlinkinLedDriver.BlinkinLedMode.SOLID_GREEN),

    // Blinking Red
    INTAKING(BlinkinLedDriver.BlinkinLedMode.SOLID_BLUE),

    // Blinking Yellow
    CLIMBER_READY(BlinkinLedDriver.BlinkinLedMode.FIXED_BEATS_OCEAN),

    // Blinking Green
    CLIMBING(BlinkinLedDriver.BlinkinLedMode.FIXED_BEATS_LAVA),

    // TODO
    SPINNING_UP(BlinkinLedDriver.BlinkinLedMode.SOLID_BLACK),

    // TODO
    READY_SHOOT(BlinkinLedDriver.BlinkinLedMode.SOLID_GREEN)
  }
}
