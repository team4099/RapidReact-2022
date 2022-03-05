package com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.DemandType
import com.ctre.phoenix.motorcontrol.InvertType
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.team4099.lib.around
import com.team4099.lib.logging.Logger
import com.team4099.lib.units.AngularVelocity
import com.team4099.lib.units.base.inMilliseconds
import com.team4099.lib.units.ctreAngularMechanismSensor
import com.team4099.lib.units.derived.rotations
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.lib.units.inRotationsPerMinute
import com.team4099.lib.units.perMinute
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.ShooterConstants
import edu.wpi.first.math.filter.MedianFilter
import edu.wpi.first.wpilibj2.command.SubsystemBase

object Shooter : SubsystemBase() {
  private val leaderMotor = TalonFX(ShooterConstants.LEADER_MOTOR_ID)
  private val followerMotor = TalonFX(ShooterConstants.FOLLOWER_MOTOR_ID)

  private val filter = MedianFilter(100)

  private val shooterSensor =
      ctreAngularMechanismSensor(
          leaderMotor,
          ShooterConstants.SHOOTER_SENSOR_CPR,
          ShooterConstants.SHOOTER_SENSOR_GEAR_RATIO)

  var shooterState = ShooterConstants.ShooterState.OFF
    set(state) {
      setVelocity(state.targetVelocity)
      field = state
    }

  val shooterVelocity
    get() = shooterSensor.velocity

  val shooterPosition
    get() = shooterSensor.position

  var isOnTarget = false

  init {
    leaderMotor.configFactoryDefault()
    followerMotor.configFactoryDefault()

    followerMotor.follow(leaderMotor)
    leaderMotor.setInverted(InvertType.None)
    followerMotor.setInverted(InvertType.OpposeMaster)

    leaderMotor.enableVoltageCompensation(true)
    followerMotor.enableVoltageCompensation(true)
    leaderMotor.configVoltageCompSaturation(12.0)
    followerMotor.configVoltageCompSaturation(12.0)

    leaderMotor.config_kP(0, ShooterConstants.SHOOTER_KP)
    leaderMotor.config_kI(0, ShooterConstants.SHOOTER_KI)
    leaderMotor.config_kD(0, ShooterConstants.SHOOTER_KD)

    Logger.addSource("Shooter", "Shooter Velocity (rpm)") { shooterVelocity.inRotationsPerMinute }

    //    Logger.addSource("Shooter", "Shooter Leader Temperature") { leaderMotor.temperature }
    //
    //    Logger.addSource("Shooter", "Shooter Follower Temperature") { followerMotor.temperature }

    Logger.addSource("Shooter", "Shooter Leader Bus Voltage") { leaderMotor.busVoltage }

    Logger.addSource("Shooter", "Shooter Follower Bus Voltage") { followerMotor.busVoltage }

    Logger.addSource("Shooter", "Shooter Leader Supply Current") { leaderMotor.supplyCurrent }

    Logger.addSource("Shooter", "Shooter Follower Supply Current") { followerMotor.supplyCurrent }

    //    Logger.addSource("Shooter", "Shooter Leader Stator Current") { leaderMotor.statorCurrent }
    //
    //    Logger.addSource("Shooter", "Shooter Follower Stator Current") {
    // followerMotor.statorCurrent }

    Logger.addSource("Shooter", "Shooter On Target") { isOnTarget }

    //    leaderMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General,
    // Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    leaderMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0,
    // Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    leaderMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_3_Quadrature,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    leaderMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_4_AinTempVbat,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    //    leaderMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_6_Misc,
//    // Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    leaderMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_8_PulseWidth,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    leaderMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_9_MotProfBuffer,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    leaderMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_10_MotionMagic,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    leaderMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_10_Targets,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    leaderMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_12_Feedback1,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    leaderMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_13_Base_PIDF0,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    leaderMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_14_Turn_PIDF1,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    leaderMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_15_FirmwareApiStatus,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//
//    followerMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_1_General,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    followerMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_2_Feedback0,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    followerMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_3_Quadrature,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    followerMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_4_AinTempVbat,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    followerMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_6_Misc,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    followerMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_8_PulseWidth,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    followerMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_9_MotProfBuffer,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    followerMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_10_MotionMagic,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    followerMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_10_Targets,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    followerMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_12_Feedback1,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    followerMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_13_Base_PIDF0,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    followerMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_14_Turn_PIDF1,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
//    followerMotor.setStatusFramePeriod(
//        StatusFrameEnhanced.Status_15_FirmwareApiStatus,
//        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
  }

  private fun setVelocity(velocity: AngularVelocity) {
    if (velocity <= 600.rotations.perMinute) {
      setOpenLoop(0.0)
    } else {
      leaderMotor.set(
          ControlMode.Velocity,
          shooterSensor.velocityToRawUnits(velocity),
          DemandType.ArbitraryFeedForward,
          (ShooterConstants.SHOOTER_KS_VOLTS +
              ShooterConstants.SHOOTER_KV_VOLTS_PER_RADIAN_PER_SECOND *
                  velocity.inRadiansPerSecond) / 12.0)
    }
  }

  fun setOpenLoop(power: Double) {
    //    println("Shooter Power: $power :p XD (-__-)")
    leaderMotor.set(ControlMode.PercentOutput, power)
  }

  override fun periodic() {
    isOnTarget =
        shooterState != ShooterConstants.ShooterState.OFF &&
            filter.calculate(shooterVelocity.inRotationsPerMinute)
                .around(
                    shooterState.targetVelocity.inRotationsPerMinute,
                    ShooterConstants.TARGET_VELOCITY_THRESHOLD.inRotationsPerMinute)
  }
}
