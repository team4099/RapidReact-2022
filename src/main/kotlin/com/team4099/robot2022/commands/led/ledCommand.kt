package com.team4099.robot2022.commands.led

import com.team4099.robot2022.config.Constants
import com.team4099.robot2022.subsystems.Led

class LedCommand : CommandBase() {
  init {
    addRequirement(Feeder)
    addRequirement(Intake)
    addRequirement(Shooter)
    addRequirement(Led)
  }

  override fun initialize(){
    if (Intake.intakeState == 1.0 || Shooter.shooterState == 1200.0) {
      val isChanging = true
    }
    when (Feeder.ballCount) {
      0 -> {
        if (isChanging){
          Led.ledState = Constants.Led.LedState.CHANGING_ZERO
        }
        else {
          Led.ledState = Constants.Led.LedState.STANDING_ZERO
        }
      }
      1 -> {
        if (isChanging){
          Led.ledState = Constants.Led.LedState.CHANGING_ONE
        }
        else {
          Led.ledState = Constants.Led.LedState.STANDING_ONE
        }
      }
      2 -> {
        if (isChanging){
          Led.ledState = Constants.Led.LedState.CHANGING_TWO
        }
        else {
          Led.ledState = Constants.Led.LedState.STANDING_TWO
        }
      }
    }
  }
}
