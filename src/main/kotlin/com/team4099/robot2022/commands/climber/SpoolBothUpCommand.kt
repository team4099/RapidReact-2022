package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.TelescopingClimberConstants
import com.team4099.robot2022.subsystems.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class SpoolBothUpCommand : CommandBase() {
  init {
    addRequirements(TelescopingClimber)
  }

  override fun execute() {
    if (TelescopingClimber.telescopingLeftArmSensor.position <
        TelescopingClimberConstants.FORWARD_SOFT_LIMIT) {
      if (TelescopingClimber.telescopingRightArmSensor.position <
          TelescopingClimberConstants.FORWARD_SOFT_LIMIT) {
        TelescopingClimber.setOpenLoop(0.3, 0.3)
      } else {
        TelescopingClimber.setOpenLoop(0.3, 0.0)
      }
    } else {
      if (TelescopingClimber.telescopingRightArmSensor.position <
          TelescopingClimberConstants.FORWARD_SOFT_LIMIT) {
        TelescopingClimber.setOpenLoop(0.0, 0.3)
      } else {
        TelescopingClimber.setOpenLoop(0.0, 0.0)
      }
    }

    Logger.addEvent("Climber", "Telescoping climber spooling up")
  }

  override fun end(interrupted: Boolean) {
    TelescopingClimber.setOpenLoop(0.0, 0.0)
  }
}
