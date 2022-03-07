package com.team4099.robot2022.commands.intake

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.IntakeConstants
import com.team4099.robot2022.subsystems.intake.Intake
import edu.wpi.first.wpilibj2.command.CommandBase

class IntakeBallsCommand(val intake: Intake) : CommandBase() {
  init {
    addRequirements(intake)
    // addRequirements(Feeder)
  }

  override fun initialize() {
    /*
    if (Feeder.ballCount <= 2) {
      Intake.intakeState = Constants.Intake.IntakeState.IN
      Intake.armState = Constants.Intake.ArmPos.OUT
      Logger.addEvent("Intake", "Intake Balls")
    }
     */

    intake.intakeState = IntakeConstants.IntakeState.IN
    intake.armState = IntakeConstants.ArmPos.OUT
    Logger.addEvent("Intake", "Intake Balls")
  }

  override fun execute() {}
  override fun isFinished(): Boolean {
    return false
  }
}
