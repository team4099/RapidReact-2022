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

  // Pair<flywheel velocity, backwheels velocity>
  private var lastRecordedSpeeds = mutableListOf<Pair<AngularVelocity, AngularVelocity>>()

  private val flywheel_kP =
    TunableNumber("Shooter/flywheel_kP", ShooterConstants.SHOOTER_FLYWHEEL_KP)
  private val flywheel_kI =
    TunableNumber("Shooter/flywheel_kI", ShooterConstants.SHOOTER_FLYWHEEL_KI)
  private val flywheel_kD =
    TunableNumber("Shooter/flywheel_kD", ShooterConstants.SHOOTER_FLYWHEEL_KD)

  private val backwheels_kP =
    TunableNumber("Shooter/backwheels_kP", ShooterConstants.SHOOTER_BACKWHEELS_KP)
  private val backwheels_kI =
    TunableNumber("Shooter/backwheels_kI", ShooterConstants.SHOOTER_BACKWHEELS_KI)
  private val backwheels_kD =
    TunableNumber("Shooter/backwheels_kD", ShooterConstants.SHOOTER_BACKWHEELS_KD)

  //  private val nearSpeedRM =
  //    TunableNumber(
  //      "Shooter/targetRPM",
  //      ShooterConstants.ShooterState.SPIN_UP_UPPER.targetVelocity.inRotationsPerMinute
  //    )

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

  var targetVelocity: Pair<AngularVelocity, AngularVelocity> =
    Pair(0.0.rotations.perMinute, 0.0.rotations.perMinute)
    set(value) {
        io.setVelocity(value)
        field = value
      }

  var isOnTarget = false

  init {
    state = state
  }

  fun setOpenLoop(flywheelPower: Double, backwheelsPower: Double) {
    io.setFlywheelOpenLoop(flywheelPower)
    io.setBackwheelsOpenLoop(backwheelsPower)
  }

  override fun periodic() {
    io.updateInputs(inputs)

    lastRecordedSpeeds.add(Pair(inputs.flywheelVelocity, inputs.backwheelsVelocity))

    if (lastRecordedSpeeds.size > filterSize.get()) {
      lastRecordedSpeeds.removeAt(0)
    }

    //    isOnTarget =
    //        targetVelocity != 0.0.rotations.perMinute &&
    //            filter.calculate(inputs.velocity.inRotationsPerMinute)
    //                .around(targetVelocity.inRotationsPerMinute, shooterToleranceRPM.value)

    isOnTarget =
      targetVelocity != Pair(0.0.rotations.perMinute, 0.0.rotations.perMinute) &&
      lastRecordedSpeeds.all {
        it.first.inRotationsPerMinute.around(
          targetVelocity.first.inRotationsPerMinute, shooterToleranceRPM.get()
        ) &&
          it.second.inRotationsPerMinute.around(
            targetVelocity.second.inRotationsPerMinute, shooterToleranceRPM.get()
          )
      }

    Logger.getInstance().processInputs("Shooter", inputs)
    Logger.getInstance()
      .recordOutput("Shooter/flywheelSetpointRPM", targetVelocity.first.inRotationsPerMinute)
    Logger.getInstance()
      .recordOutput("Shooter/backwheelsSetpointRPM", targetVelocity.second.inRotationsPerMinute)
    Logger.getInstance().recordOutput("Shooter/isOnTarget", isOnTarget)
    Logger.getInstance().recordOutput("Shooter/state", state.name)

    if (flywheel_kP.hasChanged() || flywheel_kI.hasChanged() || flywheel_kD.hasChanged()) {
      io.configureFlywheelPID(flywheel_kP.get(), flywheel_kI.get(), flywheel_kD.get())
    }
    if (backwheels_kD.hasChanged() || backwheels_kI.hasChanged() || backwheels_kD.hasChanged()) {
      io.configureBackwheelsPID(backwheels_kP.get(), backwheels_kI.get(), backwheels_kD.get())
    }

    if (filterSize.hasChanged()) {
      filter = MedianFilter(filterSize.get().toInt())
      lastRecordedSpeeds = mutableListOf()
    }

    //    if (nearSpeedRM.hasChanged()) {
    //      targetVelocity = nearSpeedRM.value.rotations.perMinute
    //    }
  }
}
