package com.team4099.robot2022.subsystems

import com.team4099.lib.drivers.BlinkinLedDriver
import com.team4099.lib.logging.Logger
import com.team4099.robot2021.subsystems.Intake
import com.team4099.robot2022.Robot
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.IntakeConstants
import com.team4099.robot2022.config.constants.LEDConstants
import com.team4099.robot2022.config.constants.ShooterConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase

object LED : SubsystemBase() {
  private val ledController = BlinkinLedDriver(Constants.LED.LED_CONTROLLER_ID)

  var ledState = LEDConstants.LEDState.IDLE
    set(newState) {
      ledController.setMode(newState.blinkinMode)
      field = newState
    }

  init {
    Logger.addSource(Constants.LED.TAB, "LED Status") { ledState.toString() }
  }

  override fun periodic() {
    // when (Climber.telescopeState) {
    //  ClimberConstants.TelescopeState.OFF -> {
    //    insert current code
    //  }
    //  else ->
    // }
    ledState = when (Shooter.shooterState) {
      ShooterConstants.ShooterState.SPIN_UP_NEAR, ShooterConstants.ShooterState.SPIN_UP_FAR -> {
            if (Shooter.isOnTarget) {
              LEDConstants.LEDState.READY_SHOOT
            } else {
              LEDConstants.LEDState.SPINNING_UP
            }
      }
      else -> {
        if (Robot.isDisabled) {
          LEDConstants.LEDState.IDLE
        } else if (Intake.intakeTalon.statorCurrent >= LEDConstants.CURRENT_THRESHOLD && Intake.intakeState == IntakeConstants.IntakeState.IN) {
          LEDConstants.LEDState.INTAKING
        } else {
          LEDConstants.LEDState.STANDING_ZERO
        }
      }
    }
  }
}
