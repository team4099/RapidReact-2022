package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.ClimberConstants.TAB
import com.team4099.robot2022.subsystems.PivotClimber
import com.team4099.robot2022.subsystems.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class ClimberIdleCommand : CommandBase() {
  init {
    addRequirements(TelescopingClimber, PivotClimber)
  }

  override fun execute() {
    TelescopingClimber.setOpenLoop(0.0, 0.0)
    PivotClimber.setOpenLoop(0.0, 0.0)
  }
  override fun initialize() {
    Logger.addEvent(TAB, "Climber Idle")
  }
}
