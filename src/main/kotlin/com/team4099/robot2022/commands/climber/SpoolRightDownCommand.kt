package com.team4099.robot2022.commands.climber

import com.team4099.robot2022.config.constants.TelescopingClimberConstants
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class SpoolRightDownCommand(val telescopingClimber: TelescopingClimber) : CommandBase() {
  init {
    addRequirements(telescopingClimber)
  }

  override fun execute() {
    telescopingClimber.setOpenLoop(0.0, -0.5, useSoftLimits = false)
    telescopingClimber.desiredState = TelescopingClimberConstants.DesiredTelescopeStates.START
    Logger.getInstance().recordOutput("ActiveCommands/SpoolRightDownCommand", true)
  }

  override fun end(interrupted: Boolean) {
    telescopingClimber.setOpenLoop(0.0, 0.0)
    telescopingClimber.zeroRightEncoder()
  }
}
