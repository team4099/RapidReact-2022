package com.team4099.robot2022.config.constants

import com.team4099.lib.units.Value
import com.team4099.lib.units.base.Length
import com.team4099.lib.units.base.Second
import com.team4099.lib.units.base.Time
import com.team4099.lib.units.base.inches
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.base.seconds
import com.team4099.lib.units.perSecond

object TelescopingClimberConstants {
  const val SENSOR_CPR = 2048
  const val GEAR_RATIO = (8.0 / 54.0) * (28.0 / 44.0)

  const val KP = 0.0
  const val KI = 0.0
  const val KD = 0.0
  const val KFF = 0.0

  val NO_LOAD_KS = 0.0
  val NO_LOAD_KV = 0.0
  val NO_LOAD_KA = 0.0

  val LOAD_KS = 0.0
  val LOAD_KV = 0.0
  val LOAD_KA = 0.0

  val SPOOL_RADIUS = 0.5.inches
  val MAX_VELOCITY = 0.0.meters.perSecond
  val MAX_ACCELERATION = 0.0.meters.perSecond.perSecond

  const val BOTTOM_SAFETY_THRESHOLD = 0
  const val TOP_SAFETY_THRESHOLD = 0

  enum class TelescopingArmPosition(val length: Length) {
    LOW(0.meters),
    HIGH(1.000.meters) // extended, value estimated for now
  }

  val FORWARD_SOFT_LIMIT = 22.5.inches
  val REVERSE_SOFT_LIMIT = 0.inches

  const val TAB = "Telescoping Climber"
}
