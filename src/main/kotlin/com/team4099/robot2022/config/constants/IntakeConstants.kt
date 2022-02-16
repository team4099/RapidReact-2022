package com.team4099.robot2022.config.constants

import edu.wpi.first.wpilibj.DoubleSolenoid

object IntakeConstants {
  val RAMP_TIME = 1.0 // Change
  const val TAB = "Intake"
  const val SUPPLY_CURRENT_LIMIT = 30.0

  enum class IntakeState(val speed: Double) {
    IDLE(0.0),
    IN(1.0),
    OUT(-1.0)
  }

  enum class ArmPos(val position: DoubleSolenoid.Value?) {
    OUT(DoubleSolenoid.Value.kReverse),
    IN(DoubleSolenoid.Value.kForward)
  }
}
