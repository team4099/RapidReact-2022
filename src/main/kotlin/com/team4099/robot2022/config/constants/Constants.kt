package com.team4099.robot2022.config.constants

import com.team4099.lib.units.derived.div

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

  object Joysticks {
    const val DRIVER_PORT = 0
    const val SHOTGUN_PORT = 1
    const val TECHNICIAN_PORT = 2

    const val THROTTLE_DEADBAND = 0.05
    const val TURN_DEADBAND = 0.05
  }

  object Drivetrain {
    const val FRONT_LEFT_DRIVE_ID = 11
    const val FRONT_LEFT_STEERING_ID = 21
    const val FRONT_LEFT_ANALOG_POTENTIOMETER = 0

    const val FRONT_RIGHT_DRIVE_ID = 12
    const val FRONT_RIGHT_STEERING_ID = 22
    const val FRONT_RIGHT_ANALOG_POTENTIOMETER = 1

    const val BACK_RIGHT_DRIVE_ID = 13
    const val BACK_RIGHT_STEERING_ID = 23
    const val BACK_RIGHT_ANALOG_POTENTIOMETER = 2

    const val BACK_LEFT_DRIVE_ID = 14
    const val BACK_LEFT_STEERING_ID = 24
    const val BACK_LEFT_ANALOG_POTENTIOMETER = 3
  }

  object Feeder {
    const val FLOOR_MOTOR_ID = 61
    const val VERTICAL_MOTOR_ID = 62

    const val TOP_DIO_PIN = 9
    const val BOTTOM_DIO_PIN = 8
  }

  object Intake {
    const val INTAKE_MOTOR = 31
    const val ARM_SOLENOID = 8
  }

  object TelescopingClimber {
    const val SOLENOID_ID = 0
    const val R_ARM_ID = 42
    const val L_ARM_ID = 41
  }

  object PivotClimber {
    const val L_ARM_ID = 43
    const val R_ARM_ID = 44
  }
}
