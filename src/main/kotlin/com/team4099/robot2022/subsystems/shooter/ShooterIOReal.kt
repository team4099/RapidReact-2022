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
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.lib.units.perMinute
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.Constants.Universal.CANIVORE_NAME
import com.team4099.robot2022.config.constants.ShooterConstants

object ShooterIOReal : ShooterIO {
  private val flywheelMotor = TalonFX(Constants.Shooter.FLYWHEEL_MOTOR_ID, CANIVORE_NAME)
  private val backWheelsMotor = TalonFX(Constants.Shooter.BACK_WHEELS_MOTOR_ID, CANIVORE_NAME)

  private val shooterSensor =
    ctreAngularMechanismSensor(
      flywheelMotor,
      ShooterConstants.SHOOTER_SENSOR_CPR,
      ShooterConstants.SHOOTER_SENSOR_GEAR_RATIO
    )

  init {
    flywheelMotor.configFactoryDefault()
    backWheelsMotor.configFactoryDefault()

    flywheelMotor.setInverted(InvertType.None)
    backWheelsMotor.setInverted(InvertType.None)

    flywheelMotor.clearStickyFaults()
    backWheelsMotor.clearStickyFaults()
    flywheelMotor.enableVoltageCompensation(true)
    backWheelsMotor.enableVoltageCompensation(true)
    flywheelMotor.configVoltageCompSaturation(12.0)
    backWheelsMotor.configVoltageCompSaturation(12.0)

    flywheelMotor.configSupplyCurrentLimit(
      SupplyCurrentLimitConfiguration(
        true,
        ShooterConstants.SUPPLY_CURRENT_LIMIT,
        ShooterConstants.SUPPLY_CURRENT_LIMIT,
        1000.0
      )
    )
    backWheelsMotor.configSupplyCurrentLimit(
      SupplyCurrentLimitConfiguration(
        true,
        ShooterConstants.SUPPLY_CURRENT_LIMIT,
        ShooterConstants.SUPPLY_CURRENT_LIMIT,
        1000.0
      )
    )

    flywheelMotor.config_kP(0, ShooterConstants.SHOOTER_KP)
    flywheelMotor.config_kI(0, ShooterConstants.SHOOTER_KI)
    flywheelMotor.config_kD(0, ShooterConstants.SHOOTER_KD)

    backWheelsMotor.config_kP(0, ShooterConstants.SHOOTER_KP)
    backWheelsMotor.config_kI(0, ShooterConstants.SHOOTER_KI)
    backWheelsMotor.config_kD(0, ShooterConstants.SHOOTER_KD)

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
    inputs.statorCurrent[0] = flywheelMotor.statorCurrent.amps
    //    inputs.statorCurrent[1] = followerMotor.statorCurrent.amps
    inputs.supplyCurrent[0] = flywheelMotor.supplyCurrent.amps
    //    inputs.supplyCurrent[1] = followerMotor.supplyCurrent.amps
    inputs.tempCelcius[0] = flywheelMotor.temperature
    //    inputs.tempCelcius[1] = followerMotor.temperature

    //    inputs.appliedVoltage = leaderMotor.motorOutputVoltage.volts
  }

  override fun setVelocity(velocity: AngularVelocity) {
    if (velocity.absoluteValue <= 600.rotations.perMinute) {
      setOpenLoop(0.0)
    } else {
      flywheelMotor.set(
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
    flywheelMotor.set(ControlMode.PercentOutput, power)
    backWheelsMotor.set(ControlMode.PercentOutput, power)
  }

  override fun configurePID(kP: Double, kI: Double, kD: Double) {
    flywheelMotor.config_kP(0, kP)
    flywheelMotor.config_kI(0, kI)
    flywheelMotor.config_kD(0, kD)
  }
}
