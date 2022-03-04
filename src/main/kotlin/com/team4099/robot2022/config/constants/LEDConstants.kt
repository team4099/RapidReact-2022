package com.team4099.robot2022.config.constants

import com.team4099.lib.drivers.BlinkinLedDriver

object LEDConstants {
  const val CURRENT_THRESHOLD = 20

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
    CHANGING_ONE(BlinkinLedDriver.BlinkinLedMode.SOLID_YELLOW),

    // Blinking Green
    CHANGING_TWO(BlinkinLedDriver.BlinkinLedMode.SOLID_GREEN),

    // TODO
    SPINNING_UP(BlinkinLedDriver.BlinkinLedMode.SOLID_BLACK),

    // TODO
    READY_SHOOT(BlinkinLedDriver.BlinkinLedMode.SOLID_GREEN)
  }
}
