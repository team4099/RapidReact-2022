package com.team4099.robot2022.config.constants

import com.team4099.lib.units.base.feet
import com.team4099.lib.units.base.inches
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.radians

object VisionConstants {
  const val CAMERA_NAME = "photoncamera"

  val CAMERA_HEIGHT = 29.413.meters // TODO: Update with correct value
  val UPPER_HUB_TARGET_HEIGHT = (8.feet + 8.inches) // TODO: Make sure this is correct
  val CAMERA_ANGLE = 60.degrees
  val CAMERA_PITCH = 0.radians

  val TARGET_RANGE = 5.inches // TODO: Update with correct value
  val TARGET_HEIGHT = 0.meters
  val RANGE_THRESHOLD = 2.inches // TODO: Update with correct value

  val GOAL_RANGE_METERS = 0.1.meters
  val FOV_VERTICAL = 30.degrees // TODO: ask for a better value
  val FOV_HORIZONTAL = 0.degrees // TODO: Check if its the correct value

  enum class VisionLedMode {
    AUTO, ALWAYS_ON, ALWAYS_OFF
  }
}
