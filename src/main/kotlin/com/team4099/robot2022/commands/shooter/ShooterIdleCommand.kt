package com.team4099.robot2022.commands.shooter

import com.team4099.robot2022.config.constants.ShooterConstants
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj2.command.CommandBase

class ShooterIdleCommand : CommandBase() {
  init {
    addRequirements(Shooter)
  }

  override fun initialize() {
    Shooter.shooterState = ShooterConstants.ShooterState.OFF
  }

  override fun isFinished(): Boolean = false
}
