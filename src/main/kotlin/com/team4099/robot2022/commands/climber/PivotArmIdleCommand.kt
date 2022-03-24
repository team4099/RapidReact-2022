package com.team4099.robot2022.commands.climber

import com.team4099.robot2022.subsystems.climber.PivotClimber
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class PivotArmIdleCommand(val pivotClimber: PivotClimber) : CommandBase() {
  init {
    addRequirements(pivotClimber)
  }

  override fun initialize() {}

  override fun execute() {
    Logger.getInstance().recordOutput("ActiveCommands/PivotArmIdleCommand", true)
  }
}
