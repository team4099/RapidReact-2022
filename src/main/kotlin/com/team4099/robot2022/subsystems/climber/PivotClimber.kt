package com.team4099.robot2022.subsystems.climber

import com.team4099.robot2022.config.constants.PivotClimberConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class PivotClimber(val io: PivotClimberIO) : SubsystemBase() {

  val inputs = PivotClimberIO.PivotClimberIOInputs()

  var pivotClimbState = PivotClimberConstants.DesiredPivotStates.IN
    set(state) {
      io.setPivotSolenoid(state.extendPosition)
      field = state
    }

  val desiredState = PivotClimberConstants.DesiredPivotStates.IN

  init {
    // necessary because the setter is not called on initialization
    pivotClimbState = pivotClimbState
  }

  override fun periodic() {
    io.updateInputs(inputs)
    Logger.getInstance().processInputs("Pivot Climber", inputs)
    Logger.getInstance().recordOutput("Climber/PivotClimberSolenoidPosition", pivotClimbState.name)
  }
}
