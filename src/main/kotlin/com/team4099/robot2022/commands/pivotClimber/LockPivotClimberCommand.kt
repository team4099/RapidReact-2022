package com.team4099.robot2022.commands.pivotClimber

import com.team4099.lib.hal.Clock
import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.Constants
import com.team4099.robot2022.subsystems.PivotClimber
import com.team4099.robot2022.subsystems.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class LockPivotClimberCommand : CommandBase() {
  init {
    addRequirements(PivotClimber)
  }
  private var initTime = Clock.fpgaTime

  override fun initialize() {
    initTime = Clock.fpgaTime
    PivotClimber.brakeApplied = true
  }

  override fun end(interrupted: Boolean) {
    if (!interrupted) {
      Logger.addEvent("Pivot Climber", "Climber Locked")
    }
  }

  override fun isFinished(): Boolean {
    return Clock.fpgaTime - initTime > Constants.TelescopingClimber.BRAKE_RELEASE_TIMEOUT
  }
}
