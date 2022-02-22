package com.team4099.robot2022.commands.led

import com.team4099.robot2022.config.constants.LEDConstants
import com.team4099.robot2022.subsystems.LED
import edu.wpi.first.wpilibj2.command.CommandBase

class ChangeLedState(val changestate: LEDConstants.LEDState) : CommandBase() {
  init {
    addRequirements(LED)
  }

  override fun initialize() {
    /* val isChanging = false

    if (Intake.intakeTalon.supplyCurrent >= LEDConstants.CURRENT_THRESHOLD){
      LED.ballCount = LED.ballCount + 1
    }
    else {
      LED.intakeReady = true
    }


    if (Intake.intakeState == 1.0 || Shooter.shooterState == 1200.0) {
      val isChanging = true
    }
    when (LED.ballCount) {
      0 -> {
        if (isChanging) {
          LED.ledState = LEDConstants.LEDState.CHANGING_ZERO
        } else {
          LED.ledState = LEDConstants.LEDState.STANDING_ZERO
        }
      }
      1 -> {
        val isChanging = false
        if (isChanging) {
          LED.ledState = LEDConstants.LEDState.CHANGING_ONE
        } else {
          LED.ledState = LEDConstants.LEDState.STANDING_ONE
        }
      }
      2 -> {
        val isChanging = false

        if (isChanging) {

          LED.ledState = LEDConstants.LEDState.CHANGING_TWO
        } else {
          LED.ledState = LEDConstants.LEDState.STANDING_TWO
        }
      }
    }
    */
  }
}
