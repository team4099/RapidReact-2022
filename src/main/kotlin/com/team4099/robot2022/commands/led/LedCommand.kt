package com.team4099.robot2022.commands.led

import com.team4099.robot2022.Robot
import com.team4099.robot2022.config.constants.LEDConstants
import com.team4099.robot2022.config.constants.ShooterConstants
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.intake.Intake
import com.team4099.robot2022.subsystems.led.Led
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj2.command.CommandBase

class LedCommand(val led: Led, val intake: Intake, val shooter: Shooter, val feeder: Feeder) :
    CommandBase() {
  init {
    addRequirements(led)
  }

  override fun execute() {
    led.state =
        when (shooter.state) {
          ShooterConstants.ShooterState.SPIN_UP_NEAR,
          ShooterConstants.ShooterState.SPIN_UP_LOWER -> {
            if (shooter.isOnTarget) {
              LEDConstants.LEDState.READY_SHOOT
            } else {
              LEDConstants.LEDState.SPINNING_UP
            }
          }
          else -> {
            if (Robot.isDisabled) {
              LEDConstants.LEDState.IDLE
            } else if (intake.hasBall) {
              LEDConstants.LEDState.INTAKING
            } else {
              when (feeder.ballCount) {
                0 -> LEDConstants.LEDState.STANDING_ZERO
                1 -> LEDConstants.LEDState.STANDING_ONE
                2 -> LEDConstants.LEDState.STANDING_TWO
                else -> LEDConstants.LEDState.IDLE
              }
            }
          }
        }
  }

  override fun isFinished(): Boolean {
    return false
  }
}
