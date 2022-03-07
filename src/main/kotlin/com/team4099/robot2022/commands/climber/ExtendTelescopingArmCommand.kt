package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.lib.units.base.inMeters
import com.team4099.robot2022.config.constants.TelescopingClimberConstants
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.CommandBase

class ExtendTelescopingArmCommand : CommandBase() {
  var goal: TrapezoidProfile.State = TrapezoidProfile.State(0.0, 0.0)
  lateinit var leftProfile: TrapezoidProfile
  lateinit var rightProfile: TrapezoidProfile

  init {
    addRequirements(TelescopingClimber)
    goal =
        TrapezoidProfile.State(
            TelescopingClimberConstants.TelescopingArmPosition.HIGH.length.inMeters, 0.0)
  }

  override fun initialize() {
    Logger.addEvent("Telescoping Climber", "Telescoping Climber moved to high")
  }

  override fun execute() {
    leftProfile =
        TrapezoidProfile(TelescopingClimber.constraints, goal, TelescopingClimber.leftSetpoint)
    rightProfile =
        TrapezoidProfile(TelescopingClimber.constraints, goal, TelescopingClimber.rightSetpoint)
    TelescopingClimber.setPosition(leftProfile, rightProfile, false)
  }
}
