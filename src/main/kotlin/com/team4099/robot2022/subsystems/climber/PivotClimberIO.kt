package com.team4099.robot2022.subsystems.climber

import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface PivotClimberIO {
  class PivotClimberIOInputs : LoggableInputs {
    var leftExtended = false
    var rightExtended = false

    override fun toLog(table: LogTable?) {
      table?.put("leftPivotExtended", leftExtended)
      table?.put("rightPivotExtended", rightExtended)
    }

    override fun fromLog(table: LogTable?) {
      table?.getBoolean("leftPivotExtended", leftExtended)?.let { leftExtended = it }
      table?.getBoolean("rightPivotExtended", rightExtended)?.let { rightExtended = it }
    }
  }

  fun updateInputs(inputs: PivotClimberIOInputs) {}

  fun setLeftPivotSolenoid(solenoidValue: Boolean) {}
  fun setRightPivotSolenoid(solenoidValue: Boolean) {}
}
