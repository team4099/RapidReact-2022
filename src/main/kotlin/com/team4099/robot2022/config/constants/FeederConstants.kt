package com.team4099.robot2022.config.constants

object FeederConstants {
  // feeder_power temp
  const val FEEDER_POWER = 1.0
  const val TAB = "Feeder"

  // temp values
  const val BEAM_BREAK_BROKEN_TIME = 0.05
  const val BEAM_BREAK_BACKWARDS_TIME = 0.05

  // not final
  enum class FeederState(val floorMotorPower: Double, val verticalMotorPower: Double) {
    FORWARD_ALL(FEEDER_POWER, FEEDER_POWER),
    FORWARD_FLOOR(FEEDER_POWER, 0.0),
    BACKWARD_ALL(-FEEDER_POWER, -FEEDER_POWER),
    BACKWARD_FLOOR(-FEEDER_POWER, 0.0),
    BACKWARD_VERTICAL(0.0, -FEEDER_POWER),
    NEUTRAL(0.0, 0.0),
    SHOOT(FEEDER_POWER, FEEDER_POWER)
  }
}
