package com.team4099.robot2022.commands.climber

import com.team4099.robot2022.config.constants.TelescopingClimberConstants
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class TelescopingIdleCommand(val telescopingClimber: TelescopingClimber) : CommandBase() {
  init {
    addRequirements(telescopingClimber)
  }

  override fun execute() {
    if (telescopingClimber.currentState ==
        TelescopingClimberConstants.ActualTelescopeStates.START ||
        telescopingClimber.currentState ==
            TelescopingClimberConstants.ActualTelescopeStates.MAX_EXTENSION || !telescopingClimber.activelyHold) {
      telescopingClimber.setOpenLoop(0.0, 0.0)
    } else {
      telescopingClimber.holdPosition(loaded = true)
    }
    Logger.getInstance().recordOutput("ActiveCommands/TelescopingIdleCommand", true)
  }
  override fun initialize() {}
}
