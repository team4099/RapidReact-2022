package com.team4099.robot2022.subsystems.shooter

import com.team4099.lib.around
import com.team4099.lib.logging.TunableNumber
import com.team4099.lib.units.AngularVelocity
import com.team4099.lib.units.derived.rotations
import com.team4099.lib.units.inRotationsPerMinute
import com.team4099.lib.units.perMinute
import com.team4099.robot2022.config.constants.ShooterConstants
import edu.wpi.first.math.filter.MedianFilter
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class Shooter(val io: ShooterIO) : SubsystemBase() {
  val inputs = ShooterIO.ShooterIOInputs()
  private var filter = MedianFilter(ShooterConstants.FILTER_SIZE)

  private var lastRecordedSpeeds = mutableListOf<AngularVelocity>()

  private val kP = TunableNumber("Shooter/kP", ShooterConstants.SHOOTER_KP)
  private val kI = TunableNumber("Shooter/kI", ShooterConstants.SHOOTER_KI)
  private val kD = TunableNumber("Shooter/kD", ShooterConstants.SHOOTER_KD)

  private val nearSpeedRM =
    TunableNumber(
      "Shooter/targetRPM",
      ShooterConstants.ShooterState.SPIN_UP_UPPER.targetVelocity.inRotationsPerMinute
    )

  private val filterSize =
    TunableNumber("Shooter/filterSize", ShooterConstants.FILTER_SIZE.toDouble())
  private val shooterToleranceRPM =
    TunableNumber(
      "Shooter/toleranceRPM", ShooterConstants.TARGET_VELOCITY_THRESHOLD.inRotationsPerMinute
    )

  var state = ShooterConstants.ShooterState.OFF
    set(state) {
      //      targetVelocity = state.targetVelocity
      targetVelocity = state.targetVelocity
      field = state
    }

  var targetVelocity = 0.0.rotations.perMinute
    set(value) {
      io.setVelocity(value)
      field = value
    }

  var isOnTarget = false

  init {
    state = state
  }

  fun setOpenLoop(power: Double) {
    io.setOpenLoop(power)
  }

  override fun periodic() {
    io.updateInputs(inputs)

    lastRecordedSpeeds.add(inputs.velocity)

    if (lastRecordedSpeeds.size > filterSize.value) {
      lastRecordedSpeeds.removeAt(0)
    }

    //    isOnTarget =
    //        targetVelocity != 0.0.rotations.perMinute &&
    //            filter.calculate(inputs.velocity.inRotationsPerMinute)
    //                .around(targetVelocity.inRotationsPerMinute, shooterToleranceRPM.value)

    isOnTarget =
      targetVelocity != 0.0.rotations.perMinute &&
      lastRecordedSpeeds.all {
        it.inRotationsPerMinute.around(
          targetVelocity.inRotationsPerMinute, shooterToleranceRPM.value
        )
      }

    Logger.getInstance().processInputs("Shooter", inputs)
    Logger.getInstance().recordOutput("Shooter/setpointRPM", targetVelocity.inRotationsPerMinute)
    Logger.getInstance().recordOutput("Shooter/isOnTarget", isOnTarget)
    Logger.getInstance().recordOutput("Shooter/state", state.name)

    if (kP.hasChanged() || kI.hasChanged() || kD.hasChanged()) {
      io.configurePID(kP.value, kI.value, kD.value)
    }

    if (filterSize.hasChanged()) {
      filter = MedianFilter(filterSize.value.toInt())
      lastRecordedSpeeds = mutableListOf()
    }

    //    if (nearSpeedRM.hasChanged()) {
    //      targetVelocity = nearSpeedRM.value.rotations.perMinute
    //    }
  }
}
