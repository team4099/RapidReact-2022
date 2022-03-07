package com.team4099.robot2022.subsystems.feeder

import com.team4099.lib.units.base.amps
import com.team4099.lib.units.base.inAmperes
import com.team4099.lib.units.derived.inVolts
import com.team4099.lib.units.derived.volts
import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface FeederIO {
  class FeederIOInputs : LoggableInputs {
    var topBeamBroken = false
    var bottomBeamBroken = false

    var floorAppliedVoltage = 0.0.volts
    var verticalAppliedVoltage = 0.0.volts
    var floorStatorCurrent = 0.0.amps
    var floorSupplyCurrent = 0.0.amps
    var verticalStatorCurrent = 0.0.amps
    var verticalSupplyCurrent = 0.0.amps

    var floorTempCelcius = 0.0
    var verticalTempCelcius = 0.0

    override fun toLog(table: LogTable?) {
      table?.put("topBeamBroken", topBeamBroken)
      table?.put("bottomBeamBroken", bottomBeamBroken)
      table?.put("floorAppliedVoltage", floorAppliedVoltage.inVolts)
      table?.put("verticalAppliedVoltage", verticalAppliedVoltage.inVolts)
      table?.put("floorStatorCurrent", floorStatorCurrent.inAmperes)
      table?.put("floorSupplyCurrent", floorSupplyCurrent.inAmperes)
      table?.put("verticalStatorCurrent", verticalStatorCurrent.inAmperes)
      table?.put("verticalSupplyCurrent", verticalSupplyCurrent.inAmperes)
      table?.put("floorTempCelcius", floorTempCelcius)
      table?.put("verticalTempCelcius", verticalTempCelcius)
    }

    override fun fromLog(table: LogTable?) {
      table?.getBoolean("topBeamBroken", topBeamBroken)?.let { topBeamBroken = it }
      table?.getBoolean("bottomBeamBroken", bottomBeamBroken)?.let { bottomBeamBroken = it }
      table?.getDouble("floorAppliedVoltage", floorAppliedVoltage.inVolts)?.let {
        floorAppliedVoltage = it.volts
      }
      table?.getDouble("verticalAppliedVoltage", verticalAppliedVoltage.inVolts)?.let {
        verticalAppliedVoltage = it.volts
      }
      table?.getDouble("floorStatorCurrent", floorStatorCurrent.inAmperes)?.let {
        floorStatorCurrent = it.amps
      }
      table?.getDouble("floorSupplyCurrent", floorSupplyCurrent.inAmperes)?.let {
        floorSupplyCurrent = it.amps
      }
      table?.getDouble("verticalStatorCurrent", verticalStatorCurrent.inAmperes)?.let {
        verticalStatorCurrent = it.amps
      }
      table?.getDouble("verticalSupplyCurrent", verticalSupplyCurrent.inAmperes)?.let {
        verticalSupplyCurrent = it.amps
      }
      table?.getDouble("floorTempCelcius", floorTempCelcius)?.let { floorTempCelcius = it }
      table?.getDouble("verticalTempCelcius", verticalTempCelcius)?.let { verticalTempCelcius = it }
    }
  }

  fun updateInputs(inputs: FeederIOInputs) {}

  fun setPower(floorPower: Double, verticalPower: Double) {}
}
