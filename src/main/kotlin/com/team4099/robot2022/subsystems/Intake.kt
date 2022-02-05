package com.team4099.robot2021.subsystems.com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration
import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.Constants
import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj2.command.SubsystemBase

object Intake : SubsystemBase() {

  // motor params
  private val intakeConfiguration: TalonFXConfiguration = TalonFXConfiguration()

  private val intakeTalon = TalonFX(Constants.Intake.INTAKE_MOTOR)
  private val intakeDoubleSolenoid =
      DoubleSolenoid(
          PneumaticsModuleType.CTREPCM,
          Constants.Intake.ARM_SOLENOID_FORWARD,
          Constants.Intake.ARM_SOLENOID_REVERSE)

  var intakeState = Constants.Intake.IntakeState.IDLE
    set(state) {
      intakeTalon.set(ControlMode.PercentOutput, state.speed)
      field = state
    }

  var armState = Constants.Intake.ArmPos.IN // Change Eventually
    set(state) {
      intakeDoubleSolenoid.set(state.position)
      field = state
    }

  init {
    intakeTalon.configFactoryDefault()
    Logger.addSource(Constants.Intake.TAB, "Intake State") { intakeState.toString() }
    Logger.addSource(Constants.Intake.TAB, "Intake Motor Power") { intakeTalon.motorOutputPercent }
    Logger.addSource(Constants.Intake.TAB, "Intake Motor Stator Current") {
      intakeTalon.statorCurrent
    }
    Logger.addSource(Constants.Intake.TAB, "Intake Motor Supply Current") {
      intakeTalon.supplyCurrent
    }
    Logger.addSource(Constants.Intake.TAB, "Intake Motor Voltage") {
      intakeTalon.motorOutputVoltage
    }
    Logger.addSource(Constants.Intake.TAB, "Arm State") { armState.toString() }

    intakeConfiguration.supplyCurrLimit.currentLimit = Constants.Intake.SUPPLY_CURRENT_LIMIT
    intakeTalon.configOpenloopRamp(Constants.Intake.RAMP_TIME)
  }
}
