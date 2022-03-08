package com.team4099.robot2022.subsystems.climber

import com.team4099.lib.units.base.Length
import com.team4099.lib.units.base.amps
import com.team4099.lib.units.base.inAmperes
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.derived.ElectricalPotential
import com.team4099.lib.units.derived.inVolts
import com.team4099.lib.units.derived.volts
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.perSecond
import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface TelescopingClimberIO {
  class TelescopingClimberIOInputs : LoggableInputs {
    var leftPosition = 0.0.meters
    var rightPosition = 0.0.meters

    var leftVelocity = 0.0.meters.perSecond
    var rightVelocity = 0.0.meters.perSecond

    var leftStatorCurrent = 0.0.amps
    var rightStatorCurrent = 0.0.amps

    var leftSupplyCurrent = 0.0.amps
    var rightSupplyCurrent = 0.0.amps

    var leftOutputVoltage = 0.0.volts
    var rightOutputVoltage = 0.0.volts

    var leftTemperatureCelcius = 0.0
    var rightTemperatureCelcius = 0.0

    override fun toLog(table: LogTable?) {
      table?.put("leftPositionMeters", leftPosition.inMeters)
      table?.put("rightPositionMeters", rightPosition.inMeters)
      table?.put("leftVelocityMetersPerSec", leftVelocity.inMetersPerSecond)
      table?.put("rightVelocityMetersPerSec", rightVelocity.inMetersPerSecond)
      table?.put("leftStatorCurrentAmps", leftStatorCurrent.inAmperes)
      table?.put("rightStatorCurrentAmps", rightStatorCurrent.inAmperes)
      table?.put("leftSupplyCurrentAmps", leftSupplyCurrent.inAmperes)
      table?.put("rightSupplyCurrentAmps", rightSupplyCurrent.inAmperes)
      table?.put("leftOutputVoltage", leftOutputVoltage.inVolts)
      table?.put("rightOutputVoltage", rightOutputVoltage.inVolts)
      table?.put("leftTemperatureCelcius", leftTemperatureCelcius)
      table?.put("rightTemperatureCelcius", rightTemperatureCelcius)
    }

    override fun fromLog(table: LogTable?) {
      table?.getDouble("leftPositionMeters", leftPosition.inMeters)?.let {
        leftPosition = it.meters
      }
      table?.getDouble("rightPositionMeters", rightPosition.inMeters)?.let {
        rightPosition = it.meters
      }
      table?.getDouble("leftVelocityMetersPerSec", leftVelocity.inMetersPerSecond)?.let {
        leftVelocity = it.meters.perSecond
      }
      table?.getDouble("rightVelocityMetersPerSec", rightVelocity.inMetersPerSecond)?.let {
        rightVelocity = it.meters.perSecond
      }
      table?.getDouble("leftStatorCurrentAmps", leftStatorCurrent.inAmperes)?.let {
        leftStatorCurrent = it.amps
      }
      table?.getDouble("rightStatorCurrentAmps", rightStatorCurrent.inAmperes)?.let {
        rightStatorCurrent = it.amps
      }
      table?.getDouble("leftSupplyCurrentAmps", leftSupplyCurrent.inAmperes)?.let {
        leftSupplyCurrent = it.amps
      }
      table?.getDouble("rightSupplyCurrentAmps", rightSupplyCurrent.inAmperes)?.let {
        rightSupplyCurrent = it.amps
      }
      table?.getDouble("leftOutputVoltage", leftOutputVoltage.inVolts)?.let {
        leftOutputVoltage = it.volts
      }
      table?.getDouble("rightOutputVoltage", rightOutputVoltage.inVolts)?.let {
        rightOutputVoltage = it.volts
      }
      table?.getDouble("leftTemperatureCelcius", leftTemperatureCelcius)?.let {
        leftTemperatureCelcius = it
      }
      table?.getDouble("rightTemperatureCelcius", leftTemperatureCelcius)?.let {
        rightTemperatureCelcius = it
      }
    }
  }

  fun updateInputs(inputs: TelescopingClimberIOInputs) {}

  fun setLeftOpenLoop(percentOutput: Double) {}
  fun setRightOpenLoop(percentOutput: Double) {}

  fun setLeftPosition(height: Length, feedforward: ElectricalPotential) {}
  fun setRightPosition(height: Length, feedforward: ElectricalPotential) {}

  fun zeroLeftEncoder() {}
  fun zeroRightEncoder() {}
}
