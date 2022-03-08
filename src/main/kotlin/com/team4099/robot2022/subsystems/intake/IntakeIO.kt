package com.team4099.robot2022.subsystems.intake

import com.team4099.lib.units.base.amps
import com.team4099.lib.units.base.inAmperes
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.derived.inVolts
import com.team4099.lib.units.derived.radians
import com.team4099.lib.units.derived.volts
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.lib.units.perSecond
import com.team4099.robot2022.config.constants.IntakeConstants
import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface IntakeIO {
  class IntakeIOInputs : LoggableInputs {
    var extended = false

    var rollerPosition = 0.degrees
    var rollerVelocity = 0.degrees.perSecond
    var rollerAppliedVoltage = 0.volts
    var rollerStatorCurrent = 0.amps
    var rollerSupplyCurrent = 0.amps
    var rollerTempCelcius = 0.0 // TODO: units for temperature

    override fun toLog(table: LogTable?) {
      table?.put("extended", extended)

      table?.put("rollerPositionRad", rollerPosition.inRadians)
      table?.put("rollerVelocityRadPerSec", rollerVelocity.inRadiansPerSecond)
      table?.put("rollerAppliedVolts", rollerAppliedVoltage.inVolts)
      table?.put("rollerStatorCurrentAmps", rollerStatorCurrent.inAmperes)
      table?.put("rollerSupplyCurrentAmps", rollerSupplyCurrent.inAmperes)
      table?.put("rollerTempCelcius", rollerTempCelcius)
    }

    override fun fromLog(table: LogTable?) {
      table?.getBoolean("extended", extended)?.let { extended = it }

      table?.getDouble("rollerPositionRad", rollerPosition.inRadians)?.let {
        rollerPosition = it.radians
      }
      table?.getDouble("rollerVelocityRadPerSec", rollerVelocity.inRadiansPerSecond)?.let {
        rollerVelocity = it.radians.perSecond
      }
      table?.getDouble("rollerAppliedVolts", rollerAppliedVoltage.inVolts)?.let {
        rollerAppliedVoltage = it.volts
      }
      table?.getDouble("rollerStatorCurrentAmps", rollerStatorCurrent.inAmperes)?.let {
        rollerStatorCurrent = it.amps
      }
      table?.getDouble("rollerSupplyCurrentAmps", rollerSupplyCurrent.inAmperes)?.let {
        rollerSupplyCurrent = it.amps
      }
      table?.getDouble("rollerTempCelcius", rollerTempCelcius)?.let { rollerTempCelcius = it }
    }
  }

  fun updateInputs(inputs: IntakeIOInputs) {}

  fun setArmSolenoid(solenoidValue: Boolean) {}
  fun setRollerPower(percentOutput: Double) {}
}
