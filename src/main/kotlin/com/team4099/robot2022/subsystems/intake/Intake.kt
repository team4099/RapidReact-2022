package com.team4099.robot2022.subsystems.intake

import com.team4099.robot2022.config.constants.IntakeConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class Intake(val io: IntakeIO) : SubsystemBase() {

  val inputs = IntakeIO.IntakeIOInputs()

  var intakeState = IntakeConstants.IntakeState.IDLE
    set(state) {
      io.setRollerState(state)
      field = state
    }

  var armState = IntakeConstants.ArmPos.IN
    set(state) {
      io.setArmState(state)
      field = state
    }

  init {
    // necessary because the setter is not called on initialization
    intakeState = intakeState
    armState = armState
  }

  override fun periodic() {
    io.updateInputs(inputs)
    Logger.getInstance().processInputs("Intake", inputs)
  }
}
