package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class SpoolRightUpCommand(val telescopingClimber: TelescopingClimber) : CommandBase() {
  init {
    addRequirements(telescopingClimber)
  }

  override fun execute() {
    telescopingClimber.setOpenLoop(0.0, 0.75, useSoftLimits = false)

    Logger.addEvent("Climber", "Right Climber spooling up")
  }

  override fun end(interrupted: Boolean) {
    telescopingClimber.setOpenLoop(0.0, 0.0)
//    telescopingClimber.zeroLeftEncoder()
  }
}
