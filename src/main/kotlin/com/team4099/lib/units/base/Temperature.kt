package com.team4099.lib.units.base

import com.team4099.lib.units.UnitKey
import com.team4099.lib.units.Value

object Celsius : UnitKey

typealias Temperature = Value<Celsius>

val Double.celsius: Temperature
  get() = Temperature(this)

val Double.fahrenheit: Temperature
  get() = Temperature((this - 32.0) * 5.0 / 9.0)

val Number.celsius: Temperature
  get() = toDouble().celsius

val Number.fahrenheit: Temperature
  get() = toDouble().fahrenheit

val Temperature.inCelsius: Double
  get() = value

val Temperature.inFahrenheit: Double
  get() = (value * 9.0 / 5.0) + 32.0
