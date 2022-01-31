package com.team4099.robot2022.commands.led

import com.team4099.robot2022.config.Constants
import com.team4099.robot2022.subsystems.LED
import edu.wpi.first.wpilibj2.command.CommandBase

class ChangeLedState(val changestate: Constants.LED.LEDState) : CommandBase() {
  /*
  init {
    addRequirement(Feeder)
    addRequirement(Intake)
    addRequirement(Shooter)
    addRequirement(LED)
  }

  override fun initialize() {
    val isChanging = false

    if (Intake.intakeState == 1.0 || Shooter.shooterState == 1200.0) {
      val isChanging = true
    }
    when (Feeder.ballCount) {
      0 -> {
        if (isChanging) {
          LED.ledState = Constants.Led.LedState.CHANGING_ZERO
        } else {
          LED.ledState = Constants.Led.LedState.STANDING_ZERO
        }
      }
      1 -> {
        val isChanging = false
        if (isChanging) {
          LED.ledState = Constants.Led.LedState.CHANGING_ONE
        } else {
          LED.ledState = Constants.Led.LedState.STANDING_ONE
        }
      }
      2 -> {
        val isChanging = false

        if (isChanging) {

          LED.ledState = Constants.Led.LedState.CHANGING_TWO
        } else {
          LED.ledState = Constants.Led.LedState.STANDING_TWO
        }
      }
    }
  }
   */

  init {
    addRequirements(LED)
  }

  override fun initialize() {
    LED.ledState = changestate
  }
}
