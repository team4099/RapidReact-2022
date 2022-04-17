package com.team4099.robot2022.commands.pneumatics

import com.team4099.robot2022.subsystems.pneumatics.Pneumatic
import edu.wpi.first.wpilibj2.command.CommandBase

class UseLowThresholdClimbCommand(val pneumatic: Pneumatic) : CommandBase() {
  init {
    addRequirements(pneumatic)
  }

  override fun initialize() {
    pneumatic.allowLowPressure = true
  }

  override fun isFinished(): Boolean {
    return false
  }
}
