package com.team4099.robot2022.commands.intake

import com.team4099.robot2022.config.constants.IntakeConstants
import com.team4099.robot2022.subsystems.intake.Intake
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

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

    intake.rollerState = IntakeConstants.RollerState.IN
    intake.armState = IntakeConstants.ArmState.OUT
    //    intake.extendTime = Clock.fpgaTime
  }

  override fun execute() {
    Logger.getInstance().recordOutput("ActiveCommands/IntakeBallsCommand", true)
  }

  override fun end(interrupted: Boolean) {
    intake.rollerState = IntakeConstants.RollerState.IDLE
    intake.armState = IntakeConstants.ArmState.IN
  }

  override fun isFinished(): Boolean {
    return false
  }
}
