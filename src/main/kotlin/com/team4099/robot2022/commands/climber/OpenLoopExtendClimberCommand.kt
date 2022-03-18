package com.team4099.robot2022.commands.climber

import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class OpenLoopExtendClimberCommand(val telescopingClimber: TelescopingClimber) : CommandBase() {
  init {
    addRequirements(telescopingClimber)
  }

  override fun execute() {
    if (telescopingClimber.leftForwardThresholdLimitReached || telescopingClimber.rightForwardThresholdLimitReached) {
      telescopingClimber.setOpenLoop(0.6, 0.6)
    } else {
      telescopingClimber.setOpenLoop(1.0, 1.0)
    }

    Logger.getInstance().recordOutput("ActiveCommands/OpenLoopExtendClimberCommand", true)
  }

  override fun end(interrupted: Boolean) {
    telescopingClimber.setOpenLoop(0.0, 0.0)
  }
}
