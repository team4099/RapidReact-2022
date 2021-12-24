package com.team4099.robot2022.config

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

    const val THROTTLE_DEADBAND = 0.05
    const val TURN_DEADBAND = 0.05
  }
}
