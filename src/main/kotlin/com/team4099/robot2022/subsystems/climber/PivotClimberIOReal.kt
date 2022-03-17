package com.team4099.robot2022.subsystems.climber

import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.PivotClimberConstants
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj.Solenoid

object PivotClimberIOReal : PivotClimberIO {
  private val leftPivotSolenoid =
      Solenoid(PneumaticsModuleType.REVPH, Constants.PivotClimber.LEFT_SOLENOID)
  private val rightPivotSolenoid =
      Solenoid(PneumaticsModuleType.REVPH, Constants.PivotClimber.RIGHT_SOLENOID)

  init {}

  override fun setLeftPivotSolenoid(solenoidValue: Boolean) {
    leftPivotSolenoid.set(solenoidValue)
  }

  override fun setRightPivotSolenoid(solenoidValue: Boolean) {
    rightPivotSolenoid.set(solenoidValue)
  }

  override fun updateInputs(inputs: PivotClimberIO.PivotClimberIOInputs) {
    inputs.leftExtended =
        leftPivotSolenoid.get() == PivotClimberConstants.ExtendState.IN.extendPosition
  }
}
