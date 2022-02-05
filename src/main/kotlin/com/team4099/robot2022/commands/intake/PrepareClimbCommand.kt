package com.team4099.robot2022.commands.intake

import com.team4099.lib.logging.Logger
import com.team4099.robot2021.subsystems.com.team4099.robot2022.subsystems.Intake
import com.team4099.robot2022.config.Constants
import edu.wpi.first.wpilibj2.command.CommandBase

class PrepareClimbCommand : CommandBase() {
  init {
    { addRequirements(Intake) }
  }

  override fun initialize() {
    Intake.armState = Constants.Intake.ArmPos.IN
    Intake.intakeState = Constants.Intake.IntakeState.IDLE
    // Use this later maybe? Shooter.hoodState
    Logger.addEvent("Intake", "Intake lifted and idle")
    // Use later: Logger.addEvent("Shooter", "HoodRetracted")
  }

  override fun execute() {}

  override fun isFinished(): Boolean {
    return true
  }
}
