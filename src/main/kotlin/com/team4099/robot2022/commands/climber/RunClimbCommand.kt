package com.team4099.robot2022.commands.climberPivotClimber

import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.robot2022.subsystems.climber.PivotClimber
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.CommandBase

class RunClimbCommand(val telescopingClimber: TelescopingClimber, val pivotClimber: PivotClimber) :
    CommandBase() {
  init {
    addRequirements(telescopingClimber, pivotClimber)
  }
  var telescopingIsUnderLoad: Boolean = true
  var pivotIsUnderLoad: Boolean = true

  override fun initialize() {
    // TODO figure out isUnderLoad stuff
    //    when(telescopingClimber.desiredState){
    //      DesiredTelescopeStates.START -> when (pivotClimber.desiredState){
    //        DesiredPivotStates.BEHIND_TELESCOPE -> telescopingClimber.desiredState =
    // DesiredTelescopeStates.MAX_EXTENSION
    //        DesiredPivotStates.VERTICAL -> pivotClimber.desiredState =
    // DesiredPivotStates.BEHIND_TELESCOPE
    //        else -> Logger.addEvent(TAB, "Illegal Climb State. Telescoping Desired State:
    // ${telescopingClimber.desiredState}, Pivot Desired State: ${pivotClimber.desiredState} ")
    //      }
    //      DesiredTelescopeStates.MAX_EXTENSION -> when (pivotClimber.desiredState){
    //        DesiredPivotStates.BEHIND_TELESCOPE -> telescopingClimber.desiredState =
    // DesiredTelescopeStates.MAX_RETRACT
    //        DesiredPivotStates.MID_FORWARD -> telescopingClimber.desiredState =
    // DesiredTelescopeStates.RELEASE_HOOK
    //        DesiredPivotStates.FULL_FORWARD -> pivotClimber.desiredState =
    // DesiredPivotStates.MID_FORWARD
    //        else -> Logger.addEvent(TAB, "Illegal Climb State. Telescoping Desired State:
    // ${telescopingClimber.desiredState}, Pivot Desired State: ${pivotClimber.desiredState} ")
    //      }
    //      DesiredTelescopeStates.MAX_RETRACT -> when (pivotClimber.desiredState){
    //        DesiredPivotStates.BEHIND_TELESCOPE -> pivotClimber.desiredState =
    // DesiredPivotStates.VERTICAL
    //        DesiredPivotStates.VERTICAL -> telescopingClimber.desiredState =
    // DesiredTelescopeStates.RELEASE_HOOK
    //        else -> Logger.addEvent(TAB, "Illegal Climb State. Telescoping Desired State:
    // ${telescopingClimber.desiredState}, Pivot Desired State: ${pivotClimber.desiredState} ")
    //      }
    //      DesiredTelescopeStates.RELEASE_HOOK -> when (pivotClimber.desiredState){
    //        DesiredPivotStates.BEHIND_TELESCOPE -> telescopingClimber.desiredState =
    // DesiredTelescopeStates.MAX_RETRACT
    //        DesiredPivotStates.VERTICAL -> pivotClimber.desiredState =
    // DesiredPivotStates.FULL_FORWARD
    //        DesiredPivotStates.MID_FORWARD -> pivotClimber.desiredState =
    // DesiredPivotStates.BEHIND_TELESCOPE
    //        DesiredPivotStates.FULL_FORWARD -> telescopingClimber.desiredState =
    // DesiredTelescopeStates.MAX_EXTENSION
    //        else -> Logger.addEvent(TAB, "Illegal Climb State. Telescoping Desired State:
    // ${telescopingClimber.desiredState}, Pivot Desired State: ${pivotClimber.desiredState} ")
    //      }
    //      else -> Logger.addEvent(TAB, "Illegal Climb State. Telescoping Desired State:
    // ${telescopingClimber.desiredState}, Pivot Desired State: ${pivotClimber.desiredState} ")
    //    }
  }
  override fun execute() {
    val leftTelescopingProfile =
        TrapezoidProfile(
            telescopingClimber.constraints,
            TrapezoidProfile.State(telescopingClimber.desiredState.position.inMeters, 0.0),
            TrapezoidProfile.State(
                telescopingClimber.inputs.leftPosition.inMeters,
                telescopingClimber.inputs.leftVelocity.inMetersPerSecond))
    val rightTelescopingProfile =
        TrapezoidProfile(
            telescopingClimber.constraints,
            TrapezoidProfile.State(telescopingClimber.desiredState.position.inMeters, 0.0),
            TrapezoidProfile.State(
                telescopingClimber.inputs.rightPosition.inMeters,
                telescopingClimber.inputs.rightVelocity.inMetersPerSecond))
    telescopingClimber.setPosition(
        leftTelescopingProfile, rightTelescopingProfile, telescopingIsUnderLoad)

    //    val leftPivotProfile =
    //        TrapezoidProfile(
    //            pivotClimber.constraints,
    //            TrapezoidProfile.State(pivotClimber.desiredState.angle.inRadians, 0.0),
    //            TrapezoidProfile.State(
    //                pivotClimber.pivotLeftArmSensor.position.inRadians,
    //                pivotClimber.pivotLeftArmSensor.velocity.inRadiansPerSecond))
    //    val rightPivotProfile =
    //        TrapezoidProfile(
    //            pivotClimber.constraints,
    //            TrapezoidProfile.State(pivotClimber.desiredState.angle.inRadians, 0.0),
    //            TrapezoidProfile.State(
    //                pivotClimber.pivotRightArmSensor.position.inRadians,
    //                pivotClimber.pivotRightArmSensor.velocity.inRadiansPerSecond))
    //    pivotClimber.setAngle(leftPivotProfile, rightPivotProfile, pivotIsUnderLoad)
  }

  override fun isFinished(): Boolean {
    return telescopingClimber.currentState.correspondingDesiredState ==
        telescopingClimber.desiredState // &&
    //        pivotClimber.currentState.correspondingDesiredState == pivotClimber.desiredState
  }
}
