package com.team4099.robot2022.config.constants

object LEDConstants {
  const val CURRENT_THRESHOLD = 12

  enum class LEDState(val pwmValue: Double) {
    // Solid Orange
    IDLE(0.65),

    // Solid Red
    STANDING_ZERO(0.61),

    // Solid Yellow
    STANDING_ONE(0.69),

    // Solid Green
    STANDING_TWO(0.77),

    // Blinking Red
    CHANGING_ZERO(-0.25),

    // Blinking Yellow
    CHANGING_ONE(0.05),

    // Blinking Green
    CHANGING_TWO(0.25)
  }
}
