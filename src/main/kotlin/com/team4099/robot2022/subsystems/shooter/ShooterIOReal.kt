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
  private val flywheelMotor = TalonFX(Constants.Shooter.FLYWHEEL_MOTOR_ID, CANIVORE_NAME)
  private val backwheelsMotor = TalonFX(Constants.Shooter.BACK_WHEELS_MOTOR_ID, CANIVORE_NAME)

  private val flywheelShooterSensor =
    ctreAngularMechanismSensor(
      flywheelMotor,
      ShooterConstants.SHOOTER_SENSOR_CPR,
      ShooterConstants.SHOOTER_SENSOR_GEAR_RATIO
    )

  private val backwheelsShooterSensor =
    ctreAngularMechanismSensor(
      backwheelsMotor,
      ShooterConstants.SHOOTER_SENSOR_CPR,
      ShooterConstants.SHOOTER_SENSOR_GEAR_RATIO
    )

  init {
    flywheelMotor.configFactoryDefault()
    backwheelsMotor.configFactoryDefault()

    flywheelMotor.setInverted(InvertType.None)
    backwheelsMotor.setInverted(InvertType.None)

    flywheelMotor.clearStickyFaults()
    backwheelsMotor.clearStickyFaults()
    flywheelMotor.enableVoltageCompensation(true)
    backwheelsMotor.enableVoltageCompensation(true)
    flywheelMotor.configVoltageCompSaturation(12.0)
    backwheelsMotor.configVoltageCompSaturation(12.0)

    flywheelMotor.configSupplyCurrentLimit(
      SupplyCurrentLimitConfiguration(
        true,
        ShooterConstants.SUPPLY_CURRENT_LIMIT,
        ShooterConstants.SUPPLY_CURRENT_LIMIT,
        1000.0
      )
    )
    backwheelsMotor.configSupplyCurrentLimit(
      SupplyCurrentLimitConfiguration(
        true,
        ShooterConstants.SUPPLY_CURRENT_LIMIT,
        ShooterConstants.SUPPLY_CURRENT_LIMIT,
        1000.0
      )
    )

    flywheelMotor.config_kP(0, ShooterConstants.SHOOTER_FLYWHEEL_KP)
    flywheelMotor.config_kI(0, ShooterConstants.SHOOTER_FLYWHEEL_KI)
    flywheelMotor.config_kD(0, ShooterConstants.SHOOTER_FLYWHEEL_KD)

    backwheelsMotor.config_kP(0, ShooterConstants.SHOOTER_BACKWHEELS_KP)
    backwheelsMotor.config_kI(0, ShooterConstants.SHOOTER_BACKWHEELS_KI)
    backwheelsMotor.config_kD(0, ShooterConstants.SHOOTER_BACKWHEELS_KD)

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
    inputs.flywheelPosition = flywheelShooterSensor.position
    inputs.backwheelsPosition = backwheelsShooterSensor.position

    inputs.flywheelVelocity = flywheelShooterSensor.velocity
    inputs.backwheelsVelocity = backwheelsShooterSensor.velocity

    inputs.flywheelAppliedVoltage = flywheelMotor.motorOutputVoltage.volts
    inputs.backwheelsAppliedVoltage = backwheelsMotor.motorOutputVoltage.volts

    inputs.flywheelStatorCurrent = flywheelMotor.statorCurrent.amps
    inputs.backwheelsStatorCurrent = backwheelsMotor.statorCurrent.amps

    inputs.flywheelSupplyCurrent = flywheelMotor.supplyCurrent.amps
    inputs.backwheelsSupplyCurrent = backwheelsMotor.supplyCurrent.amps

    inputs.flywheelTempCelcius = flywheelMotor.temperature
    inputs.backwheelsTempCelcius = backwheelsMotor.temperature
  }

  override fun setVelocity(velocity: Pair<AngularVelocity, AngularVelocity>) {
    if (velocity.first.absoluteValue <= 600.rotations.perMinute &&
      velocity.second.absoluteValue <= 600.rotations.perMinute
    ) {
      setFlywheelOpenLoop(0.0)
      setBackwheelsOpenLoop(0.0)
    } else {
      flywheelMotor.set(
        ControlMode.Velocity,
        flywheelShooterSensor.velocityToRawUnits(velocity.first),
        DemandType.ArbitraryFeedForward,
        (
          ShooterConstants.SHOOTER_FLYWHEEL_KS_VOLTS +
            ShooterConstants.SHOOTER_FLYWHEEL_KV_VOLTS_PER_RADIAN_PER_SECOND *
            velocity.first.inRadiansPerSecond
          ) / 12.0
      )
      backwheelsMotor.set(
        ControlMode.Velocity,
        backwheelsShooterSensor.velocityToRawUnits(velocity.second),
        DemandType.ArbitraryFeedForward,
        (
          ShooterConstants.SHOOTER_BACKWHEELS_KS_VOLTS +
            ShooterConstants.SHOOTER_BACKWHEELS_KV_VOLTS_PER_RADIAN_PER_SECOND *
            velocity.second.inRadiansPerSecond
          ) / 12.0
      )
    }
  }

  override fun setFlywheelOpenLoop(power: Double) {
    flywheelMotor.set(ControlMode.PercentOutput, power)
  }

  override fun setBackwheelsOpenLoop(power: Double) {
    backwheelsMotor.set(ControlMode.PercentOutput, power)
  }

  override fun configureFlywheelPID(kP: Double, kI: Double, kD: Double) {
    flywheelMotor.config_kP(0, kP)
    flywheelMotor.config_kI(0, kI)
    flywheelMotor.config_kD(0, kD)
  }

  override fun configureBackwheelsPID(kP: Double, kI: Double, kD: Double) {
    backwheelsMotor.config_kP(0, kP)
    backwheelsMotor.config_kI(0, kI)
    backwheelsMotor.config_kD(0, kD)
  }
}
