package com.team4099.robot2022.commands.feeder

import com.team4099.robot2022.config.constants.FeederConstants
import com.team4099.robot2022.subsystems.feeder.Feeder
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class FeederCommand(val feeder: Feeder, var state: FeederConstants.FeederState) : CommandBase() {
  init {
    addRequirements(feeder)
  }

  override fun initialize() {
    feeder.state = state
  }

  override fun execute() {
    Logger.getInstance().recordOutput("ActiveCommands/FeederCommand${feeder.state}", true)
  }
}
