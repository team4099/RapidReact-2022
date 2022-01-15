package com.team4099.robot2021.subsystems.com.team4099.robot2022.subsystems
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.team4099.robot2022.config.Constants
import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj2.command.SubsystemBase





object Intake : SubsystemBase() {

  private val intakeTalon = TalonFX(Constants.Intake.INTAKE_MOTOR)
  private val intakeDoubleSolenoid =
    DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.Intake.ARM_SOLENOID_FORWARD, Constants.Intake.ARM_SOLENOID_REVERSE)

}
