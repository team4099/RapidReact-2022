package com.team4099.robot2022.commands.climber

import com.team4099.lib.units.base.inMeters
import com.team4099.robot2022.config.constants.TelescopingClimberConstants
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class RetractTelescopingArmCommand(val telescopingClimber: TelescopingClimber) : CommandBase() {
  var goal: TrapezoidProfile.State = TrapezoidProfile.State(0.0, 0.0)
  lateinit var leftProfile: TrapezoidProfile
  lateinit var rightProfile: TrapezoidProfile

  init {
    addRequirements(telescopingClimber)
    goal =
        TrapezoidProfile.State(
            TelescopingClimberConstants.TelescopingArmPosition.LOW.length.inMeters, 0.0)
  }

  override fun initialize() {}

  override fun execute() {
    leftProfile =
        TrapezoidProfile(telescopingClimber.constraints, goal, telescopingClimber.leftSetpoint)
    rightProfile =
        TrapezoidProfile(telescopingClimber.constraints, goal, telescopingClimber.rightSetpoint)
    telescopingClimber.setPosition(leftProfile, rightProfile, true)

    Logger.getInstance().recordOutput("ActiveCommands/RetractTelescopingArmCommand", true)
  }
}
