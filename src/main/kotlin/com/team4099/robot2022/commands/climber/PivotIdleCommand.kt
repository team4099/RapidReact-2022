package com.team4099.robot2022.commands.climber

import com.team4099.robot2022.subsystems.climber.PivotClimber
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class PivotIdleCommand(val pivotClimber: PivotClimber) : CommandBase() {
  init {
    addRequirements(pivotClimber)
  }

  override fun execute() {
    pivotClimber.setOpenLoop(0.0, 0.0)

    Logger.getInstance().recordOutput("ActiveCommands/PivotIdleCommand", true)
  }
  override fun initialize() {}
}
