package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class SpootLeftUpCommand : CommandBase() {
  init {
    addRequirements(TelescopingClimber)
  }

  override fun execute() {
    TelescopingClimber.setOpenLoop(0.5, 0.0, useSoftLimits = false)

    Logger.addEvent("Climber", "Left Climber spooling up")
  }

  override fun end(interrupted: Boolean) {
    TelescopingClimber.setOpenLoop(0.0, 0.0)
    TelescopingClimber.zeroLeftEncoder()
  }
}
