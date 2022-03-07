package com.team4099.robot2022.commands.shooter

import com.team4099.robot2022.config.constants.FeederConstants
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj2.command.CommandBase

class ShootCommand(val shooter: Shooter) : CommandBase() {
  init {
    addRequirements(Feeder, shooter)
  }

  override fun initialize() {
    Feeder.feederState = FeederConstants.FeederState.SHOOT
  }

  override fun isFinished(): Boolean {
    return false
  }
}
