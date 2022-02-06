package com.team4099.robot2022.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import com.revrobotics.ControlType
import com.revrobotics.SparkMaxPIDController
import com.team4099.lib.logging.Logger
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inDegrees
import com.team4099.lib.units.inDegreesPerSecond
import com.team4099.lib.units.inDegreesPerSecondPerSecond
import com.team4099.lib.units.sparkMaxAngularMechanismSensor
import com.team4099.robot2022.config.Constants
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.SubsystemBase
import kotlin.math.sign

object PivotClimber : SubsystemBase() {
  private val pivotRightArm: CANSparkMax =
      CANSparkMax(Constants.PivotClimber.L_ARM_ID, CANSparkMaxLowLevel.MotorType.kBrushless)
  private val pivotLeftArm: CANSparkMax =
      CANSparkMax(Constants.PivotClimber.R_ARM_ID, CANSparkMaxLowLevel.MotorType.kBrushless)

  private val pivotLeftArmSensor =
      sparkMaxAngularMechanismSensor(pivotLeftArm, Constants.PivotClimber.GEAR_RATIO)
  private val pivotRightArmSensor =
      sparkMaxAngularMechanismSensor(pivotRightArm, Constants.PivotClimber.GEAR_RATIO)

  private val rightPID = pivotRightArm.pidController
  private val leftPID = pivotLeftArm.pidController

  var constraints: TrapezoidProfile.Constraints =
      TrapezoidProfile.Constraints(
          Constants.PivotClimber.MAX_VELOCITY.inDegreesPerSecond,
          Constants.PivotClimber.MAX_ACCELERATION.inDegreesPerSecondPerSecond)
  var leftSetpoint: TrapezoidProfile.State =
      TrapezoidProfile.State(
          pivotLeftArmSensor.position.inDegrees, pivotLeftArmSensor.velocity.inDegreesPerSecond)
  var rightSetpoint: TrapezoidProfile.State =
      TrapezoidProfile.State(
          pivotRightArmSensor.position.inDegrees, pivotRightArmSensor.velocity.inDegreesPerSecond)

  init {
    /* RIGHT ARM */
    Logger.addSource(Constants.PivotClimber.TAB, "Traversal Right Arm Motor Power") {
      pivotRightArm.get()
    }
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Right Arm Output Current") {
      pivotRightArm.outputCurrent
    }
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Right Arm Motor Applied Voltage") {
      pivotRightArm.busVoltage
    }
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Right Arm Motor Velocity") {
      pivotRightArmSensor.velocity.inDegreesPerSecond
    }
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Right Arm Current Position") {
      pivotRightArmSensor.position.inDegrees
    }

    /* LEFT ARM */
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Left Arm Motor Power") {
      pivotLeftArm.get()
    }
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Left Arm Output Current") {
      pivotLeftArm.outputCurrent
    }
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Left Arm Motor Applied Voltage") {
      pivotLeftArm.busVoltage
    }
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Left Arm Motor Velocity") {
      pivotLeftArmSensor.velocity.inDegreesPerSecond
    }
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Left Arm Current Position") {
      pivotLeftArmSensor.position.inDegrees
    }

    pivotRightArm.restoreFactoryDefaults()
    rightPID.p = Constants.PivotClimber.KP
    rightPID.i = Constants.PivotClimber.KI
    rightPID.d = Constants.PivotClimber.KD
    rightPID.ff = Constants.PivotClimber.KFF
    rightPID.setSmartMotionMaxVelocity(
        pivotRightArmSensor.velocityToRawUnits(Constants.Drivetrain.DIRECTION_VEL_MAX), 0)
    rightPID.setSmartMotionMaxAccel(
        pivotRightArmSensor.accelerationToRawUnits(Constants.Drivetrain.DIRECTION_ACCEL_MAX), 0)
    rightPID.setOutputRange(-1.0, 1.0)
    rightPID.setSmartMotionMinOutputVelocity(0.0, 0)
    rightPID.setSmartMotionAllowedClosedLoopError(
        pivotRightArmSensor.positionToRawUnits(Constants.Drivetrain.ALLOWED_ANGLE_ERROR), 0)
    pivotRightArm.setSmartCurrentLimit(Constants.PivotClimber.SMART_CURRENT_LIMIT)
    pivotRightArm.burnFlash()

    pivotLeftArm.restoreFactoryDefaults()
    leftPID.p = Constants.PivotClimber.KP
    leftPID.i = Constants.PivotClimber.KI
    leftPID.d = Constants.PivotClimber.KD
    leftPID.ff = Constants.PivotClimber.KFF
    leftPID.setSmartMotionMaxVelocity(
        pivotLeftArmSensor.velocityToRawUnits(Constants.Drivetrain.DIRECTION_VEL_MAX), 0)
    leftPID.setSmartMotionMaxAccel(
        pivotLeftArmSensor.accelerationToRawUnits(Constants.Drivetrain.DIRECTION_ACCEL_MAX), 0)
    leftPID.setOutputRange(-1.0, 1.0)
    leftPID.setSmartMotionMinOutputVelocity(0.0, 0)
    leftPID.setSmartMotionAllowedClosedLoopError(
        pivotLeftArmSensor.positionToRawUnits(Constants.Drivetrain.ALLOWED_ANGLE_ERROR), 0)
    pivotLeftArm.setSmartCurrentLimit(Constants.PivotClimber.SMART_CURRENT_LIMIT)
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
          Constants.PivotClimber.NO_LOAD_KS * sign(leftSetpoint.position) +
              Constants.PivotClimber.NO_LOAD_KV * leftSetpoint.velocity,
          SparkMaxPIDController.ArbFFUnits.kVoltage)
      rightPID.setReference(
          pivotRightArmSensor.positionToRawUnits(rightSetpoint.position.degrees),
          CANSparkMax.ControlType.kPosition,
          0,
          Constants.PivotClimber.NO_LOAD_KS * sign(rightSetpoint.position) +
              Constants.PivotClimber.NO_LOAD_KV * rightSetpoint.velocity,
          SparkMaxPIDController.ArbFFUnits.kVoltage)
    } else {
      leftPID.setReference(
          pivotLeftArmSensor.positionToRawUnits(leftSetpoint.position.degrees),
          CANSparkMax.ControlType.kPosition,
          0,
          Constants.PivotClimber.LOAD_KS * sign(leftSetpoint.position) +
              Constants.PivotClimber.LOAD_KV * leftSetpoint.velocity,
          SparkMaxPIDController.ArbFFUnits.kVoltage)
      rightPID.setReference(
          pivotRightArmSensor.positionToRawUnits(rightSetpoint.position.degrees),
          CANSparkMax.ControlType.kPosition,
          0,
          Constants.PivotClimber.LOAD_KS * sign(rightSetpoint.position) +
              Constants.PivotClimber.LOAD_KV * rightSetpoint.velocity,
          SparkMaxPIDController.ArbFFUnits.kVoltage)
    }
  }
}
