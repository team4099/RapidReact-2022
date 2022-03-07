package com.team4099.robot2022.commands.feeder

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.FeederConstants
import com.team4099.robot2022.subsystems.feeder.Feeder
import edu.wpi.first.wpilibj2.command.CommandBase

class FeederCommand(var state: FeederConstants.FeederState) : CommandBase() {
  init {
    addRequirements(Feeder)
  }

  override fun initialize() {
    Feeder.feederState = state
    Logger.addEvent("Feeder", "Feeder state: ${Feeder.feederState}")
  }
}
