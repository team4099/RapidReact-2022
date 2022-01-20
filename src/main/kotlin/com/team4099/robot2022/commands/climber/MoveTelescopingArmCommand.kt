package com.team4099.robot2022.commands.climber

import com.team4099.robot2022.config.Constants
import com.team4099.robot2022.subsystems.Climber
import edu.wpi.first.wpilibj2.command.CommandBase
import java.util.logging.Logger

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
