package com.team4099.robot2022.subsystems.feeder

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.team4099.lib.units.base.amps
import com.team4099.lib.units.derived.volts
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.Constants.Universal.CANIVORE_NAME
import edu.wpi.first.wpilibj.DigitalInput

object FeederIOReal : FeederIO {
  // The motor that takes balls from the intake
  private val floorMotor = TalonFX(Constants.Feeder.FLOOR_MOTOR_ID, CANIVORE_NAME)

  // The motor that takes balls to the shooter
  private val verticalMotor = TalonFX(Constants.Feeder.VERTICAL_MOTOR_ID, CANIVORE_NAME)

  // The top Beam Break
  private val topBeamDIO = DigitalInput(Constants.Feeder.TOP_DIO_PIN)

  // The bottom Beam Break
  private val bottomBeamDIO = DigitalInput(Constants.Feeder.BOTTOM_DIO_PIN)

  val topBeamBroken: Boolean
    get() = !topBeamDIO.get()

  val bottomBeamBroken: Boolean
    get() = !bottomBeamDIO.get()

  init {
    floorMotor.configFactoryDefault()
    verticalMotor.configFactoryDefault()

    // temp values
    floorMotor.enableVoltageCompensation(true)
    verticalMotor.enableVoltageCompensation(true)

    floorMotor.setNeutralMode(NeutralMode.Coast)
    verticalMotor.setNeutralMode(NeutralMode.Brake)

    floorMotor.inverted = true

    //    floorMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_1_General,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    floorMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_2_Feedback0,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    floorMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_3_Quadrature,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    floorMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_4_AinTempVbat,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    floorMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_6_Misc,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    floorMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_8_PulseWidth,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    floorMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_9_MotProfBuffer,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    floorMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_10_MotionMagic,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    floorMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_10_Targets,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    floorMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_12_Feedback1,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    floorMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_13_Base_PIDF0,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    floorMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_14_Turn_PIDF1,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    floorMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_15_FirmwareApiStatus,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //
    //    verticalMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_1_General,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    verticalMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_2_Feedback0,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    verticalMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_3_Quadrature,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    verticalMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_4_AinTempVbat,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    verticalMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_6_Misc,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    verticalMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_8_PulseWidth,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    verticalMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_9_MotProfBuffer,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    verticalMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_10_MotionMagic,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    verticalMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_10_Targets,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    verticalMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_12_Feedback1,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    verticalMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_13_Base_PIDF0,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    verticalMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_14_Turn_PIDF1,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
    //    verticalMotor.setStatusFramePeriod(
    //        StatusFrameEnhanced.Status_15_FirmwareApiStatus,
    //        Constants.Universal.SLOW_STATUS_FRAME_TIME.inMilliseconds.toInt())
  }

  override fun updateInputs(inputs: FeederIO.FeederIOInputs) {
    inputs.topBeamBroken = topBeamBroken
    inputs.bottomBeamBroken = bottomBeamBroken

    inputs.floorAppliedVoltage = floorMotor.motorOutputVoltage.volts
    inputs.verticalAppliedVoltage = verticalMotor.motorOutputVoltage.volts
    inputs.floorStatorCurrent = floorMotor.statorCurrent.amps
    inputs.floorSupplyCurrent = floorMotor.supplyCurrent.amps
    inputs.verticalStatorCurrent = verticalMotor.statorCurrent.amps
    inputs.verticalSupplyCurrent = verticalMotor.supplyCurrent.amps

    inputs.floorTempCelcius = floorMotor.temperature
    inputs.verticalTempCelcius = verticalMotor.temperature
  }

  override fun setPower(floorPower: Double, verticalPower: Double) {
    floorMotor.set(ControlMode.PercentOutput, floorPower)
    verticalMotor.set(ControlMode.PercentOutput, verticalPower)
  }
}
