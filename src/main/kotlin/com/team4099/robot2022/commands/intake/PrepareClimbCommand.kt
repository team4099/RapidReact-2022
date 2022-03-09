package com.team4099.robot2022.commands.intake

import com.team4099.robot2022.config.constants.IntakeConstants
import com.team4099.robot2022.subsystems.intake.Intake
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class PrepareClimbCommand(val intake: Intake) : CommandBase() {
  init {
    { addRequirements(intake) }
  }

  override fun initialize() {
    intake.armState = IntakeConstants.ArmState.IN
    intake.rollerState = IntakeConstants.RollerState.IDLE
    // Use this later maybe? Shooter.hoodState
    // Use later: Logger.addEvent("Shooter", "HoodRetracted")
  }

  override fun execute() {
    Logger.getInstance().recordOutput("ActiveCommands/PrepareClimbCommand", true)
  }

  override fun isFinished(): Boolean {
    return true
  }
}
