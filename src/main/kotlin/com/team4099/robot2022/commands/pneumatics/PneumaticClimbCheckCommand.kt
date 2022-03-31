package com.team4099.robot2022.commands.pneumatics

import com.team4099.robot2022.config.constants.PneumaticConstants
import com.team4099.robot2022.subsystems.pneumatics.Pneumatic
import edu.wpi.first.wpilibj2.command.CommandBase

class PneumaticClimbCheckCommand(val pneumatic: Pneumatic) : CommandBase() {
  init {
    addRequirements(pneumatic)
  }

  override fun isFinished(): Boolean {
    return pneumatic.allowClimb == PneumaticConstants.AllowClimb.CLIMB
  }
}
