package com.team4099.robot2022.subsystems.climber

import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface PivotClimberIO {
  class PivotClimberIOInputs : LoggableInputs {
    var pivotExtended = false
    override fun toLog(table: LogTable?) {
      table?.put("pivotExtended", pivotExtended)
    }

    override fun fromLog(table: LogTable?) {
      table?.getBoolean("pivotExtended", pivotExtended)?.let { pivotExtended = it }
    }
  }

  fun updateInputs(inputs: PivotClimberIOInputs) {}

  fun setPivotSolenoid(solenoidValue: Boolean) {}
}
