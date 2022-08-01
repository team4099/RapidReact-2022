package com.team4099.lib.units.base

import com.team4099.lib.units.UnitKey
import com.team4099.lib.units.Value

object Celsius : UnitKey
// Somewhat misleading to use UnitKey because adding celsius and farenheit together
// will produce different results if you convert them both to celsius or farenheit first
// so addition/subtraction between temperatures should not be done (although supported)

// The incorporation of UnitKey does allow scalar multiplication and division which might be useful
// but addition and subtraction will produce misleading results

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
