package com.team4099.robot2022.config.constants

import com.team4099.lib.units.base.feet
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.base.inches
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.div
import com.team4099.lib.units.derived.radians
import com.team4099.lib.units.derived.volts
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.perSecond
import kotlin.math.sqrt

object DrivetrainConstants {
  const val TICKS = 4096

  const val WHEEL_COUNT = 4
  val WHEEL_DIAMETER = 4.inches
  val DRIVETRAIN_LENGTH = 22.750.inches
  val DRIVETRAIN_WIDTH = 22.750.inches

  val DRIVE_SETPOINT_MAX = 15.feet.perSecond
  val TURN_SETPOINT_MAX =
      (DRIVE_SETPOINT_MAX.inMetersPerSecond / DRIVETRAIN_LENGTH.inMeters / 2 * sqrt(2.0)).radians
          .perSecond // 648

  // cruise velocity in raw sensor units per 100 ms
  val STEERING_VEL_NATIVE_MAX = 1393.2.degrees.perSecond
  val STEERING_ACCEL_NATIVE_MAX = 13932.degrees.perSecond.perSecond

  const val GYRO_RATE_COEFFICIENT = 0.0 // TODO: Change this value

  val MAX_AUTO_VEL = 3.meters.perSecond
  val SLOW_AUTO_VEL = 0.66.meters.perSecond
  val MAX_AUTO_ACCEL = 2.0.meters.perSecond.perSecond

  val MAX_AUTO_ANGULAR_VEL = 90.0.degrees.perSecond
  val MAX_AUTO_ANGULAR_ACCEL = 90.0.degrees.perSecond.perSecond

  const val ABSOLUTE_GEAR_RATIO = 1.0
  const val DRIVE_SENSOR_CPR = 2048 // TODO
  const val STEERING_SENSOR_CPR = 2048 // TODO
  const val DRIVE_SENSOR_GEAR_RATIO = (14.0 / 50.0) * (27.0 / 17.0) * (15.0 / 45.0)
  const val STEERING_SENSOR_GEAR_RATIO = 7.0 / 150.0

  val ALLOWED_ANGLE_ERROR = 1.degrees
  const val STEERING_SUPPLY_CURRENT_LIMIT = 20.0
  const val DRIVE_SUPPLY_CURRENT_LIMIT = 80.0

  val FRONT_LEFT_MODULE_ZERO = 2.687684.radians
  val FRONT_RIGHT_MODULE_ZERO = 5.966769.radians
  val BACK_RIGHT_MODULE_ZERO = 4.608515.radians
  val BACK_LEFT_MODULE_ZERO = 5.204072.radians

  object PID {
    const val STEERING_KP = 0.25
    const val STEERING_KI = 0.0
    const val STEERING_KD = 0.0

    const val STEERING_KFF = 0.0375

    const val DRIVE_KP = 0.0
    const val DRIVE_KI = 0.0
    const val DRIVE_KD = 0.0
    const val DRIVE_KFF = 0.0

    const val AUTO_POS_KP = 0.581 / 6
    const val AUTO_POS_KI = 0.0
    const val AUTO_POS_KD = 0.0 // 263.0 / 6

    const val DRIVE_THETA_PID_KP = 1.0
    const val DRIVE_THETA_PID_KI = 0.0
    const val DRIVE_THETA_PID_KD = 0.0
    val DRIVE_THETA_PID_MAX_VEL = 0.0.meters.perSecond
    val DRIVE_THETA_PID_MAX_ACCEL = 0.0.meters.perSecond.perSecond

    val DRIVE_KS = 0.339.volts
    val DRIVE_KV = 2.78.volts / 1.0.meters.perSecond
    val DRIVE_KA = 0.421.volts / 1.0.meters.perSecond.perSecond
  }
}
