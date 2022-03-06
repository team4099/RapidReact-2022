package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.subsystems.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class OpenLoopExtendClimberCommand : CommandBase() {
  init {
    addRequirements(TelescopingClimber)
  }

  override fun execute() {
    TelescopingClimber.setOpenLoop(1.5, 1.5)

    Logger.addEvent("Climber", "Telescoping climber spooling up")
  }

  override fun end(interrupted: Boolean) {
    TelescopingClimber.setOpenLoop(0.0, 0.0)
  }
}