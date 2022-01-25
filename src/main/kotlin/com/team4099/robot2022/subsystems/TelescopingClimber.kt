package com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.Constants
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj.Solenoid
import edu.wpi.first.wpilibj2.command.SubsystemBase

object TelescopingClimber: SubsystemBase(){

  private val traversalRightArm: TalonFX = TalonFX(Constants.Climber.CLIMBER_TID)
  private val traversalLeftArm: TalonFX = TalonFX(Constants.Climber.CLIMBER_PID)

  //val traversalRightArmSensor =
  //val traversalLeftArmSensor =

  //PneumaticsModuleType new?
  private val pneumaticBrake = Solenoid(
    PneumaticsModuleType.CTREPCM,
    Constants.Climber.SOLENOID_ID
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
    Logger.addSource(Constants.Climber.TAB, "Traversal Right Arm Motor Power") {
      traversalRightArm.appliedOutput
    }
    Logger.addSource(Constants.Climber.TAB, "Climber Right Arm Output Current") {
      traversalRightArm.outputCurrent
    }
    Logger.addSource(Constants.Climber.TAB, "Climber Right Arm Motor Applied Voltage") {
      traversalRightArm.busVoltage
    }
    Logger.addSource(Constants.Climber.TAB, "Climber Right Arm Motor Velocity") {
      traversalRightArmSensor.velocity.inInchesPerSecond
    }
    Logger.addSource(Constants.Climber.TAB, "Climber Right Arm Current Position") {
      traversalRightArmSensor.position.inInches
    }

    Logger.addSource(Constants.Climber.TAB, "Climber Left Arm Motor Power") {
      traversalLeftArm.appliedOutput
    }
    Logger.addSource(Constants.Climber.TAB, "Climber Left Arm Output Current") {
      traversalLeftArm.outputCurrent
    }
    Logger.addSource(Constants.Climber.TAB, "Climber Left Arm Motor Applied Voltage") {
      traversalLeftArm.busVoltage
    } // idk if this correct
    Logger.addSource(Constants.Climber.TAB, "Climber Left Arm Motor Velocity") {
      traversalLeftArmSensor.velocity.inInchesPerSecond
    }
    Logger.addSource(Constants.Climber.TAB, "Climber Left Arm Current Position") {
      traversalLeftArmSensor.position.inInches
    }

    Logger.addSource(Constants.Climber.TAB, "Right Pneumatics State") { brakeApplied.toString() }
    Logger.addSource(Constants.Climber.TAB, "Left Pneumatics State") { brakeApplied.toString() }

    traversalRightArm.restoreFactoryDefaults()
    traversalRightArm.inverted = true
    // climberRArmPIDController.p = Constants.Climber.CLIMBER_P
    // climberRArmPIDController.i = Constants.Climber.CLIMBER_I
    // climberRArmPIDController.d = Constants.Climber.CLIMBER_D
    // climberRArmPIDController.setSmartMotionMaxVelocity(
    //    climberRArmSensor.velocityToRawUnits(Constants.Climber.CLIMBER_SPARKMAX_VEL), 0)
    // climberRArmPIDController.setSmartMotionMaxAccel(
    //    climberRArmSensor.accelerationToRawUnits(Constants.Climber.CLIMBER_SPARKMAX_ACC), 0)
    traversalRightArm.idleMode = TalonFX.IdleMode.kBrake //change for talonfx
    traversalRightArm.burnFlash()

    traversalLeftArm.restoreFactoryDefaults()
    traversalLeftArm.inverted = true
    traversalLeftArm.idleMode = TalonFX.IdleMode.kBrake //change for talon fx
    traversalLeftArm.burnFlash()
  }
  fun setPosition(position: Constants.ClimberPosition) {
    //  climberRArmPIDController.setReference(
    //      climberRArmSensor.positionToRawUnits(position.length), ControlType.kSmartMotion)
    //  climberLArmPIDController.setReference(
    //      climberLArmSensor.positionToRawUnits(position.length), ControlType.kSmartMotion)
  }

  fun setOpenLoopPower(leftPower: Double, rightPower: Double, safetyEnabled: Boolean = true) {
    Logger.addEvent("Climber", "Left power: $leftPower Right power: $rightPower")
    if (safetyEnabled &&
      ((climberLArmSensor.position < Constants.Climber.BOTTOM_SAFETY_THRESHOLD &&
        leftPower < 0.0) ||
        (climberLArmSensor.position > Constants.Climber.TOP_SAFETY_THRESHOLD &&
          leftPower > 0.0))) {
      climberLArm.set(0.0)
    } else {
      climberLArm.set(leftPower)
    }
    if (safetyEnabled &&
      ((climberRArmSensor.position < Constants.Climber.BOTTOM_SAFETY_THRESHOLD &&
        rightPower < 0.0) ||
        (climberRArmSensor.position > Constants.Climber.TOP_SAFETY_THRESHOLD &&
          rightPower > 0.0))) {
      climberRArm.set(0.0)
    } else {
      climberRArm.set(rightPower)
    }
  }

  fun zeroLeftEncoder() {
    climberLArm.encoder.position = 0.0
  }

  fun zeroRightEncoder() {
    climberRArm.encoder.position = 0.0
  }


}
