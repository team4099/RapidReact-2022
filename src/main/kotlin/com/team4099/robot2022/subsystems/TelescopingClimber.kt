package com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.DemandType
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration
import com.team4099.lib.logging.Logger
import com.team4099.lib.units.base.Length
import com.team4099.lib.units.base.inInches
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.ctreLinearMechanismSensor
import com.team4099.lib.units.inInchesPerSecond
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.inMetersPerSecondPerSecond
import com.team4099.robot2022.config.constants.ClimberConstants.ActualTelescopeStates
import com.team4099.robot2022.config.constants.ClimberConstants.DesiredTelescopeStates
import com.team4099.robot2022.config.constants.ClimberConstants.telescopingTolerance
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.TelescopingClimberConstants
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj.Solenoid
import edu.wpi.first.wpilibj2.command.SubsystemBase

object TelescopingClimber : SubsystemBase() {

  private val telescopingRightArm: TalonFX = TalonFX(Constants.TelescopingClimber.R_ARM_ID)
  private val telescopingLeftArm: TalonFX = TalonFX(Constants.TelescopingClimber.L_ARM_ID)

  val telescopingRightArmSensor =
      ctreLinearMechanismSensor(
          telescopingRightArm,
          TelescopingClimberConstants.SENSOR_CPR,
          TelescopingClimberConstants.GEAR_RATIO,
          TelescopingClimberConstants.PULLEY_MECHANISM)
  val telescopingLeftArmSensor =
      ctreLinearMechanismSensor(
          telescopingLeftArm,
          TelescopingClimberConstants.SENSOR_CPR,
          TelescopingClimberConstants.GEAR_RATIO,
          TelescopingClimberConstants.PULLEY_MECHANISM)

  private val telescopingConfiguration: TalonFXConfiguration = TalonFXConfiguration()

  val currentPosition: Length
    get() {
      if (telescopingLeftArmSensor.position > telescopingRightArmSensor.position) {
        return telescopingLeftArmSensor.position
      } else {
        return telescopingRightArmSensor.position
      }
    }

  var desiredState = DesiredTelescopeStates.START
  val currentState: ActualTelescopeStates
    get() {
      return when (currentPosition) {
        in Double.NEGATIVE_INFINITY.meters..DesiredTelescopeStates.START.position -
                telescopingTolerance -> ActualTelescopeStates.BELOW_START
        in DesiredTelescopeStates.START.position -
            telescopingTolerance..DesiredTelescopeStates.START.position + telescopingTolerance ->
            ActualTelescopeStates.START
        in DesiredTelescopeStates.START.position +
            telescopingTolerance..DesiredTelescopeStates.MAX_RETRACT.position -
                telescopingTolerance -> ActualTelescopeStates.BETWEEN_START_AND_MAX_RETRACT
        in DesiredTelescopeStates.MAX_RETRACT.position -
            telescopingTolerance..DesiredTelescopeStates.MAX_RETRACT.position +
                telescopingTolerance -> ActualTelescopeStates.MAX_RETRACT
        in DesiredTelescopeStates.MAX_RETRACT.position +
            telescopingTolerance..DesiredTelescopeStates.RELEASE_HOOK.position -
                telescopingTolerance -> ActualTelescopeStates.BETWEEN_MAX_RETRACT_AND_RELEASE_HOOK
        in DesiredTelescopeStates.RELEASE_HOOK.position -
            telescopingTolerance..DesiredTelescopeStates.RELEASE_HOOK.position +
                telescopingTolerance -> ActualTelescopeStates.RELEASE_HOOK
        in DesiredTelescopeStates.RELEASE_HOOK.position +
            telescopingTolerance..DesiredTelescopeStates.MAX_EXTENSION.position -
                telescopingTolerance -> ActualTelescopeStates.BETWEEN_RELEASE_HOOK_AND_MAX_EXTENSION
        in DesiredTelescopeStates.MAX_EXTENSION.position -
            telescopingTolerance..DesiredTelescopeStates.MAX_EXTENSION.position +
                telescopingTolerance -> ActualTelescopeStates.MAX_EXTENSION
        in DesiredTelescopeStates.MAX_EXTENSION.position +
            telescopingTolerance..Double.POSITIVE_INFINITY.meters ->
            ActualTelescopeStates.ABOVE_MAX_EXTENSION
        else -> {
          ActualTelescopeStates.START
        }
      }
    }

  var constraints: TrapezoidProfile.Constraints =
      TrapezoidProfile.Constraints(
          TelescopingClimberConstants.MAX_VELOCITY.inMetersPerSecond,
          TelescopingClimberConstants.MAX_ACCELERATION.inMetersPerSecondPerSecond)
  var leftSetpoint: TrapezoidProfile.State =
      TrapezoidProfile.State(
          telescopingLeftArmSensor.position.inMeters,
          telescopingLeftArmSensor.velocity.inMetersPerSecond)
  var rightSetpoint: TrapezoidProfile.State =
      TrapezoidProfile.State(
          telescopingRightArmSensor.position.inMeters,
          telescopingRightArmSensor.velocity.inMetersPerSecond)

  // PneumaticsModuleType new?
  private val pneumaticBrake = // TODO delete pneumatic brakes
      Solenoid(PneumaticsModuleType.CTREPCM, Constants.TelescopingClimber.SOLENOID_ID)
  var isLocked: Boolean = true
    set(value) {
      pneumaticBrake.set(!value)
      field = value
    }
  var brakeApplied = true
    set(value) {
      field = value
      pneumaticBrake.set(!value)
    }
  init {
    Logger.addSource(TelescopingClimberConstants.TAB, "Right Arm Motor Power") {
      telescopingRightArm.motorOutputPercent
    }
    Logger.addSource(TelescopingClimberConstants.TAB, "Right Arm Output Current") {
      telescopingRightArm.supplyCurrent
    }
    Logger.addSource(TelescopingClimberConstants.TAB, "Right Arm Motor Applied Voltage") {
      telescopingRightArm.busVoltage
    }
    Logger.addSource(TelescopingClimberConstants.TAB, "Right Arm Motor Velocity") {
      telescopingRightArmSensor.velocity.inInchesPerSecond
    }
    Logger.addSource(TelescopingClimberConstants.TAB, "Right Arm Current Position") {
      telescopingRightArmSensor.position.inInches
    }

    Logger.addSource(TelescopingClimberConstants.TAB, "Left Arm Motor Power") {
      telescopingLeftArm.motorOutputPercent
    }
    Logger.addSource(TelescopingClimberConstants.TAB, "Left Arm Output Current") {
      telescopingLeftArm.supplyCurrent
    }
    Logger.addSource(TelescopingClimberConstants.TAB, "Left Arm Motor Applied Voltage") {
      telescopingLeftArm.busVoltage
    }
    Logger.addSource(TelescopingClimberConstants.TAB, "Left Arm Motor Velocity") {
      telescopingLeftArmSensor.velocity.inInchesPerSecond
    }
    Logger.addSource(TelescopingClimberConstants.TAB, "Left Arm Current Position") {
      telescopingLeftArmSensor.position.inInches
    }

    Logger.addSource(TelescopingClimberConstants.TAB, "Right Pneumatics State") {
      brakeApplied.toString()
    }
    Logger.addSource(TelescopingClimberConstants.TAB, "Left Pneumatics State") {
      brakeApplied.toString()
    }

    telescopingConfiguration.slot0.kP = TelescopingClimberConstants.KP
    telescopingConfiguration.slot0.kI = TelescopingClimberConstants.KI
    telescopingConfiguration.slot0.kD = TelescopingClimberConstants.KD
    telescopingConfiguration.slot0.kF = TelescopingClimberConstants.KFF

    telescopingRightArm.configFactoryDefault()
    telescopingRightArm.configAllSettings(telescopingConfiguration)
    telescopingRightArm.setNeutralMode(NeutralMode.Brake)
    telescopingRightArm.enableVoltageCompensation(true)

    telescopingLeftArm.configFactoryDefault()
    telescopingLeftArm.configAllSettings(telescopingConfiguration)
    telescopingLeftArm.setNeutralMode(NeutralMode.Brake)
    telescopingLeftArm.enableVoltageCompensation(true)
  }
  fun setOpenLoop(leftPower: Double, rightPower: Double) {
    telescopingLeftArm.set(ControlMode.PercentOutput, leftPower)
    telescopingRightArm.set(ControlMode.PercentOutput, rightPower)
  }
  fun setPosition(
    leftProfile: TrapezoidProfile,
    rightProfile: TrapezoidProfile,
    isUnderLoad: Boolean
  ) {
    leftSetpoint = leftProfile.calculate(0.0)
    rightSetpoint = rightProfile.calculate(0.0)

    if (!isUnderLoad) {
      telescopingLeftArm.set(
          ControlMode.Position,
          leftSetpoint.position,
          DemandType.ArbitraryFeedForward,
          (TelescopingClimberConstants.NO_LOAD_KS +
              leftSetpoint.velocity * TelescopingClimberConstants.NO_LOAD_KV))

      telescopingRightArm.set(
          ControlMode.Position,
          rightSetpoint.position,
          DemandType.ArbitraryFeedForward,
          (TelescopingClimberConstants.NO_LOAD_KS +
              rightSetpoint.velocity * TelescopingClimberConstants.NO_LOAD_KV))
    } else {
      telescopingLeftArm.set(
          ControlMode.Position,
          leftSetpoint.position,
          DemandType.ArbitraryFeedForward,
          (TelescopingClimberConstants.LOAD_KS +
              leftSetpoint.velocity * TelescopingClimberConstants.LOAD_KV))

      telescopingRightArm.set(
          ControlMode.Position,
          rightSetpoint.position,
          DemandType.ArbitraryFeedForward,
          (TelescopingClimberConstants.LOAD_KS +
              rightSetpoint.velocity * TelescopingClimberConstants.LOAD_KV))
    }
  }
}
