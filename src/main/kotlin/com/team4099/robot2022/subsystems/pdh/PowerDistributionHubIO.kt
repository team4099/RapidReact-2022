package com.team4099.robot2022.subsystems.pdh

import com.team4099.lib.units.base.amps
import com.team4099.lib.units.base.inAmperes
import com.team4099.lib.units.derived.inVolts
import com.team4099.lib.units.derived.volts
import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface PowerDistributionHubIO {
  class PowerDistributionHubIOInputs : LoggableInputs {
    var voltage = 0.0.volts
    var current = 0.0.amps

    override fun fromLog(table: LogTable?) {
      table?.put("Voltage", voltage.inVolts)
      table?.put("Total Current", current.inAmperes)
    }

    override fun toLog(table: LogTable?) {
      table?.getDouble("Voltage", voltage.inVolts)?.let { voltage = it.volts }
      table?.getDouble("Total Current", current.inAmperes)?.let { current = it.amps }
    }
  }
  fun updateInputs(inputs: PowerDistributionHubIOInputs) {}
}
