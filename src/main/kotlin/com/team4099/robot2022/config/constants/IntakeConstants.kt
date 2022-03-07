package com.team4099.robot2022.config.constants

object IntakeConstants {
  val RAMP_TIME = 1.0 // Change
  const val TAB = "Intake"
  const val SUPPLY_CURRENT_LIMIT = 30.0
  const val SENSOR_CPR = 2048
  const val GEAR_RATIO = (12.0 / 36.0)

  enum class RollerState(val speed: Double) {
    IDLE(-0.1),
    IN(0.86),
    OUT(-1.0)
  }

  enum class ArmState(val out: Boolean) {
    OUT(true),
    IN(false)
  }
}
