package com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.DemandType
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.team4099.lib.logging.Logger
import com.team4099.lib.units.AngularVelocity
import com.team4099.lib.units.ctreAngularMechanismSensor
import com.team4099.lib.units.inRotationsPerMinute
import com.team4099.lib.units.inRotationsPerSecond
import com.team4099.robot2022.config.ShooterConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase

object Shooter : SubsystemBase() {
  private val leaderMotor = TalonFX(ShooterConstants.LEADER_MOTOR_ID)
  private val followerMotor = TalonFX(ShooterConstants.FOLLOWER_MOTOR_ID)

  private val shooterSensor =
      ctreAngularMechanismSensor(
          leaderMotor,
          ShooterConstants.SHOOTER_SENSOR_CPR,
          ShooterConstants.SHOOTER_SENSOR_GEAR_RATIO)

  var shooterState = ShooterConstants.ShooterState.IDLE
    set(state) {
      setVelocity(state.rotationsPerMinute)
      field = state
    }

  val shooterVelocity
    get() = shooterSensor.velocity

  init {
    leaderMotor.configFactoryDefault()

    followerMotor.configFactoryDefault()
    followerMotor.follow(leaderMotor)

    leaderMotor.config_kP(0, ShooterConstants.SHOOTER_KP)
    leaderMotor.config_kI(0, ShooterConstants.SHOOTER_KI)
    leaderMotor.config_kD(0, ShooterConstants.SHOOTER_KD)

    Logger.addSource("Shooter", "Shooter Velocity (rpm)") { shooterVelocity.inRotationsPerMinute }

    Logger.addSource("Shooter", "Shooter Leader Temperature") { leaderMotor.temperature }

    Logger.addSource("Shooter", "Shooter Follower Temperature") { followerMotor.temperature }

    Logger.addSource("Shooter", "Shooter Leader Bus Voltage") { leaderMotor.busVoltage }

    Logger.addSource("Shooter", "Shooter Follower Bus Voltage") { followerMotor.busVoltage }

    Logger.addSource("Shooter", "Shooter Leader Supply Current") { leaderMotor.supplyCurrent }

    Logger.addSource("Shooter", "Shooter Follower Supply Current") { followerMotor.supplyCurrent }

    Logger.addSource("Shooter", "Shooter Leader Stator Current") { leaderMotor.statorCurrent }

    Logger.addSource("Shooter", "Shooter Follower Stator Current") { followerMotor.statorCurrent }
  }

  private fun setVelocity(velocity: AngularVelocity) =
      leaderMotor.set(
          ControlMode.Velocity,
          shooterSensor.velocityToRawUnits(velocity),
          DemandType.ArbitraryFeedForward,
          (ShooterConstants.SHOOTER_KS +
              ShooterConstants.SHOOTER_KV * velocity.inRotationsPerSecond) / 12.0)
}
