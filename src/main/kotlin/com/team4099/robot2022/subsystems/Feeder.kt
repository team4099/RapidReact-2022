package com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.FeederConstants
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj2.command.SubsystemBase

object Feeder : SubsystemBase() {

  // The motor that takes balls from the intake
  private val floorMotor = TalonFX(Constants.Feeder.FLOOR_MOTOR_ID)

  // The motor that takes balls to the shooter
  private val verticalMotor = TalonFX(Constants.Feeder.VERTICAL_MOTOR_ID)

  // The top Beam Break
  private val topBeamDIO = DigitalInput(Constants.Feeder.TOP_DIO_PIN)

  // The bottom Beam Break
  private val bottomBeamDIO = DigitalInput(Constants.Feeder.BOTTOM_DIO_PIN)

  val topBeamBroken: Boolean
    //    get() = !topBeamDIO.get()
    get() = false

  val bottomBeamBroken: Boolean
    get() = !bottomBeamDIO.get()

  var feederState = FeederConstants.FeederState.NEUTRAL
    set(state) {
      field = state
      floorMotor.set(ControlMode.PercentOutput, feederState.floorMotorPower)
      verticalMotor.set(ControlMode.PercentOutput, feederState.verticalMotorPower)
    }

  var ballCount: Int = 0
  private var bottomPrevStage: Boolean = bottomBeamBroken
  private var topPrevStage: Boolean = bottomBeamBroken
  override fun periodic() {
    if ((bottomPrevStage != bottomBeamBroken) && !bottomBeamBroken) {
      if (floorMotor.motorOutputPercent > 0) {
        ballCount++
      } else if (floorMotor.motorOutputPercent < 0) {
        ballCount--
      }
    }
    if ((topPrevStage != topBeamBroken) && !topBeamBroken && verticalMotor.motorOutputPercent > 0) {
      ballCount--
    }
    bottomPrevStage = bottomBeamBroken
    topPrevStage = topBeamBroken
  }

  init {
    Logger.addSource(FeederConstants.TAB, "Feeder State") { feederState.toString() }
    // floor motor values
    Logger.addSource(FeederConstants.TAB, "Feeder Floor Motor Power") {
      floorMotor.motorOutputPercent
    }
    Logger.addSource(FeederConstants.TAB, "Feeder Floor Motor Supply Current") {
      floorMotor.supplyCurrent
    }
    Logger.addSource(FeederConstants.TAB, "Feeder Floor Motor Voltage") { floorMotor.busVoltage }
    // vertical motor values
    Logger.addSource(FeederConstants.TAB, "Feeder Vertical Motor Power") {
      verticalMotor.motorOutputPercent
    }
    Logger.addSource(FeederConstants.TAB, "Feeder Vertical Motor Supply Current") {
      verticalMotor.supplyCurrent
    }
    Logger.addSource(FeederConstants.TAB, "Feeder Vertical Motor Voltage") {
      verticalMotor.busVoltage
    }
    // beam broken values
    Logger.addSource(FeederConstants.TAB, "Feeder Top Beam DIO Broken") { topBeamBroken }
    Logger.addSource(FeederConstants.TAB, "Feeder Bottom Beam DIO Broken") { bottomBeamBroken }
    // ball count value
    Logger.addSource(FeederConstants.TAB, "Feeder Ball Count") { ballCount }

    floorMotor.configFactoryDefault()
    verticalMotor.configFactoryDefault()

    // temp values
    floorMotor.enableVoltageCompensation(true)
    verticalMotor.enableVoltageCompensation(true)

    floorMotor.setNeutralMode(NeutralMode.Coast)
    verticalMotor.setNeutralMode(NeutralMode.Coast)

    floorMotor.inverted = true
  }
}