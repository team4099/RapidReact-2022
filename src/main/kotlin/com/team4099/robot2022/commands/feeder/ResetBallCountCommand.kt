package com.team4099.robot2022.commands.feeder

import com.team4099.robot2022.subsystems.feeder.Feeder
import edu.wpi.first.wpilibj2.command.CommandBase

class ResetBallCountCommand(val feeder: Feeder) : CommandBase() {
  init {
    addRequirements(feeder)
  }

  override fun execute() {
    feeder.ballCount = 0
  }
}
