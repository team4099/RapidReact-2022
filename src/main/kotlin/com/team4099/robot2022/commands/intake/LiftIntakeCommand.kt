package com.team4099.robot2022.commands.intake

import com.team4099.robot2022.config.constants.IntakeConstants
import com.team4099.robot2022.subsystems.intake.Intake
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class LiftIntakeCommand(val intake: Intake) : CommandBase() {

  init {
    addRequirements(intake)
  }

  override fun initialize() {
    intake.armState = IntakeConstants.ArmState.IN
  }
  override fun execute() {
    Logger.getInstance().recordOutput("ActiveCommands/LiftIntakeCommand", true)
  }

  override fun isFinished(): Boolean {
    return false
  }

  override fun end(interrupted: Boolean) {
    intake.armState = IntakeConstants.ArmState.OUT
  }
}
