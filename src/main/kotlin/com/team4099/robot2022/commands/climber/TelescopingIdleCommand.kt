package com.team4099.robot2022.commands.climber

import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class TelescopingIdleCommand(val telescopingClimber: TelescopingClimber) : CommandBase() {
  init {
    addRequirements(telescopingClimber)
  }

  override fun execute() {
    telescopingClimber.setOpenLoop(0.0, 0.0)
    org.littletonrobotics.junction.Logger
        .getInstance()
        .recordOutput("ActiveCommands/TelescopingIdleCommand", true)
  }
  override fun initialize() {}
}
