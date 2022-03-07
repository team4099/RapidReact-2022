package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.ClimberConstants.DesiredPivotStates
import com.team4099.robot2022.config.constants.ClimberConstants.DesiredTelescopeStates
import com.team4099.robot2022.config.constants.ClimberConstants.TAB
import com.team4099.robot2022.subsystems.climber.PivotClimber
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class AdvanceClimberCommand(
  val telescopingClimber: TelescopingClimber,
  val pivotClimber: PivotClimber
) : CommandBase() {
  init {
    addRequirements(telescopingClimber, pivotClimber)
  }
  override fun initialize() {
    when (telescopingClimber.desiredState) {
      DesiredTelescopeStates.START ->
          when (pivotClimber.desiredState) {
            DesiredPivotStates.BEHIND_TELESCOPE ->
                telescopingClimber.desiredState = DesiredTelescopeStates.MAX_EXTENSION
            DesiredPivotStates.VERTICAL ->
                pivotClimber.desiredState = DesiredPivotStates.BEHIND_TELESCOPE
            else ->
                Logger.addEvent(
                    TAB,
                    "Illegal Climb State. " +
                        "Telescoping Desired State: ${telescopingClimber.desiredState}, " +
                        "Pivot Desired State: ${pivotClimber.desiredState}")
          }
      DesiredTelescopeStates.MAX_EXTENSION ->
          when (pivotClimber.desiredState) {
            DesiredPivotStates.BEHIND_TELESCOPE ->
                telescopingClimber.desiredState = DesiredTelescopeStates.MAX_RETRACT
            DesiredPivotStates.MID_FORWARD ->
                telescopingClimber.desiredState = DesiredTelescopeStates.RELEASE_HOOK
            DesiredPivotStates.FULL_FORWARD ->
                pivotClimber.desiredState = DesiredPivotStates.MID_FORWARD
            else ->
                Logger.addEvent(
                    TAB,
                    "Illegal Climb State." +
                        " Telescoping Desired State: ${telescopingClimber.desiredState}," +
                        " Pivot Desired State: ${pivotClimber.desiredState} ")
          }
      DesiredTelescopeStates.MAX_RETRACT ->
          when (pivotClimber.desiredState) {
            DesiredPivotStates.BEHIND_TELESCOPE ->
                pivotClimber.desiredState = DesiredPivotStates.VERTICAL
            DesiredPivotStates.VERTICAL ->
                telescopingClimber.desiredState = DesiredTelescopeStates.RELEASE_HOOK
            else ->
                Logger.addEvent(
                    TAB,
                    "Illegal Climb State." +
                        " Telescoping Desired State: ${telescopingClimber.desiredState}," +
                        " Pivot Desired State: ${pivotClimber.desiredState} ")
          }
      DesiredTelescopeStates.RELEASE_HOOK ->
          when (pivotClimber.desiredState) {
            DesiredPivotStates.BEHIND_TELESCOPE ->
                telescopingClimber.desiredState = DesiredTelescopeStates.MAX_RETRACT
            DesiredPivotStates.VERTICAL ->
                pivotClimber.desiredState = DesiredPivotStates.FULL_FORWARD
            DesiredPivotStates.MID_FORWARD ->
                pivotClimber.desiredState = DesiredPivotStates.BEHIND_TELESCOPE
            DesiredPivotStates.FULL_FORWARD ->
                telescopingClimber.desiredState = DesiredTelescopeStates.MAX_EXTENSION
            else ->
                Logger.addEvent(
                    TAB,
                    "Illegal Climb State. " +
                        "Telescoping Desired State: ${telescopingClimber.desiredState}, " +
                        "Pivot Desired State: ${pivotClimber.desiredState} ")
          }
      else ->
          Logger.addEvent(
              TAB,
              "Illegal Climb State. " +
                  "Telescoping Desired State: ${telescopingClimber.desiredState}, " +
                  "Pivot Desired State: ${pivotClimber.desiredState} ")
    }
  }

  override fun isFinished(): Boolean {
    return true
  }
}
