package com.team4099.robot2022.subsystems.led

import com.team4099.lib.drivers.BlinkinLedDriver
import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface LedIO {
  class LedIOInputs : LoggableInputs {

    var pwmValue = 0.0

    override fun toLog(table: LogTable?) {
      table?.put("pwmValue", pwmValue)
    }

    override fun fromLog(table: LogTable?) {
      table?.getDouble("pwmValue", pwmValue)?.let { pwmValue = it }
    }
  }

  fun updateInputs(inputs: LedIOInputs) {}

  fun setMode(mode: BlinkinLedDriver.BlinkinLedMode) {}
}
