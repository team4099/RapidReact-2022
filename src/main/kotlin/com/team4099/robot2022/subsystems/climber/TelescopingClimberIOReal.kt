package com.team4099.robot2022.subsystems.climber

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.DemandType
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration
import com.team4099.lib.units.base.Length
import com.team4099.lib.units.base.amps
import com.team4099.lib.units.ctreLinearMechanismSensor
import com.team4099.lib.units.derived.ElectricalPotential
import com.team4099.lib.units.derived.inVolts
import com.team4099.lib.units.derived.volts
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.Constants.Universal.CANIVORE_NAME
import com.team4099.robot2022.config.constants.TelescopingClimberConstants

object TelescopingClimberIOReal : TelescopingClimberIO {
  private val telescopingRightArm: TalonFX = TalonFX(Constants.TelescopingClimber.R_ARM_ID)
  private val telescopingLeftArm: TalonFX =
    TalonFX(Constants.TelescopingClimber.L_ARM_ID, CANIVORE_NAME)

  val telescopingRightArmSensor =
    ctreLinearMechanismSensor(
      telescopingRightArm,
      TelescopingClimberConstants.SENSOR_CPR,
      TelescopingClimberConstants.GEAR_RATIO,
      TelescopingClimberConstants.RIGHT_SPOOL_RADIUS * 2
    )
  val telescopingLeftArmSensor =
    ctreLinearMechanismSensor(
      telescopingLeftArm,
      TelescopingClimberConstants.SENSOR_CPR,
      TelescopingClimberConstants.GEAR_RATIO,
      TelescopingClimberConstants.LEFT_SPOOL_RADIUS * 2
    )

  private val telescopingConfiguration: TalonFXConfiguration = TalonFXConfiguration()

  val currentPosition: Length
    get() {
      if (telescopingLeftArmSensor.position > telescopingRightArmSensor.position) {
        return telescopingLeftArmSensor.position
      } else {
        return telescopingRightArmSensor.position
      }
    }

  init {
    telescopingConfiguration.slot0.kP = TelescopingClimberConstants.KP
    telescopingConfiguration.slot0.kI = TelescopingClimberConstants.KI
    telescopingConfiguration.slot0.kD = TelescopingClimberConstants.KD
    telescopingConfiguration.slot0.kF = TelescopingClimberConstants.KFF

    telescopingRightArm.configFactoryDefault()
    telescopingRightArm.clearStickyFaults()
    telescopingRightArm.configAllSettings(telescopingConfiguration)
    telescopingRightArm.setNeutralMode(NeutralMode.Brake)
    telescopingRightArm.enableVoltageCompensation(true)
    telescopingRightArm.inverted = true
    telescopingRightArm.configForwardSoftLimitThreshold(
      telescopingRightArmSensor.positionToRawUnits(
        TelescopingClimberConstants.FORWARD_SOFT_LIMIT
      )
    )
    telescopingRightArm.configForwardSoftLimitEnable(false)
    telescopingRightArm.configReverseSoftLimitThreshold(
      telescopingRightArmSensor.positionToRawUnits(
        TelescopingClimberConstants.REVERSE_SOFT_LIMIT
      )
    )

    telescopingLeftArm.configFactoryDefault()
    telescopingLeftArm.clearStickyFaults()
    telescopingLeftArm.configAllSettings(telescopingConfiguration)
    telescopingLeftArm.setNeutralMode(NeutralMode.Brake)
    telescopingLeftArm.enableVoltageCompensation(true)
    telescopingLeftArm.inverted = false
    telescopingLeftArm.configForwardSoftLimitThreshold(
      telescopingLeftArmSensor.positionToRawUnits(TelescopingClimberConstants.FORWARD_SOFT_LIMIT)
    )
    telescopingLeftArm.configForwardSoftLimitEnable(false)
    telescopingLeftArm.configReverseSoftLimitThreshold(
      telescopingLeftArmSensor.positionToRawUnits(TelescopingClimberConstants.REVERSE_SOFT_LIMIT)
    )
  }

  override fun updateInputs(inputs: TelescopingClimberIO.TelescopingClimberIOInputs) {
    inputs.leftPosition = telescopingLeftArmSensor.position
    inputs.rightPosition = telescopingRightArmSensor.position

    inputs.leftVelocity = telescopingLeftArmSensor.velocity
    inputs.rightVelocity = telescopingRightArmSensor.velocity

    inputs.leftStatorCurrent = telescopingLeftArm.statorCurrent.amps
    inputs.rightStatorCurrent = telescopingRightArm.statorCurrent.amps

    inputs.leftSupplyCurrent = telescopingLeftArm.supplyCurrent.amps
    inputs.rightSupplyCurrent = telescopingRightArm.supplyCurrent.amps

    inputs.leftOutputVoltage = telescopingLeftArm.motorOutputVoltage.volts
    inputs.rightOutputVoltage = telescopingRightArm.motorOutputVoltage.volts

    inputs.leftTemperatureCelcius = telescopingLeftArm.temperature
    inputs.rightTemperatureCelcius = telescopingRightArm.temperature
  }

  override fun zeroLeftEncoder() {
    telescopingLeftArm.selectedSensorPosition = 0.0
  }

  override fun zeroRightEncoder() {
    telescopingRightArm.selectedSensorPosition = 0.0
  }

  override fun setLeftOpenLoop(percentOutput: Double) {
    telescopingLeftArm.set(ControlMode.PercentOutput, percentOutput)
  }

  override fun setRightOpenLoop(percentOutput: Double) {
    telescopingRightArm.set(ControlMode.PercentOutput, percentOutput)
  }

  override fun setLeftPosition(height: Length, feedforward: ElectricalPotential) {
    telescopingLeftArm.set(
      ControlMode.Position,
      telescopingLeftArmSensor.positionToRawUnits(height),
      DemandType.ArbitraryFeedForward,
      feedforward.inVolts / 12.0
    )
  }

  override fun setRightPosition(height: Length, feedforward: ElectricalPotential) {
    telescopingRightArm.set(
      ControlMode.Position,
      telescopingRightArmSensor.positionToRawUnits(height),
      DemandType.ArbitraryFeedForward,
      feedforward.inVolts / 12.0
    )
  }

  override fun configPID(kP: Double, kI: Double, kD: Double) {
    telescopingLeftArm.config_kP(0, kP)
    telescopingLeftArm.config_kI(0, kI)
    telescopingLeftArm.config_kD(0, kD)

    telescopingRightArm.config_kP(0, kP)
    telescopingRightArm.config_kI(0, kI)
    telescopingRightArm.config_kD(0, kD)
  }
}
