package com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.Constants
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
    get() = !topBeamDIO.get()

  val bottomBeamBroken: Boolean
    get() = !bottomBeamDIO.get()

  var feederState = Constants.Feeder.FeederState.NEUTRAL
    set(value) {
      field = value
      floorMotor.set(ControlMode.PercentOutput, feederState.floorMotorPower)
      verticalMotor.set(ControlMode.PercentOutput, feederState.verticalMotorPower)
    }

  var ballCount: Int = 0

  init {
    Logger.addSource(Constants.Feeder.TAB, "Feeder State") { feederState.toString() }
    // floor motor values
    Logger.addSource(Constants.Feeder.TAB, "Feeder Floor Motor Power") { floorMotor.motorOutputPercent }
    Logger.addSource(Constants.Feeder.TAB, "Feeder Floor Motor Supply Current") { floorMotor.supplyCurrent }
    Logger.addSource(Constants.Feeder.TAB, "Feeder Floor Motor Voltage") { floorMotor.busVoltage }
    // vertical motor values
    Logger.addSource(Constants.Feeder.TAB, "Feeder Vertical Motor Power") { verticalMotor.motorOutputPercent }
    Logger.addSource(Constants.Feeder.TAB, "Feeder Vertical Motor Supply Current") { verticalMotor.supplyCurrent }
    Logger.addSource(Constants.Feeder.TAB, "Feeder Vertical Motor Voltage") { verticalMotor.busVoltage }
    // beam broken values
    Logger.addSource(Constants.Feeder.TAB, "Feeder Top Beam DIO Broken") { topBeamBroken }
    Logger.addSource(Constants.Feeder.TAB, "Feeder Bottom Beam DIO Broken") { bottomBeamBroken }
    // beam time values
    Logger.addSource(Constants.Feeder.TAB, "Feeder Beam Broken forwards time") { Constants.Feeder.BEAM_BREAK_BROKEN_TIME }
    Logger.addSource(Constants.Feeder.TAB, "Feeder Beam Broken backwards time") { Constants.Feeder.BEAM_BREAK_BACKWARDS_TIME }
    Logger.addSource(Constants.Feeder.TAB, "Feeder Ball Count") { ballCount }
  }
}
