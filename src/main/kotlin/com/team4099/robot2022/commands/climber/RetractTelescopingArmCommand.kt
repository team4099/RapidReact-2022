package com.team4099.robot2022.commands.climber

import com.team4099.lib.hal.Clock
import com.team4099.robot2022.config.constants.TelescopingClimberConstants
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class RetractTelescopingArmCommand(val telescopingClimber: TelescopingClimber) : CommandBase() {
  // lateinit var leftTelescopingProfile: TrapezoidProfile
  // / lateinit var rightTelescopingProfile: TrapezoidProfile

  var startTime = Clock.fpgaTime

  init {
    addRequirements(telescopingClimber)
  }

  override fun initialize() {
    telescopingClimber.desiredState = TelescopingClimberConstants.DesiredTelescopeStates.MAX_RETRACT
    telescopingClimber.activelyHold = true

    /*
    leftTelescopingProfile =
      TrapezoidProfile(
        telescopingClimber.constraints,
        TrapezoidProfile.State(telescopingClimber.desiredState.position.inMeters, 0.0),
        TrapezoidProfile.State(
          telescopingClimber.inputs.leftPosition.inMeters,
          telescopingClimber.inputs.leftVelocity.inMetersPerSecond
        )
      )

    rightTelescopingProfile =
      TrapezoidProfile(
        telescopingClimber.constraints,
        TrapezoidProfile.State(telescopingClimber.desiredState.position.inMeters, 0.0),
        TrapezoidProfile.State(
          telescopingClimber.inputs.rightPosition.inMeters,
          telescopingClimber.inputs.rightVelocity.inMetersPerSecond
        )
      )
    */
    startTime = Clock.fpgaTime
  }

  override fun execute() {
    telescopingClimber.setOpenLoop(-1.0, -1.0)
    /*
    telescopingClimber.setPosition(
      leftTelescopingProfile.calculate((Clock.fpgaTime - startTime).inSeconds),
      rightTelescopingProfile.calculate((Clock.fpgaTime - startTime).inSeconds),
      isUnderLoad = true
    )
    */
    Logger.getInstance().recordOutput("ActiveCommands/RetractTelescopingArmCommand", true)
  }

  override fun isFinished(): Boolean {
    return telescopingClimber.currentState.correspondingDesiredState ==
      telescopingClimber.desiredState
    // return leftTelescopingProfile.isFinished((Clock.fpgaTime - startTime).inSeconds) &&
    //  rightTelescopingProfile.isFinished((Clock.fpgaTime - startTime).inSeconds)
  }
}
