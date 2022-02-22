package com.team4099.robot2021.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration
import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.IntakeConstants
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj.Solenoid
import edu.wpi.first.wpilibj2.command.SubsystemBase

object Intake : SubsystemBase() {

  // motor params
  private val intakeConfiguration: TalonFXConfiguration = TalonFXConfiguration()

  val intakeTalon = TalonFX(Constants.Intake.INTAKE_MOTOR)
  private val intakeSolenoid = Solenoid(PneumaticsModuleType.REVPH, Constants.Intake.ARM_SOLENOID)

  var intakeState = IntakeConstants.IntakeState.IDLE
    set(state) {
      intakeTalon.set(ControlMode.PercentOutput, state.speed)
      field = state
    }

  var armState = IntakeConstants.ArmPos.IN // Change Eventually
    set(state) {
      intakeSolenoid.set(state.out)
      field = state
    }

  init {
    intakeTalon.configFactoryDefault()
    //    Logger.addSource(IntakeConstants.TAB, "Intake State") { intakeState.toString() }
    Logger.addSource(IntakeConstants.TAB, "Intake Motor Power") { intakeTalon.motorOutputPercent }
    Logger.addSource(IntakeConstants.TAB, "Intake Motor Stator Current") {
      intakeTalon.statorCurrent
    }
    Logger.addSource(IntakeConstants.TAB, "Intake Motor Supply Current") {
      intakeTalon.supplyCurrent
    }
    Logger.addSource(IntakeConstants.TAB, "Intake Motor Voltage") { intakeTalon.motorOutputVoltage }
    //    Logger.addSource(IntakeConstants.TAB, "Arm State") { armState.toString() }

    intakeConfiguration.supplyCurrLimit.currentLimit = IntakeConstants.SUPPLY_CURRENT_LIMIT
    intakeTalon.configAllSettings(intakeConfiguration)
    intakeTalon.configOpenloopRamp(IntakeConstants.RAMP_TIME)
  }
}
