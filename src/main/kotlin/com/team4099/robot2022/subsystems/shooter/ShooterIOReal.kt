package com.team4099.robot2022.subsystems.shooter

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.DemandType
import com.ctre.phoenix.motorcontrol.InvertType
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.team4099.lib.units.AngularVelocity
import com.team4099.lib.units.base.amps
import com.team4099.lib.units.ctreAngularMechanismSensor
import com.team4099.lib.units.derived.rotations
import com.team4099.lib.units.derived.volts
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.lib.units.perMinute
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.Constants.Universal.CANIVORE_NAME
import com.team4099.robot2022.config.constants.ShooterConstants

object ShooterIOReal : ShooterIO {
  private val leaderMotor = TalonFX(Constants.Shooter.LEADER_MOTOR_ID, CANIVORE_NAME)
  //  private val followerMotor = TalonFX(Constants.Shooter.FOLLOWER_MOTOR_ID, CANIVORE_NAME)

  private val shooterSensor =
    ctreAngularMechanismSensor(
      leaderMotor,
      ShooterConstants.SHOOTER_SENSOR_CPR,
      ShooterConstants.SHOOTER_SENSOR_GEAR_RATIO
    )

  init {
    leaderMotor.configFactoryDefault()
    //    followerMotor.configFactoryDefault()

    //    followerMotor.follow(leaderMotor)
    leaderMotor.setInverted(InvertType.None)
    //    followerMotor.setInverted(InvertType.OpposeMaster)

    leaderMotor.clearStickyFaults()
    //    followerMotor.clearStickyFaults()
    leaderMotor.enableVoltageCompensation(true)
    //    followerMotor.enableVoltageCompensation(true)
    leaderMotor.configVoltageCompSaturation(12.0)
    //    followerMotor.configVoltageCompSaturation(12.0)

    leaderMotor.configSupplyCurrentLimit(
      SupplyCurrentLimitConfiguration(
        true,
        ShooterConstants.SUPPLY_CURRENT_LIMIT,
        ShooterConstants.SUPPLY_CURRENT_LIMIT,
        1000.0
      )
    )
    //    followerMotor.configSupplyCurrentLimit(
    //      SupplyCurrentLimitConfiguration(
    //        true,
    //        ShooterConstants.SUPPLY_CURRENT_LIMIT,
    //        ShooterConstants.SUPPLY_CURRENT_LIMIT,
    //        1000.0
    //      )
    //    )

    leaderMotor.config_kP(0, ShooterConstants.SHOOTER_KP)
    leaderMotor.config_kI(0, ShooterConstants.SHOOTER_KI)
    leaderMotor.config_kD(0, ShooterConstants.SHOOTER_KD)

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

  override fun updateInputs(inputs: ShooterIO.ShooterIOInputs) {
    inputs.position = shooterSensor.position
    inputs.velocity = shooterSensor.velocity
    inputs.statorCurrent[0] = leaderMotor.statorCurrent.amps
    //    inputs.statorCurrent[1] = followerMotor.statorCurrent.amps
    inputs.supplyCurrent[0] = leaderMotor.supplyCurrent.amps
    //    inputs.supplyCurrent[1] = followerMotor.supplyCurrent.amps
    inputs.tempCelcius[0] = leaderMotor.temperature
    //    inputs.tempCelcius[1] = followerMotor.temperature

    inputs.appliedVoltage = leaderMotor.motorOutputVoltage.volts
  }

  override fun setVelocity(velocity: AngularVelocity) {
    if (velocity.absoluteValue <= 600.rotations.perMinute) {
      setOpenLoop(0.0)
    } else {
      leaderMotor.set(
        ControlMode.Velocity,
        shooterSensor.velocityToRawUnits(velocity),
        DemandType.ArbitraryFeedForward,
        (
          ShooterConstants.SHOOTER_KS_VOLTS +
            ShooterConstants.SHOOTER_KV_VOLTS_PER_RADIAN_PER_SECOND *
            velocity.inRadiansPerSecond
          ) / 12.0
      )
    }
  }

  override fun setOpenLoop(power: Double) {
    leaderMotor.set(ControlMode.PercentOutput, power)
  }

  override fun configurePID(kP: Double, kI: Double, kD: Double) {
    leaderMotor.config_kP(0, kP)
    leaderMotor.config_kI(0, kI)
    leaderMotor.config_kD(0, kD)
  }
}
