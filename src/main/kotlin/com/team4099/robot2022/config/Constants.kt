package com.team4099.robot2022.config

import com.team4099.lib.units.Value
import com.team4099.lib.units.base.Length
import com.team4099.lib.units.base.Second
import com.team4099.lib.units.base.feet
import com.team4099.lib.units.base.inches
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.base.seconds
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

  //placeholder values
  object TelescopingClimber {
    const val TELESCOPING_R_ARM_ID = 0
    const val TELESCOPING_L_ARM_ID = 0
    const val TELESCOPING_SOLENOID_ID = 0

    const val TELESCOPING_BOTTOM_SAFETY_THRESHOLD = 0
    const val TELESCOPING_TOP_SAFETY_THRESHOLD = 0

    const val TELESCOPING_POSITION = 0

    val TELESCOPING_BRAKE_RELEASE_TIMEOUT: Value<Second> = 0.0.seconds

    const val TAB = "Climber"
  }

  //temp values
  object PivotClimber {
    const val PIVOT_R_ARM_ID = 0
    const val PIVOT_L_ARM_ID = 0

    val PIVOT_BRAKE_RELEASE_TIMEOUT: Value<Second> = 0.0.seconds

    const val TAB = "Climber"
  }

  object Drivetrain {
    const val TICKS = 4096

    const val FRONT_LEFT_SPEED_ID = 11
    const val FRONT_LEFT_DIRECTION_ID = 21
    const val FRONT_LEFT_CANCODER_ID = 2

    const val FRONT_RIGHT_SPEED_ID = 12
    const val FRONT_RIGHT_DIRECTION_ID = 22
    const val FRONT_RIGHT_CANCODER_ID = 1

    const val BACK_RIGHT_SPEED_ID = 13
    const val BACK_RIGHT_DIRECTION_ID = 23
    const val BACK_RIGHT_CANCODER_ID = 4

    const val BACK_LEFT_SPEED_ID = 14
    const val BACK_LEFT_DIRECTION_ID = 24
    const val BACK_LEFT_CANCODER_ID = 3

    const val WHEEL_COUNT = 4
    val DRIVETRAIN_LENGTH = 22.173.inches
    val DRIVETRAIN_WIDTH = 22.173.inches

    val DRIVE_SETPOINT_MAX = 15.feet.perSecond
    val TURN_SETPOINT_MAX = 360.degrees.perSecond // TODO: Make sure this value is something good

    val DIRECTION_VEL_MAX = 900.degrees.perSecond
    val DIRECTION_ACCEL_MAX = 4500.degrees.perSecond.perSecond

    const val GYRO_RATE_COEFFICIENT = 0.0 // TODO: Change this value

    val MAX_AUTO_VEL = 3.meters.perSecond
    val SLOW_AUTO_VEL = 0.66.meters.perSecond
    val MAX_AUTO_ACCEL = 2.0.meters.perSecond.perSecond

    val MAX_AUTO_ANGULAR_VEL = 90.0.degrees.perSecond
    val MAX_AUTO_ANGULAR_ACCEL = 90.0.degrees.perSecond.perSecond

    const val ABSOLUTE_GEAR_RATIO = 1.0
    const val DRIVE_SENSOR_GEAR_RATIO = (12.0 / 21.0) * (15.0 / 45.0)
    const val DIRECTION_SENSOR_GEAR_RATIO = (12.0 / 64.0) * (1.0 / 10.0)

    val ALLOWED_ANGLE_ERROR = 1.degrees
    const val DIRECTION_SMART_CURRENT_LIMIT = 20
    const val DRIVE_SMART_CURRENT_LIMIT = 80

    object Gains {
      const val RAMSETE_B = 2.0
      const val RAMSETE_ZETA = 0.7
    }

    object PID {
      const val DIRECTION_KP = 0.00001
      const val DIRECTION_KI = 0.0
      const val DIRECTION_KD = 12.0
      const val DIRECTION_KFF = 0.000078

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

  enum class TelescopingArmPosition(val length: Length) {
    LOW(0.meters),
    HIGH(1.000.meters) // extended, value estimated for now
  }

  enum class PivotArmPosition {
    //idle state to mark on feeder
    //extended to rung
    //pivoting
  }
}
