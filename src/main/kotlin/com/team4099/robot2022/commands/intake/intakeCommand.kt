package com.team4099.robot2022.commands.intake

import com.team4099.lib.logging.Logger
import com.team4099.robot2021.subsystems.com.team4099.robot2022.subsystems.Intake
import com.team4099.robot2022.config.Constants
import edu.wpi.first.wpilibj2.command.CommandBase

class intakeCommand(
  var intakeState: Constants.Intake.IntakeState,
  var armState: Constants.Intake.ArmPos
) : CommandBase() {

  init {
    addRequirements(Intake)
  }

  override fun initialize() {
    Intake.armState = armState
    Intake.intakeState = intakeState
    Logger.addEvent("Intake", "Intake State: $intakeState Arm State: $armState")
  }
}
