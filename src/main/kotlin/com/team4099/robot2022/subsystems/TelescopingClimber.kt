package com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.Constants
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj.Solenoid
import edu.wpi.first.wpilibj2.command.SubsystemBase

object TelescopingClimber: SubsystemBase(){

  private val telescopingRightArm: TalonFX = TalonFX(Constants.Climber.CLIMBER_TID)
  private val telescopingLeftArm: TalonFX = TalonFX(Constants.Climber.CLIMBER_PID)


  //val traversalRightArmSensor =
  //val traversalLeftArmSensor =

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
      telescopingRightArm.appliedOutput
    }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Right Arm Output Current") {
      telescopingRightArm.outputCurrent
    }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Right Arm Motor Applied Voltage") {
      telescopingRightArm.busVoltage
    }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Right Arm Motor Velocity") {
      traversalRightArmSensor.velocity.inInchesPerSecond
    }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Right Arm Current Position") {
      traversalRightArmSensor.position.inInches
    }

    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Left Arm Motor Power") {
      telescopingLeftArm.appliedOutput
    }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Left Arm Output Current") {
      telescopingLeftArm.outputCurrent
    }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Left Arm Motor Applied Voltage") {
      telescopingLeftArm.busVoltage
    } // idk if this correct
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Left Arm Motor Velocity") {
      traversalLeftArmSensor.velocity.inInchesPerSecond
    }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Climber Left Arm Current Position") {
      traversalLeftArmSensor.position.inInches
    }

    Logger.addSource(Constants.TelescopingClimber.TAB, "Right Pneumatics State") { brakeApplied.toString() }
    Logger.addSource(Constants.TelescopingClimber.TAB, "Left Pneumatics State") { brakeApplied.toString() }

    telescopingRightArm.restoreFactoryDefaults()
    telescopingRightArm.inverted = true
    // climberRArmPIDController.p = Constants.Climber.CLIMBER_P
    // climberRArmPIDController.i = Constants.Climber.CLIMBER_I
    // climberRArmPIDController.d = Constants.Climber.CLIMBER_D
    // climberRArmPIDController.setSmartMotionMaxVelocity(
    //    climberRArmSensor.velocityToRawUnits(Constants.Climber.CLIMBER_SPARKMAX_VEL), 0)
    // climberRArmPIDController.setSmartMotionMaxAccel(
    //    climberRArmSensor.accelerationToRawUnits(Constants.Climber.CLIMBER_SPARKMAX_ACC), 0)
    telescopingRightArm.idleMode = TalonFX.IdleMode.kBrake //change for talonfx
    telescopingRightArm.burnFlash()

    telescopingLeftArm.restoreFactoryDefaults()
    telescopingLeftArm.inverted = true
    telescopingLeftArm.idleMode = TalonFX.IdleMode.kBrake //change for talon fx
    telescopingLeftArm.burnFlash()
  }
  fun setPosition(position: Constants.TelescopingClimber.TELESCOPING_POSITION) {
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
