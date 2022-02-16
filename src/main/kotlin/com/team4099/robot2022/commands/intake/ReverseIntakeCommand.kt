package com.team4099.robot2022.commands.intake

import com.team4099.lib.logging.Logger
import com.team4099.robot2021.subsystems.com.team4099.robot2022.subsystems.Intake
import com.team4099.robot2022.config.constants.IntakeConstants
import edu.wpi.first.wpilibj2.command.CommandBase

class ReverseIntakeCommand : CommandBase() {
  init {
    addRequirements(Intake)
  }

  override fun initialize() {
    Intake.intakeState = IntakeConstants.IntakeState.OUT
    Intake.armState = IntakeConstants.ArmPos.OUT
    Logger.addEvent("Intake", "Intake reversed")
  }

  override fun execute() {}

  override fun isFinished(): Boolean {
    return false
  }
}
