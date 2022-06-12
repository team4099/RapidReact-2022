package com.team4099.robot2022.subsystems.shooter

import com.team4099.lib.units.AngularVelocity
import com.team4099.lib.units.base.amps
import com.team4099.lib.units.base.inAmperes
import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.derived.inVolts
import com.team4099.lib.units.derived.radians
import com.team4099.lib.units.derived.rotations
import com.team4099.lib.units.derived.volts
import com.team4099.lib.units.inRotationsPerMinute
import com.team4099.lib.units.perMinute
import com.team4099.lib.units.perSecond
import com.team4099.robot2022.config.constants.ShooterConstants
import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface ShooterIO {
  class ShooterIOInputs : LoggableInputs {
    var flywheelPosition = 0.0.radians
    var backwheelsPosition = 0.0.radians

    var flywheelVelocity = 0.0.radians.perSecond
    var backwheelsVelocity = 0.0.radians.perSecond

    var flywheelAppliedVoltage = 0.0.volts
    var backwheelsAppliedVoltage = 0.0.volts

    var flywheelStatorCurrent = 0.0.amps
    var backwheelsStatorCurrent = 0.0.amps

    var flywheelSupplyCurrent = 0.0.amps
    var backwheelsSupplyCurrent = 0.0.amps

    var flywheelTempCelcius = 0.0
    var backwheelsTempCelcius = 0.0

    override fun toLog(table: LogTable?) {
      table?.put("flywheelPositionRad", flywheelPosition.inRadians)
      table?.put("backwheelsPositionRad", backwheelsPosition.inRadians)

      table?.put("flywheelVelocityRPM", flywheelVelocity.inRotationsPerMinute)
      table?.put("backwheelsVelocityRPM", backwheelsVelocity.inRotationsPerMinute)

      table?.put("flywheelAppliedVolts", flywheelAppliedVoltage.inVolts)
      table?.put("backwheelsAppliedVolts", backwheelsAppliedVoltage.inVolts)

      table?.put("flywheelStatorCurrentAmps", flywheelStatorCurrent.inAmperes)
      table?.put("backwheelsStatorCurrentAmps", backwheelsStatorCurrent.inAmperes)

      table?.put("flywheelSupplyCurrentAmps", flywheelSupplyCurrent.inAmperes)
      table?.put("backwheelsSupplyCurrentAmps", backwheelsSupplyCurrent.inAmperes)

      table?.put("flywheelTempCelcius", flywheelTempCelcius)
      table?.put("backwheelsTempCelcius", backwheelsTempCelcius)
    }

    override fun fromLog(table: LogTable?) {
      table?.getDouble("flywheelPositionRad", flywheelPosition.inRadians)?.let {
        flywheelPosition = it.radians
      }
      table?.getDouble("backwheelsPositionRad", backwheelsPosition.inRadians)?.let {
        backwheelsPosition = it.radians
      }

      table?.getDouble("flywheelVelocityRPM", backwheelsVelocity.inRotationsPerMinute)?.let {
        backwheelsVelocity = it.rotations.perMinute
      }
      table?.getDouble("backwheelsVelocityRPM", backwheelsVelocity.inRotationsPerMinute)?.let {
        backwheelsVelocity = it.rotations.perMinute
      }

      table?.getDouble("flywheelAppliedVolts", flywheelAppliedVoltage.inVolts)?.let {
        flywheelAppliedVoltage = it.volts
      }
      table?.getDouble("backwheelsAppliedVolts", backwheelsAppliedVoltage.inVolts)?.let {
        backwheelsAppliedVoltage = it.volts
      }

      table?.getDouble("flywheelStatorCurrentAmps", flywheelStatorCurrent.inAmperes)?.let {
        flywheelStatorCurrent = it.amps
      }
      table?.getDouble("backwheelStatorCurrentAmps", backwheelsStatorCurrent.inAmperes)?.let {
        backwheelsStatorCurrent = it.amps
      }

      table?.getDouble("flywheelStatorCurrentAmps", flywheelSupplyCurrent.inAmperes)?.let {
        flywheelSupplyCurrent = it.amps
      }
      table?.getDouble("backwheelStatorCurrentAmps", backwheelsSupplyCurrent.inAmperes)?.let {
        backwheelsSupplyCurrent = it.amps
      }

      table?.getDouble("flywheelTempCelcius", flywheelTempCelcius)?.let { flywheelTempCelcius = it }
      table?.getDouble("backwheelTempCelcius", backwheelsTempCelcius)?.let {
        backwheelsTempCelcius = it
      }
    }
  }

  fun updateInputs(inputs: ShooterIO.ShooterIOInputs) {}

  fun setShooterState(shooterState: ShooterConstants.ShooterState) {}

  fun setVelocity(velocity: Pair<AngularVelocity, AngularVelocity>) {}

  fun setFlywheelOpenLoop(power: Double) {}

  fun setBackwheelsOpenLoop(power: Double) {}

  fun configureFlywheelPID(kP: Double, kI: Double, kD: Double) {}

  fun configureBackwheelsPID(kP: Double, kI: Double, kD: Double) {}
}
