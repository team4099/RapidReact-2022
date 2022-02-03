package com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.DemandType
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration
import com.team4099.lib.logging.Logger
import com.team4099.lib.units.base.inInches
import com.team4099.lib.units.ctreLinearMechanismSensor
import com.team4099.lib.units.inInchesPerSecond
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.inMetersPerSecondPerSecond
import com.team4099.robot2022.config.Constants
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj.Solenoid
import edu.wpi.first.wpilibj2.command.SubsystemBase

object TelescopingClimber: SubsystemBase(){

  private val telescopingRightArm: TalonFX = TalonFX(Constants.TelescopingClimber.TELESCOPING_R_ARM_ID)
  private val telescopingLeftArm: TalonFX = TalonFX(Constants.TelescopingClimber.TELESCOPING_L_ARM_ID)

  val telescopingRightArmSensor = ctreLinearMechanismSensor(telescopingRightArm, Constants.TelescopingClimber.TELESCOPING_R_SENSOR_CPR,
    Constants.TelescopingClimber.TELESCOPING_R_GEAR_RATIO, Constants.TelescopingClimber.PULLEY_MECHANISM)
  val telescopingLeftArmSensor = ctreLinearMechanismSensor(
    telescopingLeftArm, Constants.TelescopingClimber.TELESCOPING_L_SENSOR_CPR,
    Constants.TelescopingClimber.TELESCOPING_L_GEAR_RATIO, Constants.TelescopingClimber.PULLEY_MECHANISM)

  val telescopingConfiguration: TalonFXConfiguration = TalonFXConfiguration()

  var constraintsTrapezoidProfile: TrapezoidProfile.Constraints = TrapezoidProfile.Constraints(Constants.TelescopingClimber.MAX_VELOCITY.inMetersPerSecond, Constants.TelescopingClimber.MAX_ACCELERATION.inMetersPerSecondPerSecond)
  var goalTrapezoidProfile: TrapezoidProfile.State = TrapezoidProfile.State(0.0, 0.0)
  var leftSetpoint: TrapezoidProfile.State = TrapezoidProfile.State()
  var rightSetpoint: TrapezoidProfile.State = TrapezoidProfile.State()

  //PneumaticsModuleType new?
  private val pneumaticBrake = Solenoid(
    PneumaticsModuleType.CTREPCM,
    Constants.TelescopingClimber.TELESCOPING_SOLENOID_ID
  )
  var isLocked: Boolean = true
    set(value){
      pneumaticBrake.set(!value)
      field = value
    }
  var brakeApplied = true
    set(value) {
      field = value
      pneumaticBrake.set(!value)
    }
  init {
    //currently based on 2021's
    Logger.addSource(Constants.TelescopingClimber.TAB, "Traversal Right Arm Motor Power") {
      telescopingRightArm.motorOutputPercent
    }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Right Arm Output Current") {
      telescopingRightArm.supplyCurrent
    }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Right Arm Motor Applied Voltage") {
      telescopingRightArm.busVoltage
    }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Right Arm Motor Velocity") {
      telescopingRightArmSensor.velocity.inInchesPerSecond
    }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Right Arm Current Position") {
      telescopingRightArmSensor.position.inInches
    }

    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Left Arm Motor Power") {
      telescopingLeftArm.motorOutputPercent
    }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Left Arm Output Current") {
      telescopingLeftArm.supplyCurrent
    }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Left Arm Motor Applied Voltage") {
      telescopingLeftArm.busVoltage
    } // idk if this correct
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Left Arm Motor Velocity") {
      telescopingLeftArmSensor.velocity.inInchesPerSecond
    }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Left Arm Current Position") {
      telescopingLeftArmSensor.position.inInches
    }

    Logger.addSource(Constants.TelescopingClimber.TAB, "Right Pneumatics State") { brakeApplied.toString() }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Left Pneumatics State") { brakeApplied.toString() }

    telescopingRightArm.configFactoryDefault()
    telescopingConfiguration.slot0.kP = Constants.TelescopingClimber.TELESCOPING_P
    telescopingConfiguration.slot0.kI = Constants.TelescopingClimber.TELESCOPING_I
    telescopingConfiguration.slot0.kD = Constants.TelescopingClimber.TELESCOPING_D
    telescopingConfiguration.slot0.kF = Constants.TelescopingClimber.TELESCOPING_FF
    // climberRArmPIDController.setSmartMotionMaxVelocity(
    //    climberRArmSensor.velocityToRawUnits(Constants.Climber.CLIMBER_SPARKMAX_VEL), 0)
    // climberRArmPIDController.setSmartMotionMaxAccel(
    //    climberRArmSensor.accelerationToRawUnits(Constants.Climber.CLIMBER_SPARKMAX_ACC), 0)
    telescopingRightArm.configAllSettings(telescopingConfiguration)
    telescopingRightArm.setNeutralMode(NeutralMode.Brake)
    telescopingRightArm.enableVoltageCompensation(true)

    telescopingLeftArm.configFactoryDefault()
    telescopingLeftArm.setNeutralMode(NeutralMode.Brake)
    telescopingLeftArm.enableVoltageCompensation(true)

  }
  fun setPosition(profile: TrapezoidProfile) {
    leftSetpoint = profile.calculate(0.02)
    telescopingLeftArm.set(ControlMode.Position, leftSetpoint.position, DemandType.ArbitraryFeedForward,
      // feed forward stuff im too braindead to do
      )
    telescopingRightArm.set(ControlMode.Position, rightSetpoint.position, DemandType.ArbitraryFeedForward, //
  //
    )

    //  climberRArmPIDController.setReference(
    //      climberRArmSensor.positionToRawUnits(position.length), ControlType.kSmartMotion)
    //  climberLArmPIDController.setReference(
    //      climberLArmSensor.positionToRawUnits(position.length), ControlType.kSmartMotion)
  }

  fun setOpenLoopPower(leftPower: Double, rightPower: Double, safetyEnabled: Boolean = true) {
    Logger.addEvent("Climber", "Left power: $leftPower Right power: $rightPower")
    if (safetyEnabled &&
      ((climberLArmSensor.position < Constants.TelescopingClimber.TELESCOPING_BOTTOM_SAFETY_THRESHOLD &&
        leftPower < 0.0) ||
        (climberLArmSensor.position > Constants.TelescopingClimber.TELESCOPING_TOP_SAFETY_THRESHOLD &&
          leftPower > 0.0))) {
      telescopingLeftArm.set(0.0)
    } else {
      telescopingLeftArm.set(leftPower)
    }
    if (safetyEnabled &&
      ((climberRArmSensor.position < Constants.TelescopingClimber.TELESCOPING_BOTTOM_SAFETY_THRESHOLD &&
        rightPower < 0.0) ||
        (climberRArmSensor.position > Constants.TelescopingClimber.TELESCOPING_TOP_SAFETY_THRESHOLD &&
          rightPower > 0.0))) {
      telescopingRightArm.set(0.0)
    } else {
      telescopingRightArm.set(rightPower)
    }
  }

  fun zeroLeftEncoder() {
    telescopingLeftArm.encoder.position = 0.0
  }

  fun zeroRightEncoder() {
    telescopingRightArm.encoder.position = 0.0
  }


}
