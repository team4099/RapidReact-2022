package com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.Constants
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj.Solenoid
import edu.wpi.first.wpilibj2.command.SubsystemBase

object PivotClimber: SubsystemBase(){

  // temp once motors are figured out
  private val pivotRightArm: CANSparkMax = CANSparkMax(Constants.PivotClimber.PIVOT_L_ARM_ID, CANSparkMaxLowLevel.MotorType.kBrushless)
  private val pivotLeftArm: CANSparkMax = CANSparkMax(Constants.PivotClimber.PIVOT_R_ARM_ID, CANSparkMaxLowLevel.MotorType.kBrushless)

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
    /* RIGHT ARM */
    Logger.addSource(Constants.PivotClimber.TAB, "Traversal Right Arm Motor Power") {
      pivotRightArm.motorOutputPercent
    }
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Right Arm Output Current") {
      pivotRightArm.supplyCurrent
    }
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Right Arm Motor Applied Voltage") {
      pivotRightArm.busVoltage
    }
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Right Arm Motor Velocity") {
      pivotRightArmSensor.velocity.inInchesPerSecond
    }
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Right Arm Current Position") {
      pivotRightArmSensor.position.inInches
    }

    /* LEFT ARM */
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Left Arm Motor Power") {
      pivotLeftArm.motorOutputPercent
    }
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Left Arm Output Current") {
      pivotLeftArm.supplyCurrent
    }
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Left Arm Motor Applied Voltage") {
      pivotLeftArm.busVoltage
    } // idk if this correct
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Left Arm Motor Velocity") {
      pivotLeftArmSensor.velocity.inInchesPerSecond
    }
    Logger.addSource(Constants.PivotClimber.TAB, "Climber Left Arm Current Position") {
      pivotLeftArmSensor.position.inInches
    }

    Logger.addSource(Constants.PivotClimber.TAB, "Right Pneumatics State") { brakeApplied.toString() }
    Logger.addSource(Constants.PivotClimber.TAB, "Left Pneumatics State") { brakeApplied.toString() }

    pivotRightArm.configFactoryDefault()
    pivotRightArm.inverted = true
    pivotRightArm.setNeutralMode(NeutralMode.Brake)
    pivotRightArm.enableVoltageCompensation(true)
    pivotRightArm.burnFlash()

    pivotLeftArm.configFactoryDefault()
    pivotLeftArm.inverted = true
    pivotLeftArm.setNeutralMode(NeutralMode.Brake)
    pivotRightArm.enableVoltageCompensation(true)
    pivotLeftArm.burnFlash()
  }


}
