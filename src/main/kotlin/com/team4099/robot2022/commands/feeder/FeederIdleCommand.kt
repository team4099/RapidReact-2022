package com.team4099.robot2022.commands.feeder

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.FeederConstants
import com.team4099.robot2022.subsystems.feeder.Feeder
import edu.wpi.first.wpilibj2.command.CommandBase

class FeederIdleCommand(val feeder: Feeder) : CommandBase() {
  init {
    addRequirements(feeder)
  }

  override fun initialize() {
    feeder.state = FeederConstants.FeederState.NEUTRAL
    Logger.addEvent(FeederConstants.TAB, "Feeder Idle")
  }
}
