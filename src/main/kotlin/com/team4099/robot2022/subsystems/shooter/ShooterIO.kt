package com.team4099.robot2022.subsystems.shooter

import com.team4099.lib.units.AngularVelocity
import com.team4099.lib.units.base.amps
import com.team4099.lib.units.base.inAmperes
import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.derived.inVolts
import com.team4099.lib.units.derived.radians
import com.team4099.lib.units.derived.rotations
import com.team4099.lib.units.derived.volts
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.lib.units.inRotationsPerMinute
import com.team4099.lib.units.perMinute
import com.team4099.lib.units.perSecond
import com.team4099.robot2022.config.constants.ShooterConstants
import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface ShooterIO {
  class ShooterIOInputs : LoggableInputs {
    var position = 0.0.radians
    var velocity = 0.0.radians.perSecond

    var appliedVoltage = 0.0.volts
    var statorCurrent = mutableListOf(0.0.amps, 0.0.amps)
    var supplyCurrent = mutableListOf(0.0.amps, 0.0.amps)
    var tempCelcius = mutableListOf(0.0, 0.0)

    override fun toLog(table: LogTable?) {
      table?.put("positionRad", position.inRadians)
      table?.put("velocityRPM", velocity.inRotationsPerMinute)
      table?.put("appliedVolts", appliedVoltage.inVolts)
      table?.put("statorCurrentAmps", statorCurrent.map { it.inAmperes }.toDoubleArray())
      table?.put("supplyCurrentAmps", supplyCurrent.map { it.inAmperes }.toDoubleArray())
      table?.put("tempCelcius", tempCelcius.toDoubleArray())
    }

    override fun fromLog(table: LogTable?) {
      table?.getDouble("positionRad", position.inRadians)?.let { position = it.radians }
      table?.getDouble("velocityRPM", velocity.inRotationsPerMinute)?.let {
        velocity = it.rotations.perMinute
      }
      table?.getDouble("appliedVolts", appliedVoltage.inVolts)?.let { appliedVoltage = it.volts }
      table?.getDoubleArray("statorCurrentAmps", statorCurrent.map { it.inAmperes }.toDoubleArray())
          ?.let { statorCurrent = it.map { it.amps }.toMutableList() }
      table?.getDoubleArray("supplyCurrentAmps", supplyCurrent.map { it.inAmperes }.toDoubleArray())
          ?.let { supplyCurrent = it.map { it.amps }.toMutableList() }
      table?.getDoubleArray("tempCelcius", tempCelcius.toDoubleArray())?.let {
        tempCelcius = it.toMutableList()
      }
    }
  }

  fun updateInputs(inputs: ShooterIOInputs) {}

  fun setShooterState(shooterState: ShooterConstants.ShooterState) {}

  fun setVelocity(velocity: AngularVelocity) {}

  fun setOpenLoop(power: Double) {}

  fun configurePID(kP: Double, kI: Double, kD: Double) {}
}
