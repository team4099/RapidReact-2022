package com.team4099.robot2022.config.constants

object IntakeConstants {
  val RAMP_TIME = 1.0 // Change
  const val TAB = "Intake"
  const val SUPPLY_CURRENT_LIMIT = 30.0

  enum class IntakeState(val speed: Double) {
    IDLE(0.0),
    IN(1.0),
    OUT(-1.0)
  }

  enum class ArmPos(val out: Boolean) {
    OUT(true),
    IN(false)
  }
}
