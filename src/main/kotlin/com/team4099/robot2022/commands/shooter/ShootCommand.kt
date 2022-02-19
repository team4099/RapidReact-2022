package com.team4099.robot2022.commands.shooter

import com.team4099.robot2022.config.constants.FeederConstants
import com.team4099.robot2022.subsystems.Feeder
import com.team4099.robot2022.subsystems.Shooter
import edu.wpi.first.wpilibj2.command.CommandBase

class ShootCommand() : CommandBase() {
  init {
    addRequirements(Feeder, Shooter)
  }

  override fun initialize() {
    Feeder.feederState = FeederConstants.FeederState.SHOOT
  }

  override fun isFinished(): Boolean {
    return false
  }
}
