package com.team4099.robot2022.commands.drivetrain

import com.team4099.lib.units.derived.Angle
import com.team4099.lib.units.derived.degrees
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class ResetGyroCommand(val drivetrain: Drivetrain, val toAngle: Angle = 0.0.degrees) :
  CommandBase() {
  init {
    addRequirements(drivetrain)
  }

  override fun initialize() {
    drivetrain.zeroGyro(toAngle)
  }

  override fun execute() {
    Logger.getInstance().recordOutput("ActiveCommands/ResetGyroCommand", true)
  }

  override fun isFinished(): Boolean {
    return true
  }
}
