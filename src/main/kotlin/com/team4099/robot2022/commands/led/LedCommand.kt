package com.team4099.robot2022.commands.led

import com.team4099.robot2022.Robot
import com.team4099.robot2022.config.constants.IntakeConstants
import com.team4099.robot2022.config.constants.LEDConstants
import com.team4099.robot2022.config.constants.ShooterConstants
import com.team4099.robot2022.subsystems.LED
import com.team4099.robot2022.subsystems.intake.Intake
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj2.command.CommandBase

class LedCommand(val intake: Intake, val shooter: Shooter) : CommandBase() {
  init {}

  override fun execute() {
    LED.ledState =
        when (shooter.state) {
          ShooterConstants.ShooterState.SPIN_UP_NEAR, ShooterConstants.ShooterState.SPIN_UP_FAR -> {
            if (shooter.isOnTarget) {
              LEDConstants.LEDState.READY_SHOOT
            } else {
              LEDConstants.LEDState.SPINNING_UP
            }
          }
          else -> {
            if (Robot.isDisabled) {
              LEDConstants.LEDState.IDLE
            } else if (intake.inputs.rollerStatorCurrent >= LEDConstants.INTAKE_CURRENT_THRESHOLD &&
                intake.rollerState == IntakeConstants.RollerState.IN) {
              LEDConstants.LEDState.INTAKING
            } else {
              LEDConstants.LEDState.STANDING_ZERO
            }
          }
        }
  }

  override fun isFinished(): Boolean {
    return false
  }
}