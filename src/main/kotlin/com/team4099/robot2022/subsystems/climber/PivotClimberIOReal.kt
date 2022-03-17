package com.team4099.robot2022.subsystems.climber

import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.PivotClimberConstants
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj.Solenoid

object PivotClimberIOReal : PivotClimberIO {
  private val pivotSolenoid =
      Solenoid(PneumaticsModuleType.REVPH, Constants.PivotClimber.PIVOT_SOLENOID)
  init {}

  override fun setPivotSolenoid(solenoidValue: Boolean) {
    pivotSolenoid.set(solenoidValue)
  }
  override fun updateInputs(inputs: PivotClimberIO.PivotClimberIOInputs) {
    inputs.pivotExtended =
        pivotSolenoid.get() == PivotClimberConstants.ExtendState.IN.extendPosition
  }
}
