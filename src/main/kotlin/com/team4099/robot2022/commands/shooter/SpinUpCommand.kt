package com.team4099.robot2022.commands.shooter

import com.team4099.robot2022.config.ShooterConstants
import com.team4099.robot2022.subsystems.Shooter
import edu.wpi.first.wpilibj2.command.CommandBase

class SpinUpCommand() : CommandBase() {
  init {
    addRequirements(Shooter)
  }

  override fun initialize() {
    Shooter.shooterState = ShooterConstants.ShooterState.SPIN_UP
//    Shooter.setOpenLoop(0.35)
  }

  override fun isFinished(): Boolean {
    return Shooter.isOnTarget
  }
}
