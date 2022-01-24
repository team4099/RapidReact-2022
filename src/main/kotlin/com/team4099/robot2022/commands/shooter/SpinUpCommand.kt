package com.team4099.robot2022.commands.shooter

import com.team4099.lib.units.inRotationsPerMinute
import com.team4099.robot2022.config.Constants.ShooterConstants
import com.team4099.robot2022.subsystems.Shooter
import edu.wpi.first.wpilibj2.command.CommandBase

class SpinUpCommand : CommandBase() {

  init {
    addRequirements(Shooter)
  }

  override fun initialize() {
    Shooter.shooterState = ShooterConstants.ShooterState.SPIN_UP
  }

  override fun isFinished(): Boolean {
    return ShooterConstants.TARGET_VELOCITY - Shooter.shooterVelocity <= ShooterConstants.TARGET_VELOCITY_THRESHOLD
  }
}
