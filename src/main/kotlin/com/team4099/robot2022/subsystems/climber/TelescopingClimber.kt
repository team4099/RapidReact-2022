package com.team4099.robot2022.subsystems.climber

import com.team4099.lib.units.base.Length
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.derived.volts
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.inMetersPerSecondPerSecond
import com.team4099.robot2022.config.constants.ClimberConstants.ActualTelescopeStates
import com.team4099.robot2022.config.constants.ClimberConstants.DesiredTelescopeStates
import com.team4099.robot2022.config.constants.ClimberConstants.telescopingTolerance
import com.team4099.robot2022.config.constants.TelescopingClimberConstants
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class TelescopingClimber(val io: TelescopingClimberIO) : SubsystemBase() {
  val inputs = TelescopingClimberIO.TelescopingClimberIOInputs()

  init {}

  override fun periodic() {
    io.updateInputs(inputs)

    Logger.getInstance().processInputs("TelescopingClimber", inputs)
    Logger.getInstance().recordOutput("TelescopingClimber/desiredState", desiredState.name)
    Logger.getInstance().recordOutput("TelescopingClimber/currentState", currentState.name)
    Logger.getInstance()
        .recordOutput("TelescopingClimber/leftPositionSetpointMeters", leftSetpoint.position)
    Logger.getInstance()
        .recordOutput("TelescopingClimber/leftVelocitySetpointMetersPerSec", leftSetpoint.velocity)
    Logger.getInstance()
        .recordOutput("TelescopingClimber/rightPositionSetpointMeters", rightSetpoint.position)
    Logger.getInstance()
        .recordOutput(
            "TelescopingClimber/rightVelocitySetpointMetersPerSec", rightSetpoint.velocity)

    Logger.getInstance()
        .recordOutput("TelescopingClimber/leftForwardLimitReached", leftForwardLimitReached)
    Logger.getInstance()
        .recordOutput("TelescopingClimber/leftReverseLimitReached", leftReverseLimitReached)
    Logger.getInstance()
        .recordOutput("TelescopingClimber/rightForwardLimitReached", rightForwardLimitReached)
    Logger.getInstance()
        .recordOutput("TelescopingClimber/rightReverseLimitReached", rightReverseLimitReached)
  }

  val leftForwardLimitReached: Boolean
    get() = inputs.leftPosition > TelescopingClimberConstants.FORWARD_SOFT_LIMIT
  val leftReverseLimitReached: Boolean
    get() = inputs.leftPosition < TelescopingClimberConstants.REVERSE_SOFT_LIMIT
  val leftForwardThresholdLimitReached: Boolean
    get() = inputs.leftPosition > TelescopingClimberConstants.FORWARD_SOFT_LIMIT - TelescopingClimberConstants.SLOW_TELESCOPING_THRESHOLD

  val rightForwardLimitReached: Boolean
    get() = inputs.rightPosition > TelescopingClimberConstants.FORWARD_SOFT_LIMIT
  val rightReverseLimitReached: Boolean
    get() = inputs.rightPosition < TelescopingClimberConstants.REVERSE_SOFT_LIMIT
  val rightForwardThresholdLimitReached: Boolean
    get() = inputs.rightPosition > TelescopingClimberConstants.FORWARD_SOFT_LIMIT - TelescopingClimberConstants.SLOW_TELESCOPING_THRESHOLD

  fun setOpenLoop(leftPower: Double, rightPower: Double, useSoftLimits: Boolean = true) {
    if (useSoftLimits &&
        ((leftForwardLimitReached && leftPower > 0.0) ||
            (leftReverseLimitReached && leftPower < 0.0))) {
      io.setLeftOpenLoop(0.0)
    } else {
        io.setLeftOpenLoop(leftPower)
    }

    if (useSoftLimits &&
        ((rightForwardLimitReached && rightPower > 0.0) ||
            (rightReverseLimitReached && rightPower < 0.0))) {
      io.setRightOpenLoop(0.0)
    } else {
      io.setRightOpenLoop(rightPower)
    }
  }

  val currentPosition: Length
    get() {
      if (inputs.leftPosition > inputs.rightPosition) {
        return inputs.leftPosition
      } else {
        return inputs.rightPosition
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
      TrapezoidProfile.State(inputs.leftPosition.inMeters, inputs.leftVelocity.inMetersPerSecond)
  var rightSetpoint: TrapezoidProfile.State =
      TrapezoidProfile.State(inputs.rightPosition.inMeters, inputs.rightVelocity.inMetersPerSecond)

  fun setPosition(
    leftProfile: TrapezoidProfile,
    rightProfile: TrapezoidProfile,
    isUnderLoad: Boolean
  ) {
    leftSetpoint = leftProfile.calculate(0.0)
    rightSetpoint = rightProfile.calculate(0.0)

    if (!isUnderLoad) {
      io.setLeftPosition(
          leftSetpoint.position.meters,
          (TelescopingClimberConstants.NO_LOAD_KS +
                  leftSetpoint.velocity * TelescopingClimberConstants.NO_LOAD_KV).volts)

      io.setRightPosition(
          rightSetpoint.position.meters,
          (TelescopingClimberConstants.NO_LOAD_KS +
                  rightSetpoint.velocity * TelescopingClimberConstants.NO_LOAD_KV).volts)
    } else {
      io.setLeftPosition(
          leftSetpoint.position.meters,
          (TelescopingClimberConstants.LOAD_KS +
                  leftSetpoint.velocity * TelescopingClimberConstants.LOAD_KV).volts)

      io.setRightPosition(
          rightSetpoint.position.meters,
          (TelescopingClimberConstants.LOAD_KS +
                  rightSetpoint.velocity * TelescopingClimberConstants.LOAD_KV).volts)
    }
  }
  fun zeroLeftEncoder() {
    io.zeroLeftEncoder()
  }

  fun zeroRightEncoder() {
    io.zeroRightEncoder()
  }
}
