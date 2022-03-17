package com.team4099.robot2022.subsystems.climber

import com.team4099.robot2022.config.constants.PivotClimberConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class PivotClimber(val io: PivotClimberIO) : SubsystemBase() {

  val inputs = PivotClimberIO.PivotClimberIOInputs()

  var leftClimbState = PivotClimberConstants.ExtendState.IN
    set(state) {
      io.setLeftPivotSolenoid(state.extendPosition)
      field = state
    }

  var rightClimbState = PivotClimberConstants.ExtendState.IN
    set(state) {
      io.setRightPivotSolenoid(state.extendPosition)
      field = state
    }

  init {
    // necessary because the setter is not called on initialization
    leftClimbState = leftClimbState
    rightClimbState = rightClimbState
  }

  override fun periodic() {
    io.updateInputs(inputs)
    Logger.getInstance().processInputs("Pivot Climber", inputs)
    Logger.getInstance().recordOutput("Climber/LeftSolenoidPosition", leftClimbState.name)
    Logger.getInstance().recordOutput("Climber/RightSolenoidPosition", rightClimbState.name)
  }
}
