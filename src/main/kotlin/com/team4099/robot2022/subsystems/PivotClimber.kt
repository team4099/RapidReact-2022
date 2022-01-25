package com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.Constants
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj.Solenoid
import edu.wpi.first.wpilibj2.command.SubsystemBase

object PivotClimber: SubsystemBase(){

  private val pivotRightArm: TalonFX = TalonFX(Constants.Climber.CLIMBER_TID)
  private val pivotLeftArm: TalonFX = TalonFX(Constants.Climber.CLIMBER_PID)

  //val pivotRightArmSensor =
  //val pivotLeftArmSensor =

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
      pivotRightArm.appliedOutput
    }
    Logger.addSource(Constants.Climber.TAB, "Climber Right Arm Output Current") {
      pivotRightArm.outputCurrent
    }
    Logger.addSource(Constants.Climber.TAB, "Climber Right Arm Motor Applied Voltage") {
      pivotRightArm.busVoltage
    }
    Logger.addSource(Constants.Climber.TAB, "Climber Right Arm Motor Velocity") {
      pivotRightArmSensor.velocity.inInchesPerSecond
    }
    Logger.addSource(Constants.Climber.TAB, "Climber Right Arm Current Position") {
      pivotRightArmSensor.position.inInches
    }

    Logger.addSource(Constants.Climber.TAB, "Climber Left Arm Motor Power") {
      pivotLeftArm.appliedOutput
    }
    Logger.addSource(Constants.Climber.TAB, "Climber Left Arm Output Current") {
      pivotLeftArm.outputCurrent
    }
    Logger.addSource(Constants.Climber.TAB, "Climber Left Arm Motor Applied Voltage") {
      pivotLeftArm.busVoltage
    } // idk if this correct
    Logger.addSource(Constants.Climber.TAB, "Climber Left Arm Motor Velocity") {
      pivotLeftArmSensor.velocity.inInchesPerSecond
    }
    Logger.addSource(Constants.Climber.TAB, "Climber Left Arm Current Position") {
      pivotLeftArmSensor.position.inInches
    }

    Logger.addSource(Constants.Climber.TAB, "Right Pneumatics State") { brakeApplied.toString() }
    Logger.addSource(Constants.Climber.TAB, "Left Pneumatics State") { brakeApplied.toString() }

    pivotRightArm.restoreFactoryDefaults()
    pivotRightArm.inverted = true
    pivotRightArm.idleMode = TalonFX.IdleMode.kBrake //change for talonfx
    pivotRightArm.burnFlash()

    pivotLeftArm.restoreFactoryDefaults()
    pivotLeftArm.inverted = true
    pivotLeftArm.idleMode = TalonFX.IdleMode.kBrake //change for talon fx
    pivotLeftArm.burnFlash()
  }


}
