package com.team4099.robot2022.commands.climber

import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.robot2022.subsystems.climber.PivotClimber
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.CommandBase

class RunClimbCommand : CommandBase() {
  init {
    addRequirements(TelescopingClimber, PivotClimber)
  }
  var telescopingIsUnderLoad: Boolean = true
  var pivotIsUnderLoad: Boolean = true

  override fun initialize() {
    // TODO figure out isUnderLoad stuff
    //    when(TelescopingClimber.desiredState){
    //      DesiredTelescopeStates.START -> when (PivotClimber.desiredState){
    //        DesiredPivotStates.BEHIND_TELESCOPE -> TelescopingClimber.desiredState =
    // DesiredTelescopeStates.MAX_EXTENSION
    //        DesiredPivotStates.VERTICAL -> PivotClimber.desiredState =
    // DesiredPivotStates.BEHIND_TELESCOPE
    //        else -> Logger.addEvent(TAB, "Illegal Climb State. Telescoping Desired State:
    // ${TelescopingClimber.desiredState}, Pivot Desired State: ${PivotClimber.desiredState} ")
    //      }
    //      DesiredTelescopeStates.MAX_EXTENSION -> when (PivotClimber.desiredState){
    //        DesiredPivotStates.BEHIND_TELESCOPE -> TelescopingClimber.desiredState =
    // DesiredTelescopeStates.MAX_RETRACT
    //        DesiredPivotStates.MID_FORWARD -> TelescopingClimber.desiredState =
    // DesiredTelescopeStates.RELEASE_HOOK
    //        DesiredPivotStates.FULL_FORWARD -> PivotClimber.desiredState =
    // DesiredPivotStates.MID_FORWARD
    //        else -> Logger.addEvent(TAB, "Illegal Climb State. Telescoping Desired State:
    // ${TelescopingClimber.desiredState}, Pivot Desired State: ${PivotClimber.desiredState} ")
    //      }
    //      DesiredTelescopeStates.MAX_RETRACT -> when (PivotClimber.desiredState){
    //        DesiredPivotStates.BEHIND_TELESCOPE -> PivotClimber.desiredState =
    // DesiredPivotStates.VERTICAL
    //        DesiredPivotStates.VERTICAL -> TelescopingClimber.desiredState =
    // DesiredTelescopeStates.RELEASE_HOOK
    //        else -> Logger.addEvent(TAB, "Illegal Climb State. Telescoping Desired State:
    // ${TelescopingClimber.desiredState}, Pivot Desired State: ${PivotClimber.desiredState} ")
    //      }
    //      DesiredTelescopeStates.RELEASE_HOOK -> when (PivotClimber.desiredState){
    //        DesiredPivotStates.BEHIND_TELESCOPE -> TelescopingClimber.desiredState =
    // DesiredTelescopeStates.MAX_RETRACT
    //        DesiredPivotStates.VERTICAL -> PivotClimber.desiredState =
    // DesiredPivotStates.FULL_FORWARD
    //        DesiredPivotStates.MID_FORWARD -> PivotClimber.desiredState =
    // DesiredPivotStates.BEHIND_TELESCOPE
    //        DesiredPivotStates.FULL_FORWARD -> TelescopingClimber.desiredState =
    // DesiredTelescopeStates.MAX_EXTENSION
    //        else -> Logger.addEvent(TAB, "Illegal Climb State. Telescoping Desired State:
    // ${TelescopingClimber.desiredState}, Pivot Desired State: ${PivotClimber.desiredState} ")
    //      }
    //      else -> Logger.addEvent(TAB, "Illegal Climb State. Telescoping Desired State:
    // ${TelescopingClimber.desiredState}, Pivot Desired State: ${PivotClimber.desiredState} ")
    //    }
  }
  override fun execute() {
    val leftTelescopingProfile =
        TrapezoidProfile(
            TelescopingClimber.constraints,
            TrapezoidProfile.State(TelescopingClimber.desiredState.position.inMeters, 0.0),
            TrapezoidProfile.State(
                TelescopingClimber.telescopingLeftArmSensor.position.inMeters,
                TelescopingClimber.telescopingLeftArmSensor.velocity.inMetersPerSecond))
    val rightTelescopingProfile =
        TrapezoidProfile(
            TelescopingClimber.constraints,
            TrapezoidProfile.State(TelescopingClimber.desiredState.position.inMeters, 0.0),
            TrapezoidProfile.State(
                TelescopingClimber.telescopingRightArmSensor.position.inMeters,
                TelescopingClimber.telescopingRightArmSensor.velocity.inMetersPerSecond))
    TelescopingClimber.setPosition(
        leftTelescopingProfile, rightTelescopingProfile, telescopingIsUnderLoad)

    val leftPivotProfile =
        TrapezoidProfile(
            PivotClimber.constraints,
            TrapezoidProfile.State(PivotClimber.desiredState.angle.inRadians, 0.0),
            TrapezoidProfile.State(
                PivotClimber.pivotLeftArmSensor.position.inRadians,
                PivotClimber.pivotLeftArmSensor.velocity.inRadiansPerSecond))
    val rightPivotProfile =
        TrapezoidProfile(
            PivotClimber.constraints,
            TrapezoidProfile.State(PivotClimber.desiredState.angle.inRadians, 0.0),
            TrapezoidProfile.State(
                PivotClimber.pivotRightArmSensor.position.inRadians,
                PivotClimber.pivotRightArmSensor.velocity.inRadiansPerSecond))
    PivotClimber.setAngle(leftPivotProfile, rightPivotProfile, pivotIsUnderLoad)
  }

  override fun isFinished(): Boolean {
    return TelescopingClimber.currentState.correspondingDesiredState ==
        TelescopingClimber.desiredState &&
        PivotClimber.currentState.correspondingDesiredState == PivotClimber.desiredState
  }
}
