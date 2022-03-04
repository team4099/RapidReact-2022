package com.team4099.robot2022.config.constants

import com.team4099.lib.drivers.BlinkinLedDriver

object LEDConstants {
  const val CURRENT_THRESHOLD = 20

  enum class LEDState(val pwmValue: Double) {
    // Solid Orange
    IDLE(BlinkinLedDriver.BlinkinLedMode.SOLID_ORANGE.value),

    // Solid Red
    STANDING_ZERO(BlinkinLedDriver.BlinkinLedMode.SOLID_GOLD.value),

    // Solid Yellow
    STANDING_ONE(BlinkinLedDriver.BlinkinLedMode.SOLID_YELLOW.value),

    // Solid Green
    STANDING_TWO(BlinkinLedDriver.BlinkinLedMode.SOLID_GREEN.value),

    // Blinking Red
    INTAKING(BlinkinLedDriver.BlinkinLedMode.SOLID_BLUE.value),

    // Blinking Yellow
    CHANGING_ONE(0.05),

    // Blinking Green
    CHANGING_TWO(0.25),

    // TODO
    SPINNING_UP(0.00),

    // TODO
    READY_SHOOT(0.00)
  }
}
