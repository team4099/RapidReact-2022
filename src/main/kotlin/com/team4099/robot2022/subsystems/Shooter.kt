package com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.team4099.lib.logging.Logger
import com.team4099.lib.units.ctreAngularMechanismSensor
import com.team4099.robot2022.config.Constants
import com.team4099.robot2022.config.Constants.ShooterConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase

object Shooter : SubsystemBase() {
  private val leaderMotor = TalonFX(ShooterConstants.LEADER_MOTOR_ID)
  private val followerMotor = TalonFX(ShooterConstants.FOLLOWER_MOTOR_ID)

  private val shooterSensor = ctreAngularMechanismSensor(leaderMotor, 2048, 1.0)

  var shooterState = ShooterConstants.ShooterState.IDLE
    set (value) {
      when (value) {
        ShooterConstants.ShooterState.IDLE -> {
          leaderMotor.set(ControlMode.PercentOutput, 0.0)
        }
        ShooterConstants.ShooterState.SPIN_UP -> {
          leaderMotor.set(ControlMode.PercentOutput, 1.0)
        }
      }
      field = value
    }

  val shooterVelocity
    get() = shooterSensor.velocity

  init {
    leaderMotor.configFactoryDefault()

    followerMotor.configFactoryDefault()
    followerMotor.follow(leaderMotor)

    Logger.addSource("Shooter", "Shooter Velocity (meters per second)") {
      shooterVelocity.inMetersPerSecond
    }

    Logger.addSource("Shooter", "Shooter Leader Temperature") {
      leaderMotor.temperature
    }

    Logger.addSource("Shooter", "Shooter Follower Temperature") {
      followerMotor.temperature
    }

    Logger.addSource("Shooter", "Shooter Leader Bus Voltage") {
      leaderMotor.busVoltage
    }

    Logger.addSource("Shooter", "Shooter Follower Bus Voltage") {
      followerMotor.busVoltage
    }

    Logger.addSource("Shooter", "Shooter Leader Supply Current") {
      leaderMotor.supplyCurrent
    }

    Logger.addSource("Shooter", "Shooter Follower Supply Current") {
      followerMotor.supplyCurrent
    }

    Logger.addSource("Shooter", "Shooter Leader Stator Current") {
      leaderMotor.statorCurrent
    }

    Logger.addSource("Shooter", "Shooter Follower Stator Current") {
      followerMotor.statorCurrent
    }
  }
}
