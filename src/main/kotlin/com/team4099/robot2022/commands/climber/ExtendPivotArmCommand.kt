package com.team4099.robot2022.commands.climber

import com.team4099.lib.hal.Clock
import com.team4099.lib.units.base.seconds
import com.team4099.robot2022.config.constants.PivotClimberConstants
import com.team4099.robot2022.subsystems.climber.PivotClimber
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class ExtendPivotArmCommand(val pivotClimber: PivotClimber) : CommandBase() {
  init {
    addRequirements(pivotClimber)
  }

  var startTime = 0.0.seconds

  override fun initialize() {
    pivotClimber.pivotClimbState = PivotClimberConstants.DesiredPivotStates.OUT
    startTime = Clock.fpgaTime
  }

  override fun execute() {
    Logger.getInstance().recordOutput("ActiveCommands/ExtendPivotArmCommand", true)
  }

  override fun isFinished(): Boolean {
    return Clock.fpgaTime - startTime >= PivotClimberConstants.EXTEND_PIVOT_TIME
  }
}
