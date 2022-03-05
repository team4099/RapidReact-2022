package com.team4099.robot2022.config.constants

import com.team4099.lib.units.base.Length
import com.team4099.lib.units.base.inches
import com.team4099.lib.units.derived.Angle
import com.team4099.lib.units.derived.degrees

object ClimberConstants {
  enum class DesiredTelescopeStates(val position: Length) {
    START(0.0.inches),
    MAX_EXTENSION(66.inches),
    MAX_RETRACT(33.inches),
    RELEASE_HOOK(40.inches),
    DUMMY(-Double.NEGATIVE_INFINITY.inches)
  }
  enum class DesiredPivotStates(val angle: Angle) {
    BEHIND_TELESCOPE(-30.degrees),
    VERTICAL(0.degrees),
    MID_FORWARD(15.degrees),
    FULL_FORWARD(30.degrees),
    DUMMY(-Double.NEGATIVE_INFINITY.degrees)
  }

  enum class ActualTelescopeStates(val correspondingDesiredState: DesiredTelescopeStates) {
    BELOW_START(DesiredTelescopeStates.DUMMY),
    START(DesiredTelescopeStates.START),
    BETWEEN_START_AND_MAX_RETRACT(DesiredTelescopeStates.DUMMY),
    MAX_RETRACT(DesiredTelescopeStates.MAX_RETRACT),
    BETWEEN_MAX_RETRACT_AND_RELEASE_HOOK(DesiredTelescopeStates.DUMMY),
    RELEASE_HOOK(DesiredTelescopeStates.RELEASE_HOOK),
    BETWEEN_RELEASE_HOOK_AND_MAX_EXTENSION(DesiredTelescopeStates.DUMMY),
    MAX_EXTENSION(DesiredTelescopeStates.MAX_EXTENSION),
    ABOVE_MAX_EXTENSION(DesiredTelescopeStates.DUMMY),
  }
  enum class ActualPivotStates(val correspondingDesiredState: DesiredPivotStates) {
    BEHIND_BEHIND_TELESCOPE(DesiredPivotStates.DUMMY),
    BEHIND_TELESCOPE(DesiredPivotStates.BEHIND_TELESCOPE),
    BETWEEN_BEHIND_TELESCOPE_AND_VERTICAL(DesiredPivotStates.DUMMY),
    VERTICAL(DesiredPivotStates.VERTICAL),
    BETWEEN_VERTICAL_AND_MID_FORWARD(DesiredPivotStates.DUMMY),
    MID_FORWARD(DesiredPivotStates.MID_FORWARD),
    BETWEEN_MID_FORWARD_AND_FULL_FORWARD(DesiredPivotStates.DUMMY),
    FULL_FORWARD(DesiredPivotStates.FULL_FORWARD),
    PAST_FULL_FORWARD(DesiredPivotStates.DUMMY)
  }

  val telescopingTolerance = 1.inches
  val pivotTolerance = 5.degrees

  val TAB = "Climber"
}
