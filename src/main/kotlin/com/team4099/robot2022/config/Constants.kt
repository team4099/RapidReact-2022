package com.team4099.robot2022.config

import com.team4099.lib.units.base.feet
import com.team4099.lib.units.base.inches
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.div
import com.team4099.lib.units.derived.volts
import com.team4099.lib.units.perSecond

object Constants {
  object Universal {
    const val CTRE_CONFIG_TIMEOUT = 0
    const val EPSILON = 1E-9
  }

  object Tuning {
    const val TUNING_TOGGLE_PIN = 0
    val ROBOT_ID_PINS = 1..2

    enum class RobotName {
      COMPETITION,
      PRACTICE,
      MULE
    }

    val ROBOT_ID_MAP =
        mapOf<Int, RobotName>(
            0 to RobotName.COMPETITION, 1 to RobotName.PRACTICE, 2 to RobotName.MULE)
  }

  object Drivetrain {
    const val TICKS = 4096

    const val FRONT_LEFT_DRIVE_ID = 11
    const val FRONT_LEFT_STEERING_ID = 21
    const val FRONT_LEFT_ANALOG_POTENTIOMETER = 2

    const val FRONT_RIGHT_DRIVE_ID = 12
    const val FRONT_RIGHT_STEERING_ID = 22
    const val FRONT_RIGHT_ANALOG_POTENTIOMETER = 1

    const val BACK_RIGHT_DRIVE_ID = 13
    const val BACK_RIGHT_STEERING_ID = 23
    const val BACK_RIGHT_ANALOG_POTENTIOMETER = 4

    const val BACK_LEFT_DRIVE_ID = 14
    const val BACK_LEFT_STEERING_ID = 24
    const val BACK_LEFT_ANALOG_POTENTIOMETER = 3

    const val WHEEL_COUNT = 4
    val WHEEL_DIAMETER = 4.inches
    val DRIVETRAIN_LENGTH = 22.750.inches
    val DRIVETRAIN_WIDTH = 22.750.inches

    val DRIVE_SETPOINT_MAX = 15.feet.perSecond
    val TURN_SETPOINT_MAX = 360.degrees.perSecond // TODO: Make sure this value is something good

    val STEERING_VEL_MAX = 900.degrees.perSecond
    val STEERING_ACCEL_MAX = 4500.degrees.perSecond.perSecond

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

    object Gains {
      const val RAMSETE_B = 2.0
      const val RAMSETE_ZETA = 0.7
    }

    object PID {
      const val STEERING_KP = 0.00001
      const val STEERING_KI = 0.0
      const val STEERING_KD = 12.0
      const val STEERING_KFF = 0.000078

      const val DRIVE_KP = 0.000129
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

  object Joysticks {
    const val DRIVER_PORT = 0
    const val SHOTGUN_PORT = 1

    const val THROTTLE_DEADBAND = 0.05
    const val TURN_DEADBAND = 0.05
  }

  object VisionConstants {
    const val CAMERA_NAME = "photoncamera"

    val CAMERA_HEIGHT = 29.413.inches // TODO: Update with correct value
    val UPPER_HUB_TARGET_HEIGHT = (8.feet + 8.inches) // TODO: Make sure this is correct
    val CAMERA_ANGLE = 60.degrees

    val TARGET_RANGE = 5.inches // TODO: Update with correct value
    val RANGE_THRESHOLD = 2.inches // TODO: Update with correct value
  }
}
