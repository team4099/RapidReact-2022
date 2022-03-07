package com.team4099.robot2022.subsystems.shooter

import com.team4099.lib.around
import com.team4099.lib.logging.TunableNumber
import com.team4099.lib.units.inRotationsPerMinute
import com.team4099.robot2022.config.constants.ShooterConstants
import edu.wpi.first.math.filter.MedianFilter
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class Shooter(val io: ShooterIO) : SubsystemBase() {
  val inputs = ShooterIO.ShooterIOInputs()
  private var filter = MedianFilter(ShooterConstants.FILTER_SIZE)

  private val kP = TunableNumber("Shooter/kP", ShooterConstants.SHOOTER_KP)
  private val kI = TunableNumber("Shooter/kI", ShooterConstants.SHOOTER_KI)
  private val kD = TunableNumber("Shooter/kD", ShooterConstants.SHOOTER_KD)

  private val filterSize = TunableNumber("Shooter/filterSize", ShooterConstants.FILTER_SIZE.toDouble())
  private val shooterToleranceRPM = TunableNumber("Shooter/toleranceRPM", ShooterConstants.TARGET_VELOCITY_THRESHOLD.inRotationsPerMinute)

  var state = ShooterConstants.ShooterState.OFF
    set(state) {
      io.setShooterState(state)
      field = state
    }

  var isOnTarget = false

  init {
    state = state
  }

  fun setOpenLoop(power: Double) {
    io.setOpenLoop(power)
  }

  override fun periodic() {
    isOnTarget =
        state != ShooterConstants.ShooterState.OFF &&
            filter.calculate(inputs.velocity.inRotationsPerMinute)
                .around(
                    state.targetVelocity.inRotationsPerMinute,
                    shooterToleranceRPM.get())

    Logger.getInstance().processInputs("Shooter", inputs)
    Logger.getInstance()
        .recordOutput("Shooter/setpointRPM", state.targetVelocity.inRotationsPerMinute)
    Logger.getInstance().recordOutput("Shooter/isOnTarget", isOnTarget)
    Logger.getInstance().recordOutput("Shooter/state", state.name)

    if (kP.hasChanged() || kI.hasChanged() || kD.hasChanged()) {
      io.configurePID(kP.get(), kI.get(), kD.get())
    }

    if (filterSize.hasChanged()) {
      filter = MedianFilter(filterSize.get().toInt())
    }

  }
}
