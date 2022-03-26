package com.team4099.robot2022.subsystems.intake

import com.team4099.lib.hal.Clock
import com.team4099.lib.units.base.inSeconds
import com.team4099.robot2022.config.constants.IntakeConstants
import com.team4099.robot2022.config.constants.LEDConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class Intake(val io: IntakeIO) : SubsystemBase() {

  val inputs = IntakeIO.IntakeIOInputs()

  var rollerState = IntakeConstants.RollerState.IDLE
    set(state) {
      io.setRollerPower(state.speed)
      if (state == IntakeConstants.RollerState.IN) {
        extendTime = Clock.fpgaTime
      }
      field = state
    }

  var armState = IntakeConstants.ArmState.IN
    set(state) {
      io.setArmSolenoid(state.out)
      field = state
    }

  val hasBall: Boolean
    get() {
      return inputs.rollerStatorCurrent >= LEDConstants.INTAKE_CURRENT_THRESHOLD &&
          rollerState == IntakeConstants.RollerState.IN &&
          ((Clock.fpgaTime - extendTime).inSeconds >= IntakeConstants.RAMP_TIME)
    }

  var extendTime = Clock.fpgaTime

  init {
    // necessary because the setter is not called on initialization
    rollerState = rollerState
    armState = armState
  }

  override fun periodic() {
    io.updateInputs(inputs)
    Logger.getInstance().processInputs("Intake", inputs)
    Logger.getInstance().recordOutput("Intake/rollerState", rollerState.name)
    Logger.getInstance().recordOutput("Intake/armState", armState.name)
  }
}
