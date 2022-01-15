package com.team4099.robot2021.subsystems.com.team4099.robot2022.subsystems
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.Constants
import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj2.command.SubsystemBase


object Intake : SubsystemBase() {

  private val intakeTalon = TalonFX(Constants.Intake.INTAKE_MOTOR)
  private val intakeDoubleSolenoid =
    DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.Intake.ARM_SOLENOID_FORWARD, Constants.Intake.ARM_SOLENOID_REVERSE)

  var intakeState = Constants.Intake.IntakeState.IDLE
    set(value) {
      intakeTalon.set(ControlMode.PercentOutput, value.speed)
      field = value
    }

  var armState = Constants.Intake.ArmPos.IN //Change Eventually
    set(value) {
      intakeDoubleSolenoid.set(value.position)
      field = value
    }

  init {
    intakeTalon.configFactoryDefault()
    Logger.addSource("INTAKE", "Intake State") { intakeState.toString() }
    Logger.addSource("INTAKE", "Intake Motor Power") { intakeTalon.motorOutputPercent }
    Logger.addSource("INTAKE", "Intake Motor Stator Current") {
      intakeTalon.statorCurrent
    }
    Logger.addSource("INTAKE", "Intake Motor Supply Current") {
      intakeTalon.supplyCurrent
    }
    Logger.addSource("INTAKE", "Intake Motor Voltage") {
      intakeTalon.motorOutputVoltage
    }
    Logger.addSource("INTAKE", "Arm State") { armState.toString() }

    intakeTalon.configOpenloopRamp(Constants.Intake.RAMP_TIME)
  }
  
}
