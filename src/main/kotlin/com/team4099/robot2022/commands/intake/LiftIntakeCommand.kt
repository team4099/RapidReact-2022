package com.team4099.robot2022.commands.intake

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.IntakeConstants
import com.team4099.robot2022.subsystems.intake.Intake
import edu.wpi.first.wpilibj2.command.CommandBase

class LiftIntakeCommand(val intake: Intake) : CommandBase() {

  init {
    addRequirements(intake)
  }

  override fun initialize() {
    intake.armState = IntakeConstants.ArmPos.IN
    Logger.addEvent("Intake", "Intake lifted")
  }
  override fun execute() {}

  override fun isFinished(): Boolean {
    return false
  }

  override fun end(interrupted: Boolean) {
    intake.armState = IntakeConstants.ArmPos.OUT
  }
}
