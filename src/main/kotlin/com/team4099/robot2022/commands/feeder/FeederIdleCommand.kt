package com.team4099.robot2022.commands.feeder

import com.team4099.robot2022.config.constants.FeederConstants
import com.team4099.robot2022.subsystems.feeder.Feeder
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class FeederIdleCommand(val feeder: Feeder) : CommandBase() {
  init {
    addRequirements(feeder)
  }

  override fun initialize() {
    feeder.state = FeederConstants.FeederState.NEUTRAL
  }

  override fun execute() {
    Logger.getInstance().recordOutput("ActiveCommands/FeederIdleCommand", true)
  }
}
