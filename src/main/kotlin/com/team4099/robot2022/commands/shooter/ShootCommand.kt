package com.team4099.robot2022.commands.shooter

import com.team4099.robot2022.subsystems.Shooter
import edu.wpi.first.wpilibj2.command.CommandBase

class ShootCommand : CommandBase() {
  init {
    addRequirements(Shooter)
    // addRequirements(Feeder)
  }

  override fun execute() {}

  override fun isFinished(): Boolean {
    return false
  }
}
