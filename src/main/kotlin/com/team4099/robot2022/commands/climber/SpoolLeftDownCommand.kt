package com.team4099.robot2022.commands.climber

import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class SpoolLeftDownCommand(val telescopingClimber: TelescopingClimber) : CommandBase() {
  init {
    addRequirements(telescopingClimber)
  }

  override fun execute() {
    telescopingClimber.setOpenLoop(-0.5, 0.0, useSoftLimits = false)

    Logger.getInstance().recordOutput("ActiveCommands/SpoolLeftDownCommand", true)
  }

  override fun end(interrupted: Boolean) {
    telescopingClimber.setOpenLoop(0.0, 0.0)
    telescopingClimber.zeroLeftEncoder()
  }
}
