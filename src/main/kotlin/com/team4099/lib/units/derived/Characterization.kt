package com.team4099.lib.units.derived

import com.team4099.lib.units.Acceleration
import com.team4099.lib.units.Fraction
import com.team4099.lib.units.UnitKey
import com.team4099.lib.units.Value
import com.team4099.lib.units.Velocity

typealias ElectricalPotentialPerVelocity<T> = Value<Fraction<Volt, Velocity<T>>>

typealias ElectricalPotentialPerAcceleration<T> = Value<Fraction<Volt, Acceleration<T>>>

operator fun <T : UnitKey> ElectricalPotential.div(o: Value<T>): Value<Fraction<Volt, T>> =
    Value(value / o.value)
