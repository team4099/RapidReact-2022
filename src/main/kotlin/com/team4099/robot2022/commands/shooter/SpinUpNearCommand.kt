package com.team4099.robot2022.commands.shooter

import com.team4099.robot2022.config.constants.ShooterConstants
import com.team4099.robot2022.subsystems.Shooter
import edu.wpi.first.wpilibj2.command.CommandBase

class SpinUpNearCommand() : CommandBase() {
  init {
    addRequirements(Shooter)
  }

  override fun initialize() {
    Shooter.shooterState = ShooterConstants.ShooterState.SPIN_UP_NEAR
    //    Shooter.setOpenLoop(0.35)
  }

  override fun isFinished(): Boolean {
    return Shooter.isOnTarget
  }
}
