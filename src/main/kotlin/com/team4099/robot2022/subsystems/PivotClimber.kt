package com.team4099.robot2022.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import com.revrobotics.SparkMaxPIDController
import com.team4099.lib.logging.Logger
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inDegrees
import com.team4099.lib.units.inDegreesPerSecond
import com.team4099.lib.units.inDegreesPerSecondPerSecond
import com.team4099.lib.units.sparkMaxAngularMechanismSensor
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.PivotClimberConstants
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.SubsystemBase
import kotlin.math.sign

object PivotClimber : SubsystemBase() {
  private val pivotRightArm: CANSparkMax =
      CANSparkMax(Constants.PivotClimber.L_ARM_ID, CANSparkMaxLowLevel.MotorType.kBrushless)
  private val pivotLeftArm: CANSparkMax =
      CANSparkMax(Constants.PivotClimber.R_ARM_ID, CANSparkMaxLowLevel.MotorType.kBrushless)

  private val pivotLeftArmSensor =
      sparkMaxAngularMechanismSensor(pivotLeftArm, PivotClimberConstants.GEAR_RATIO)
  private val pivotRightArmSensor =
      sparkMaxAngularMechanismSensor(pivotRightArm, PivotClimberConstants.GEAR_RATIO)

  private val rightPID = pivotRightArm.pidController
  private val leftPID = pivotLeftArm.pidController

  var constraints: TrapezoidProfile.Constraints =
      TrapezoidProfile.Constraints(
          PivotClimberConstants.MAX_VELOCITY.inDegreesPerSecond,
          PivotClimberConstants.MAX_ACCELERATION.inDegreesPerSecondPerSecond)
  var leftSetpoint: TrapezoidProfile.State =
      TrapezoidProfile.State(
          pivotLeftArmSensor.position.inDegrees, pivotLeftArmSensor.velocity.inDegreesPerSecond)
  var rightSetpoint: TrapezoidProfile.State =
      TrapezoidProfile.State(
          pivotRightArmSensor.position.inDegrees, pivotRightArmSensor.velocity.inDegreesPerSecond)

  init {
    /* RIGHT ARM */
    Logger.addSource(PivotClimberConstants.TAB, "Right Arm Motor Power") { pivotRightArm.get() }
    Logger.addSource(PivotClimberConstants.TAB, "Right Arm Output Current") {
      pivotRightArm.outputCurrent
    }
    Logger.addSource(PivotClimberConstants.TAB, "Right Arm Motor Applied Voltage") {
      pivotRightArm.busVoltage
    }
    Logger.addSource(PivotClimberConstants.TAB, "Right Arm Motor Velocity") {
      pivotRightArmSensor.velocity.inDegreesPerSecond
    }
    Logger.addSource(PivotClimberConstants.TAB, "Right Arm Current Position") {
      pivotRightArmSensor.position.inDegrees
    }

    /* LEFT ARM */
    Logger.addSource(PivotClimberConstants.TAB, "Left Arm Motor Power") { pivotLeftArm.get() }
    Logger.addSource(PivotClimberConstants.TAB, "Left Arm Output Current") {
      pivotLeftArm.outputCurrent
    }
    Logger.addSource(PivotClimberConstants.TAB, "Left Arm Motor Applied Voltage") {
      pivotLeftArm.busVoltage
    }
    Logger.addSource(PivotClimberConstants.TAB, "Left Arm Motor Velocity") {
      pivotLeftArmSensor.velocity.inDegreesPerSecond
    }
    Logger.addSource(PivotClimberConstants.TAB, "Left Arm Current Position") {
      pivotLeftArmSensor.position.inDegrees
    }

    pivotRightArm.restoreFactoryDefaults()
    rightPID.p = PivotClimberConstants.KP
    rightPID.i = PivotClimberConstants.KI
    rightPID.d = PivotClimberConstants.KD
    rightPID.ff = PivotClimberConstants.KFF
    rightPID.setSmartMotionMaxVelocity(
        pivotRightArmSensor.velocityToRawUnits(PivotClimberConstants.MAX_VELOCITY), 0)
    rightPID.setSmartMotionMaxAccel(
        pivotRightArmSensor.accelerationToRawUnits(PivotClimberConstants.MAX_ACCELERATION), 0)
    rightPID.setOutputRange(-1.0, 1.0)
    rightPID.setSmartMotionMinOutputVelocity(0.0, 0)
    rightPID.setSmartMotionAllowedClosedLoopError(
        pivotRightArmSensor.positionToRawUnits(PivotClimberConstants.ALLOWED_ANGLE_ERROR), 0)
    pivotRightArm.setSmartCurrentLimit(PivotClimberConstants.SMART_CURRENT_LIMIT)
    pivotRightArm.burnFlash()

    pivotLeftArm.restoreFactoryDefaults()
    leftPID.p = PivotClimberConstants.KP
    leftPID.i = PivotClimberConstants.KI
    leftPID.d = PivotClimberConstants.KD
    leftPID.ff = PivotClimberConstants.KFF
    leftPID.setSmartMotionMaxVelocity(
        pivotLeftArmSensor.velocityToRawUnits(PivotClimberConstants.MAX_VELOCITY), 0)
    leftPID.setSmartMotionMaxAccel(
        pivotLeftArmSensor.accelerationToRawUnits(PivotClimberConstants.MAX_ACCELERATION), 0)
    leftPID.setOutputRange(-1.0, 1.0)
    leftPID.setSmartMotionMinOutputVelocity(0.0, 0)
    leftPID.setSmartMotionAllowedClosedLoopError(
        pivotLeftArmSensor.positionToRawUnits(PivotClimberConstants.ALLOWED_ANGLE_ERROR), 0)
    pivotLeftArm.setSmartCurrentLimit(PivotClimberConstants.SMART_CURRENT_LIMIT)
    pivotLeftArm.burnFlash()
  }

  fun setAngle(
    leftProfile: TrapezoidProfile,
    rightProfile: TrapezoidProfile,
    isUnderLoad: Boolean
  ) {
    leftSetpoint = leftProfile.calculate(0.02)
    rightSetpoint = rightProfile.calculate(0.02)

    if (!isUnderLoad) {
      leftPID.setReference(
          pivotLeftArmSensor.positionToRawUnits(leftSetpoint.position.degrees),
          CANSparkMax.ControlType.kPosition,
          0,
          PivotClimberConstants.NO_LOAD_KS * sign(leftSetpoint.position) +
              PivotClimberConstants.NO_LOAD_KV * leftSetpoint.velocity,
          SparkMaxPIDController.ArbFFUnits.kVoltage)
      rightPID.setReference(
          pivotRightArmSensor.positionToRawUnits(rightSetpoint.position.degrees),
          CANSparkMax.ControlType.kPosition,
          0,
          PivotClimberConstants.NO_LOAD_KS * sign(rightSetpoint.position) +
              PivotClimberConstants.NO_LOAD_KV * rightSetpoint.velocity,
          SparkMaxPIDController.ArbFFUnits.kVoltage)
    } else {
      leftPID.setReference(
          pivotLeftArmSensor.positionToRawUnits(leftSetpoint.position.degrees),
          CANSparkMax.ControlType.kPosition,
          0,
          PivotClimberConstants.LOAD_KS * sign(leftSetpoint.position) +
              PivotClimberConstants.LOAD_KV * leftSetpoint.velocity,
          SparkMaxPIDController.ArbFFUnits.kVoltage)
      rightPID.setReference(
          pivotRightArmSensor.positionToRawUnits(rightSetpoint.position.degrees),
          CANSparkMax.ControlType.kPosition,
          0,
          PivotClimberConstants.LOAD_KS * sign(rightSetpoint.position) +
              PivotClimberConstants.LOAD_KV * rightSetpoint.velocity,
          SparkMaxPIDController.ArbFFUnits.kVoltage)
    }
  }
}
