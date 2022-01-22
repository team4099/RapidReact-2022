package com.team4099.robot2022.commands.climber

import com.team4099.lib.hal.Clock
import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.Constants
import com.team4099.robot2022.subsystems.Climber

class UnlockClimberCommand {
  init {
    //addRequirements(Climber)
  }
  private var initTime = Clock.fpgaTime

  override fun initialize() {
    initTime = Clock.fpgaTime
    Climber.setTOpenLoopPower(0.0)
    Climber.t_BrakeApplied = false
  } //add info for pivot arm

  override fun end(interrupted: Boolean) {
    if (!interrupted) {
      Logger.addEvent("Climber", "Climber Unlocked")
    }
  }

  override fun isFinished(): Boolean {
    return Clock.fpgaTime - initTime > Constants.Climber.BRAKE_RELEASE_TIMEOUT
  }
}
