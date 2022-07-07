package com.team4099.robot2022.config.constants

import com.team4099.lib.units.base.feet
import com.team4099.lib.units.base.inches
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.perSecond

object VisionConstants {
  const val CAMERA_NAME = "photoncamera"

  val CAMERA_HEIGHT = 29.413.inches // TODO: Update with correct value
  val UPPER_HUB_TARGET_HEIGHT = (8.feet + 8.inches) // TODO: Make sure this is correct
  val CAMERA_ANGLE = 60.degrees

  val TARGET_RANGE = 5.inches // TODO: Update with correct value
  val RANGE_THRESHOLD = 2.inches // TODO: Update with correct value

  val MAX_DIST_ERROR = 0.1.inches
  val MAX_ANGLE_ERROR = 1.0.degrees

  const val MIN_TURN_COMMAND = 20.0

  object TurnGains {
    const val KP = 8.0
    const val KI = 0.0
    const val KD = 0.8

    val MAX_VELOCITY = 90.0.degrees.perSecond
    val MAX_ACCEL = 450.0.degrees.perSecond.perSecond
  }
}
