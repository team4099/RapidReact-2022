package com.team4099.robot2022.commands.telescopingClimber

import com.team4099.robot2022.config.Constants
import edu.wpi.first.wpilibj2.command.CommandBase

class MoveTelescopingArmCommand (val pos : Constants.TelescopingArmPosition) : CommandBase() {
  init {
    //addRequirements(Climber)
  }

  override fun initialize() {
    //Logger.addEvent("Climber", "Climber Moved to $pos")
  }

  override fun execute() {
    //Climber.setTelescopingArmPosition(pos)
  }
}
