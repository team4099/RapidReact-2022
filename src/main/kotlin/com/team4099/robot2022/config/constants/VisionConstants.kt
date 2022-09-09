package com.team4099.robot2022.config.constants

import com.team4099.lib.units.base.feet
import com.team4099.lib.units.base.inches
import com.team4099.lib.units.derived.degrees

object VisionConstants {
  const val CAMERA_NAME = "photoncamera"

  val CAMERA_HEIGHT = 29.413.inches // TODO: Update with correct value
  val UPPER_HUB_TARGET_HEIGHT = (8.feet + 8.inches) // TODO: Make sure this is correct
  val CAMERA_ANGLE = 60.degrees

  val LIMELIGHT_FOV_HORIZONTAL = 59.6.degrees
  val LIMELIGHT_FOV_VERTICAL = 49.7.degrees

  val MINIMUM_TARGET_COUNT_THRESHOLD = 2


  val TARGET_RANGE = 5.inches // TODO: Update with correct value
  val RANGE_THRESHOLD = 2.inches // TODO: Update with correct value
}
