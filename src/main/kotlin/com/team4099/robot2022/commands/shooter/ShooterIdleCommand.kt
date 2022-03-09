package com.team4099.robot2022.commands.shooter

import com.team4099.robot2022.config.constants.ShooterConstants
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class ShooterIdleCommand(val shooter: Shooter) : CommandBase() {
  init {
    addRequirements(shooter)
  }

  override fun initialize() {
    shooter.state = ShooterConstants.ShooterState.OFF
  }

  override fun isFinished(): Boolean = false

  override fun execute() {
    Logger.getInstance().recordOutput("ActiveCommands/ShooterIdleCommand", true)
  }
}
