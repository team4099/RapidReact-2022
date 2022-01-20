package com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.team4099.robot2022.config.Constants
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj.Solenoid
import edu.wpi.first.wpilibj2.command.SubsystemBase

class Climber: SubsystemBase(){

  private val tmotor: TalonFX = TalonFX(Constants.Climber.CLIMBER_RID)
  private val pmotor: TalonFX = TalonFX(Constants.Climber.CLIMBER_LID)
  //PneumaticsModuleType new?
  private val pneumaticBreak = Solenoid(
    PneumaticsModuleType.CTREPCM,
    Constants.Climber.SOLENOID_ID
  )
  var isLocked: Boolean = true
    set(value){
      pneumaticBreak.set(!value)
      field = value
    }
  init {

  }
//******SECTIONS COMMENTED OUT TO AVOID ERRORS TEMPORARILY*****
  //private val climberTArm =

 // private val climberPArm =

  //val telescopingArmSensor =

  //val pivotArmSensor =

  fun setTelescopingArmPosition(position : Constants.TelescopingArmPosition) {

  }
  fun setPivotArmPosition(position : Constants.PivotArmPosition) {

  }
  fun setOpenLoopPower() {

  }
}
