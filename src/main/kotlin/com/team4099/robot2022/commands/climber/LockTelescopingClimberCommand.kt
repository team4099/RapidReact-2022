package com.team4099.robot2022.commands.climber

import com.team4099.lib.hal.Clock
import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.TelescopingClimberConstants
import com.team4099.robot2022.subsystems.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class LockTelescopingClimberCommand : CommandBase() {
  init {
    addRequirements(TelescopingClimber)
  }
  private var initTime = Clock.fpgaTime

  override fun initialize() {
    initTime = Clock.fpgaTime
    TelescopingClimber.brakeApplied = true
  }

  override fun end(interrupted: Boolean) {
    if (!interrupted) {
      Logger.addEvent("Telescoping Climber", "Climber Locked")
    }
  }

  override fun isFinished(): Boolean {
    return Clock.fpgaTime - initTime > TelescopingClimberConstants.BRAKE_RELEASE_TIMEOUT
  }
}
