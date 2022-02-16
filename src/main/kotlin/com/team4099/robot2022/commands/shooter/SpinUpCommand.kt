package com.team4099.robot2022.commands.shooter

import com.team4099.robot2022.config.ShooterConstants
import com.team4099.robot2022.subsystems.Shooter
import edu.wpi.first.wpilibj2.command.CommandBase

class SpinUpCommand(val target: Double = ShooterConstants.ShooterState.SPIN_UP.rotationsPerMinute) :
    CommandBase() {
  init {
    addRequirements(Shooter)
  }

  override fun initialize() {
    Shooter.setVelocity(target)
  }

  override fun isFinished(): Boolean {
    return ShooterConstants.TARGET_VELOCITY - Shooter.shooterVelocity <=
        ShooterConstants.TARGET_VELOCITY_THRESHOLD
  }
}
