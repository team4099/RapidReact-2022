package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.ClimberConstants.TAB
import com.team4099.robot2022.subsystems.climber.PivotClimber
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class ClimberIdleCommand(
  val telescopingClimber: TelescopingClimber,
  val pivotClimber: PivotClimber
) : CommandBase() {
  init {
    addRequirements(telescopingClimber, pivotClimber)
  }

  override fun execute() {
    telescopingClimber.setOpenLoop(0.0, 0.0)
    pivotClimber.setOpenLoop(0.0, 0.0)
  }
  override fun initialize() {
    Logger.addEvent(TAB, "Climber Idle")
  }
}
