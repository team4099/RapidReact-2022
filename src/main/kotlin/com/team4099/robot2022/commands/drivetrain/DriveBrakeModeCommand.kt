package com.team4099.robot2022.commands.drivetrain

import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import edu.wpi.first.wpilibj2.command.CommandBase

class DriveBrakeModeCommand(val drivetrain: Drivetrain) : CommandBase() {
  init {
    addRequirements(drivetrain)
  }

  override fun execute() {
    drivetrain.swerveModules.forEach() { it.setDriveBrakeMode(true) }
  }

  override fun isFinished(): Boolean {
    return false
  }

  override fun end(interrupted: Boolean) {
    drivetrain.swerveModules.forEach() { it.setDriveBrakeMode(false) }
  }
}
