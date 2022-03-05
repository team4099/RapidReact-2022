package com.team4099.robot2022.commands.drivetrain

import com.team4099.lib.units.base.feet
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.perSecond
import com.team4099.robot2022.subsystems.Drivetrain
import edu.wpi.first.wpilibj2.command.CommandBase

class OpenLoopReverseCommand() : CommandBase() {
  init {
    addRequirements(Drivetrain)
  }

  override fun execute() {
    Drivetrain.setOpenLoop(
        0.degrees.perSecond, Pair(0.0.feet.perSecond, 2.5.feet.perSecond), fieldOriented = false)
  }

  override fun isFinished(): Boolean {
    return false
  }
}
