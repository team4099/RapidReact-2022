package com.team4099.robot2022.commands.shooter

import com.team4099.robot2022.config.Constants.ShooterConstants
import com.team4099.robot2022.subsystems.Shooter
import edu.wpi.first.wpilibj2.command.CommandBase

class IdleCommand : CommandBase() {
  init {
    addRequirements(Shooter)
  }

  override fun initialize() {
    Shooter.shooterState = ShooterConstants.ShooterState.IDLE
  }

  override fun isFinished(): Boolean = false
}
