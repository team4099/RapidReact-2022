package com.team4099.robot2022.commands.climber

import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.robot2022.config.constants.TelescopingClimberConstants
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class RetractTelescopingArmCommand(val telescopingClimber: TelescopingClimber) : CommandBase() {
  init {
    addRequirements(telescopingClimber)
    telescopingClimber.desiredState = TelescopingClimberConstants.DesiredTelescopeStates.MAX_RETRACT
  }

  override fun initialize() {}

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
        leftTelescopingProfile, rightTelescopingProfile, isUnderLoad = true)

    Logger.getInstance().recordOutput("ActiveCommands/RetractTelescopingArmCommand", true)
  }

  override fun isFinished(): Boolean {
    return telescopingClimber.currentState.correspondingDesiredState ==
        telescopingClimber.desiredState
  }
}
