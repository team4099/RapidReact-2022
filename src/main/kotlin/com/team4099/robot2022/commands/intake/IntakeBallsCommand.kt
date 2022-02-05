package com.team4099.robot2022.commands.intake

import com.team4099.lib.logging.Logger
import com.team4099.robot2021.subsystems.com.team4099.robot2022.subsystems.Intake
import com.team4099.robot2022.config.Constants
import edu.wpi.first.wpilibj2.command.CommandBase

class IntakeBallsCommand : CommandBase() {
  init {
    addRequirements(Intake)
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

    Intake.intakeState = Constants.Intake.IntakeState.IN
    Intake.armState = Constants.Intake.ArmPos.OUT
    Logger.addEvent("Intake", "Intake Balls")
  }

  override fun execute() {}
  override fun isFinished(): Boolean {
    return false
  }
}
