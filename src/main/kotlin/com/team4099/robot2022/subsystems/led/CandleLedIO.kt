package com.team4099.robot2022.subsystems.led

import com.ctre.phoenix.led.Animation
import com.ctre.phoenix.led.ColorFlowAnimation
import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface CandleLedIO {
  class LEDIOInputs : LoggableInputs {
    var animation: Animation = ColorFlowAnimation(0, 0, 0)
    var red: Int = 0
    var green: Int = 0
    var blue: Int = 0

    override fun toLog(table: LogTable?) {}

    override fun fromLog(table: LogTable?) {}
  }

  fun updateInputs(inputs: CandleLedIO.LEDIOInputs) {}

  fun setAnimation(animation: Animation?, red: Int, green: Int, blue: Int) {}
  fun setRed(red: Int) {}
  fun setGreen(green: Int) {}
  fun setBlue(blue: Int) {}
}
