package com.team4099.robot2022.subsystems.shooter

import com.team4099.lib.around
import com.team4099.lib.units.inRotationsPerMinute
import com.team4099.robot2022.config.constants.ShooterConstants
import edu.wpi.first.math.filter.MedianFilter
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class Shooter(val io: ShooterIO) : SubsystemBase() {
  val inputs = ShooterIO.ShooterIOInputs()
  private val filter = MedianFilter(100)

  var shooterState = ShooterConstants.ShooterState.OFF
    set(state) {
      io.setShooterState(state)
      field = state
    }

  var isOnTarget = false

  init {}

  fun setOpenLoop(power: Double) {
    io.setOpenLoop(power)
  }

  override fun periodic() {
    isOnTarget =
        shooterState != ShooterConstants.ShooterState.OFF &&
            filter.calculate(inputs.velocity.inRotationsPerMinute)
                .around(
                    shooterState.targetVelocity.inRotationsPerMinute,
                    ShooterConstants.TARGET_VELOCITY_THRESHOLD.inRotationsPerMinute)

    Logger.getInstance()
        .recordOutput("Shooter/setpointRPM", shooterState.targetVelocity.inRotationsPerMinute)
    Logger.getInstance().recordOutput("Shooter/isOnTarget", isOnTarget)
  }
}
