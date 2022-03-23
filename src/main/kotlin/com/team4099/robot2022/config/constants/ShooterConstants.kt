package com.team4099.robot2022.config.constants

import com.team4099.lib.units.AngularVelocity
import com.team4099.lib.units.base.inches
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.div
import com.team4099.lib.units.derived.rotations
import com.team4099.lib.units.perMinute

object ShooterConstants {
  // Motor configuration
  const val LEADER_MOTOR_ID = 51 // put in values from the design tool (leader motor is left)
  const val FOLLOWER_MOTOR_ID = 52

  const val SHOOTER_KP = 0.075 // TODO: tune
  const val SHOOTER_KI = 0.0
  const val SHOOTER_KD = 7.0
  val SHOOTER_KS_VOLTS = 0.7
  val SHOOTER_KV_VOLTS_PER_RADIAN_PER_SECOND = 0.018

  const val SHOOTER_SENSOR_CPR = 2048
  const val SHOOTER_SENSOR_GEAR_RATIO = 1.0

  const val SUPPLY_CURRENT_LIMIT = 60.0

  const val FILTER_SIZE = 10

  //  val FOLLOWER_STATUS_FRAME_PERIOD = 255.milli.seconds

  // Target values
  val TARGET_VELOCITY_THRESHOLD = 50.rotations.perMinute

  // Physical measurements
  val ANGLE = 80.0.degrees
  val SHOOTER_HEIGHT = 33.0.inches
  val FLYWHEEL_RADIUS = 2.0.inches

  enum class ShooterState(val targetVelocity: AngularVelocity) {
    OFF(0.0.rotations.perMinute),
    IDLE(500.0.rotations.perMinute), // TODO: Fix with a better idle value
    SPIN_UP_NEAR(1850.0.rotations.perMinute),
    SPIN_UP_LOWER(1100.0.rotations.perMinute),
    SHOOTER_UNJAM(3000.0.rotations.perMinute)
  }
}
