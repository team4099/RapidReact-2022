package com.team4099.robot2022.config.constants

import com.team4099.lib.units.base.Length
import com.team4099.lib.units.base.inches
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.derived.div
import com.team4099.lib.units.derived.volts
import com.team4099.lib.units.perSecond

object TelescopingClimberConstants {
  const val SENSOR_CPR = 2048
  const val GEAR_RATIO = (8.0 / 54.0) * (28.0 / 44.0)

  const val KP = 0.0
  const val KI = 0.0
  const val KD = 0.0
  const val KFF = 0.0

  val NO_LOAD_KS = 1.824.volts
  val NO_LOAD_KG = (-1.8496).volts
  val NO_LOAD_KV = 13.762.volts / 1.0.meters.perSecond
  val NO_LOAD_KA = 0.64434.volts / 1.0.meters.perSecond.perSecond

  val LOAD_KS = 0.0.volts
  val LOAD_KG = 0.88.volts
  val LOAD_KV = 2.83.volts / 1.0.meters.perSecond
  val LOAD_KA = 0.11.volts / 1.0.meters.perSecond.perSecond

  val SPOOL_RADIUS = 0.5.inches
  val LEFT_SPOOL_RADIUS = 0.5.inches
  val RIGHT_SPOOL_RADIUS = 0.5.inches
  val MAX_VELOCITY = 0.0.meters.perSecond
  val MAX_ACCELERATION = 0.0.meters.perSecond.perSecond

  const val BOTTOM_SAFETY_THRESHOLD = 0
  const val TOP_SAFETY_THRESHOLD = 0

  enum class TelescopingArmPosition(val length: Length) {
    LOW(0.meters),
    HIGH(1.000.meters) // extended, value estimated for now
  }

  val FORWARD_SOFT_LIMIT = 27.inches // old one was 24 inches
  val SLOW_TELESCOPING_THRESHOLD = 5.inches
  val REVERSE_SOFT_LIMIT = 0.inches

  const val TAB = "Telescoping Climber"
}
