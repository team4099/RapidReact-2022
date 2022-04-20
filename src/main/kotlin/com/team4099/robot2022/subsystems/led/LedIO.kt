package com.team4099.robot2022.subsystems.led

import com.team4099.lib.drivers.BlinkinLedDriver
import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface LedIO {
  class LedIOInputs : LoggableInputs {

    var ledState = BlinkinLedDriver.BlinkinLedMode.SOLID_GOLD.name

    override fun toLog(table: LogTable?) {
      table?.put("ledState", ledState)
    }

    override fun fromLog(table: LogTable?) {
      table?.getString("ledState", ledState)?.let { ledState = it }
    }
  }

  fun updateInputs(inputs: LedIOInputs) {}

  fun setMode(mode: BlinkinLedDriver.BlinkinLedMode) {}
}
