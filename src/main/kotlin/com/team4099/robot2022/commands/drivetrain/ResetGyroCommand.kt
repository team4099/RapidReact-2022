package com.team4099.robot2022.commands.drivetrain

import com.team4099.lib.units.derived.Angle
import com.team4099.lib.units.derived.degrees
import com.team4099.robot2022.subsystems.Drivetrain
import edu.wpi.first.wpilibj2.command.CommandBase

class ResetGyroCommand(val toAngle: Angle = 0.0.degrees) : CommandBase() {
  init {
    addRequirements(Drivetrain)
  }

  override fun initialize() {
    Drivetrain.zeroGyro(toAngle)
  }
}
