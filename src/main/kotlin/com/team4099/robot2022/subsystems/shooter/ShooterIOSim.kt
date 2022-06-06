package com.team4099.robot2022.subsystems.shooter

import com.team4099.lib.units.AngularVelocity
import com.team4099.lib.units.base.amps
import com.team4099.lib.units.derived.inVolts
import com.team4099.lib.units.derived.radians
import com.team4099.lib.units.derived.volts
import com.team4099.lib.units.perSecond
import com.team4099.robot2022.config.constants.ShooterConstants
import edu.wpi.first.math.MathUtil
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.system.plant.DCMotor
import edu.wpi.first.wpilibj.simulation.FlywheelSim

object ShooterIOSim : ShooterIO {
  private val motorSim: DCMotor = DCMotor.getFalcon500(1)

  private val flywheelSim: FlywheelSim =
    FlywheelSim(
      motorSim,
      ShooterConstants.SHOOTER_SENSOR_GEAR_RATIO,
      ShooterConstants.FLYWHEEL_MOMENT_OF_INERTIA
    )

  private val pid: PIDController = PIDController(0.0, 0.0, 0.0)

  private var closedLoop = false
  private const val ffVolts = 0.0
  private var appliedVolts = 0.0

  override fun updateInputs(inputs: ShooterIO.ShooterIOInputs) {
    if (closedLoop) {
      appliedVolts =
        MathUtil.clamp(
          pid.calculate(flywheelSim.angularVelocityRadPerSec) + ffVolts,
          -12.0.volts.inVolts,
          12.0.volts.inVolts
        )
    }
    inputs.position = 0.0.radians
    inputs.velocity = flywheelSim.angularVelocityRadPerSec.radians.perSecond
    inputs.appliedVoltage = appliedVolts.volts
    inputs.supplyCurrent[0] = flywheelSim.currentDrawAmps.amps
  }

  override fun setOpenLoop(power: Double) {
    closedLoop = false
    appliedVolts = MathUtil.clamp(power * 12.0, -12.0, 12.0)
    flywheelSim.setInputVoltage(appliedVolts)
  }

  override fun setVelocity(velocity: AngularVelocity) {
    closedLoop = true
    pid.setpoint = velocity.value
  }

  override fun configurePID(kP: Double, kI: Double, kD: Double) {
    pid.setPID(kP, kI, kD)
  }
}
