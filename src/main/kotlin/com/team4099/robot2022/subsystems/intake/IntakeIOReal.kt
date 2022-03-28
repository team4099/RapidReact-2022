package com.team4099.robot2022.subsystems.intake

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration
import com.team4099.lib.units.base.amps
import com.team4099.lib.units.ctreAngularMechanismSensor
import com.team4099.lib.units.derived.volts
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.Constants.Universal.CANIVORE_NAME
import com.team4099.robot2022.config.constants.IntakeConstants
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj.Solenoid

object IntakeIOReal : IntakeIO {
  private val intakeConfiguration: TalonFXConfiguration = TalonFXConfiguration()

  private val intakeTalon = TalonFX(Constants.Intake.INTAKE_MOTOR, CANIVORE_NAME)
  private val intakeSolenoid = Solenoid(PneumaticsModuleType.REVPH, Constants.Intake.ARM_SOLENOID)

  private val intakeSensor =
    ctreAngularMechanismSensor(
      intakeTalon, IntakeConstants.SENSOR_CPR, IntakeConstants.GEAR_RATIO
    )

  init {
    intakeTalon.configFactoryDefault()

    //    intakeConfiguration.supplyCurrLimit.currentLimit = IntakeConstants.SUPPLY_CURRENT_LIMIT
    intakeConfiguration.supplyCurrLimit =
      SupplyCurrentLimitConfiguration(
        true, IntakeConstants.SUPPLY_CURRENT_LIMIT, IntakeConstants.SUPPLY_CURRENT_LIMIT, 200.0
      )

    intakeTalon.configAllSettings(intakeConfiguration)
    intakeTalon.configOpenloopRamp(IntakeConstants.RAMP_TIME)

    //    intakeTalon.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_1_General,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    intakeTalon.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_2_Feedback0,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    intakeTalon.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_3_Quadrature,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    intakeTalon.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_4_AinTempVbat,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    intakeTalon.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_6_Misc,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    intakeTalon.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_8_PulseWidth,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    intakeTalon.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_9_MotProfBuffer,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    intakeTalon.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_10_MotionMagic,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    intakeTalon.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_10_Targets,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    intakeTalon.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_12_Feedback1,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    intakeTalon.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_13_Base_PIDF0,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    intakeTalon.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_14_Turn_PIDF1,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    intakeTalon.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_15_FirmwareApiStatus,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
  }

  override fun setRollerPower(percentOutput: Double) {
    intakeTalon.set(ControlMode.PercentOutput, percentOutput)
  }

  override fun setArmSolenoid(solenoidValue: Boolean) {
    intakeSolenoid.set(solenoidValue)
  }

  override fun updateInputs(inputs: IntakeIO.IntakeIOInputs) {
    inputs.extended = intakeSolenoid.get() == IntakeConstants.ArmState.OUT.out

    inputs.rollerPosition = intakeSensor.position
    inputs.rollerVelocity = intakeSensor.velocity
    inputs.rollerStatorCurrent = intakeTalon.statorCurrent.amps
    inputs.rollerSupplyCurrent = intakeTalon.supplyCurrent.amps
    inputs.rollerAppliedVoltage = intakeTalon.motorOutputVoltage.volts
    inputs.rollerTempCelcius = intakeTalon.temperature
  }
}
