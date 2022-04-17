package com.team4099.robot2022.commands.shooter

import com.team4099.robot2022.config.constants.FeederConstants
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class AutoShootCommand(val shooter: Shooter, val feeder: Feeder) : CommandBase() {
  init {
    addRequirements(feeder, shooter)
  }

  override fun initialize() {}

  override fun isFinished(): Boolean {
    return false
  }

  override fun execute() {
    feeder.state = FeederConstants.FeederState.AUTO_SHOOT
    Logger.getInstance().recordOutput("ActiveCommands/ShootCommand", true)
  }

  override fun end(interrupted: Boolean) {
    feeder.state = FeederConstants.FeederState.NEUTRAL
  }
}
