package com.team4099.robot2022.subsystems.pneumatics

import com.team4099.lib.units.base.amps
import com.team4099.lib.units.base.inAmperes
import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface PneumaticIO {
  class PneumaticsIOInputs : LoggableInputs {
    var pressurePsi: Double = 0.0
    var compressorActive: Boolean = false
    var compressorCurrentAmps = 0.0.amps

    override fun toLog(table: LogTable?) {
      table?.put("PressurePsi", pressurePsi)
      table?.put("CompressorActive", compressorActive)
      table?.put("CompressorCurrentAmps", compressorCurrentAmps.inAmperes)
    }

    override fun fromLog(table: LogTable?) {
      table?.getDouble("PressurePsi", pressurePsi)?.let { pressurePsi = it }
      table?.getBoolean("CompressorActive", compressorActive)?.let { compressorActive = it }
      table?.getDouble("CompressorCurrentAmps", compressorCurrentAmps.inAmperes)?.let {
        compressorCurrentAmps = it.amps
      }
    }
  }

  fun updateInputs(inputs: PneumaticsIOInputs) {}

  fun useLowClosedLoopThresholds(useLow: Boolean) {}
}
