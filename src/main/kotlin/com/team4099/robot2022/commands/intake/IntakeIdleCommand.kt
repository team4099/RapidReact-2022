package com.team4099.robot2022.commands.intake

import com.team4099.robot2022.config.constants.IntakeConstants
import com.team4099.robot2022.subsystems.intake.Intake
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class IntakeIdleCommand(val intake: Intake) : CommandBase() {
  init {
    addRequirements(intake)
  }

  override fun initialize() {
    intake.rollerState = IntakeConstants.RollerState.IDLE
    intake.armState = IntakeConstants.ArmState.IN
  }

  override fun execute() {
    Logger.getInstance().recordOutput("ActiveCommands/IntakeIdleCommand", true)
  }
  override fun isFinished(): Boolean {
    return false
  }
}
