package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.subsystems.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class SpoolRightClimberCommand: CommandBase() {
  init{
    addRequirements(TelescopingClimber)
  }

  override fun execute() {
    TelescopingClimber.setOpenLoop(0.0,-0.5)

    Logger.addEvent("Climber", "Left Climber spooling")
  }

  override fun end(interrupted: Boolean) {
    TelescopingClimber.setOpenLoop(0.0,0.0)
    TelescopingClimber.zeroRightEncoder()
  }
}
