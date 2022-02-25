package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.ClimberConstants.DesiredPivotStates
import com.team4099.robot2022.config.constants.ClimberConstants.DesiredTelescopeStates
import com.team4099.robot2022.config.constants.ClimberConstants.TAB
import com.team4099.robot2022.subsystems.PivotClimber
import com.team4099.robot2022.subsystems.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class AdvanceClimberCommand : CommandBase() {
  init {
    addRequirements(TelescopingClimber, PivotClimber)
  }
  override fun initialize() {
    when (TelescopingClimber.desiredState) {
      DesiredTelescopeStates.START ->
          when (PivotClimber.desiredState) {
            DesiredPivotStates.BEHIND_TELESCOPE ->
                TelescopingClimber.desiredState = DesiredTelescopeStates.MAX_EXTENSION
            DesiredPivotStates.VERTICAL ->
                PivotClimber.desiredState = DesiredPivotStates.BEHIND_TELESCOPE
            else ->
                Logger.addEvent(
                    TAB,
                    "Illegal Climb State. " +
                        "Telescoping Desired State: ${TelescopingClimber.desiredState}, " +
                        "Pivot Desired State: ${PivotClimber.desiredState}")
          }
      DesiredTelescopeStates.MAX_EXTENSION ->
          when (PivotClimber.desiredState) {
            DesiredPivotStates.BEHIND_TELESCOPE ->
                TelescopingClimber.desiredState = DesiredTelescopeStates.MAX_RETRACT
            DesiredPivotStates.MID_FORWARD ->
                TelescopingClimber.desiredState = DesiredTelescopeStates.RELEASE_HOOK
            DesiredPivotStates.FULL_FORWARD ->
                PivotClimber.desiredState = DesiredPivotStates.MID_FORWARD
            else ->
                Logger.addEvent(
                    TAB,
                    "Illegal Climb State." +
                        " Telescoping Desired State: ${TelescopingClimber.desiredState}," +
                        " Pivot Desired State: ${PivotClimber.desiredState} ")
          }
      DesiredTelescopeStates.MAX_RETRACT ->
          when (PivotClimber.desiredState) {
            DesiredPivotStates.BEHIND_TELESCOPE ->
                PivotClimber.desiredState = DesiredPivotStates.VERTICAL
            DesiredPivotStates.VERTICAL ->
                TelescopingClimber.desiredState = DesiredTelescopeStates.RELEASE_HOOK
            else ->
                Logger.addEvent(
                    TAB,
                    "Illegal Climb State." +
                        " Telescoping Desired State: ${TelescopingClimber.desiredState}," +
                        " Pivot Desired State: ${PivotClimber.desiredState} ")
          }
      DesiredTelescopeStates.RELEASE_HOOK ->
          when (PivotClimber.desiredState) {
            DesiredPivotStates.BEHIND_TELESCOPE ->
                TelescopingClimber.desiredState = DesiredTelescopeStates.MAX_RETRACT
            DesiredPivotStates.VERTICAL ->
                PivotClimber.desiredState = DesiredPivotStates.FULL_FORWARD
            DesiredPivotStates.MID_FORWARD ->
                PivotClimber.desiredState = DesiredPivotStates.BEHIND_TELESCOPE
            DesiredPivotStates.FULL_FORWARD ->
                TelescopingClimber.desiredState = DesiredTelescopeStates.MAX_EXTENSION
            else ->
                Logger.addEvent(
                    TAB,
                    "Illegal Climb State. " +
                        "Telescoping Desired State: ${TelescopingClimber.desiredState}, " +
                        "Pivot Desired State: ${PivotClimber.desiredState} ")
          }
      else ->
          Logger.addEvent(
              TAB,
              "Illegal Climb State. " +
                  "Telescoping Desired State: ${TelescopingClimber.desiredState}, " +
                  "Pivot Desired State: ${PivotClimber.desiredState} ")
    }
  }

  override fun isFinished(): Boolean {
    return true
  }
}
