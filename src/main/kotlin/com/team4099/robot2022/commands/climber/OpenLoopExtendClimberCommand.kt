package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class OpenLoopExtendClimberCommand(val telescopingClimber: TelescopingClimber) : CommandBase() {
  init {
    addRequirements(telescopingClimber)
  }

  override fun execute() {
    telescopingClimber.setOpenLoop(0.3, 0.3)

    Logger.addEvent("Climber", "Telescoping climber spooling up")
  }

  override fun end(interrupted: Boolean) {
    telescopingClimber.setOpenLoop(0.0, 0.0)
  }
}
