package com.team4099.robot2022.commands.intake

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.IntakeConstants
import com.team4099.robot2022.subsystems.intake.Intake
import edu.wpi.first.wpilibj2.command.CommandBase

class PrepareClimbCommand(val intake: Intake) : CommandBase() {
  init {
    { addRequirements(intake) }
  }

  override fun initialize() {
    intake.armState = IntakeConstants.ArmPos.IN
    intake.intakeState = IntakeConstants.IntakeState.IDLE
    // Use this later maybe? Shooter.hoodState
    Logger.addEvent("Intake", "Intake lifted and idle")
    // Use later: Logger.addEvent("Shooter", "HoodRetracted")
  }

  override fun execute() {}

  override fun isFinished(): Boolean {
    return true
  }
}
