package com.team4099.robot2022.commands.telescopingClimber

import com.team4099.lib.hal.Clock
import com.team4099.lib.logging.Logger
import com.team4099.robot2022.subsystems.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class UnlockTelescopingClimberCommand : CommandBase() {
  init {
    addRequirements(TelescopingClimber)
  }
  private var initTime = Clock.fpgaTime

  override fun initialize() {
    initTime = Clock.fpgaTime
    TelescopingClimber.brakeApplied = false
  }

  override fun end(interrupted: Boolean) {
    if (!interrupted) {
      Logger.addEvent("Telescoping Climber", "Climber Unlocked")
    }
  }

  override fun isFinished(): Boolean {
    return Clock.fpgaTime - initTime > Constants.TelescopingClimber.BRAKE_RELEASE_TIMEOUT
  }
}
