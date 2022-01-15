package com.team4099.robot2022.commands.feeder

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.Constants
import com.team4099.robot2022.subsystems.Feeder
import edu.wpi.first.wpilibj2.command.CommandBase

class FeederIdleCommand() : CommandBase() {
  init {
    addRequirements(Feeder)
  }

  override fun initialize() {
    Feeder.feederState = Constants.Feeder.FeederState.NEUTRAL
    Logger.addEvent(Constants.Feeder.TAB, "Feeder Idle")
  }
}
