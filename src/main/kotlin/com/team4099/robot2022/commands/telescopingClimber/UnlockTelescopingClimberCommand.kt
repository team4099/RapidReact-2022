package com.team4099.robot2022.commands.telescopingClimber

import com.team4099.lib.hal.Clock
import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.Constants
import com.team4099.robot2022.subsystems.TelescopingClimber
import edu.wpi.first.wpilibj2.command.CommandBase

class UnlockTelescopingClimberCommand : CommandBase() {
  init {
    //addRequirements(Climber)
  }
  private var initTime = Clock.fpgaTime

  override fun initialize() {
    initTime = Clock.fpgaTime
    TelescopingClimber.setTOpenLoopPower(0.0)
    TelescopingClimber.t_BrakeApplied = false
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
