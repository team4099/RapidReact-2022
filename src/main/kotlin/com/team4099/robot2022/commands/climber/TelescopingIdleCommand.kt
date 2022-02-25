package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.ClimberConstants
import com.team4099.robot2022.subsystems.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class TelescopingIdleCommand : CommandBase() {
  init {
    addRequirements(TelescopingClimber)
  }

  override fun execute() {
    TelescopingClimber.setOpenLoop(0.0, 0.0)
  }
  override fun initialize() {
    Logger.addEvent(ClimberConstants.TAB, "Telescoping Idle")
  }
}
