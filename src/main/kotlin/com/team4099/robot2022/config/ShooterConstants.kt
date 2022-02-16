package com.team4099.robot2022.config

import com.team4099.lib.units.base.inches
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.rotations
import com.team4099.lib.units.perMinute

object ShooterConstants {
  // Motor configuration
  const val LEADER_MOTOR_ID = 51 // put in values from the design tool (leader motor is left)
  const val FOLLOWER_MOTOR_ID = 52

  const val SHOOTER_KP = 0.5 // TODO: tune
  const val SHOOTER_KI = 0.0
  const val SHOOTER_KD = 0.0

  const val SHOOTER_SENSOR_CPR = 2048
  const val SHOOTER_SENSOR_GEAR_RATIO = 1.0

  // Target values
  val TARGET_VELOCITY = 1200.rotations.perMinute // TODO: Update with correct value
  val TARGET_VELOCITY_THRESHOLD = 150.rotations.perMinute

  // Physical measurements
  val ANGLE = 80.0.degrees
  val SHOOTER_HEIGHT = 33.0.inches
  val FLYWHEEL_RADIUS = 2.0.inches

  enum class ShooterState(public val rotationsPerMinute: Double) {
    OFF(0.0),
    IDLE(500.0), // TODO: Fix with a better idle value
    SPIN_UP(1200.0)
  }
}
