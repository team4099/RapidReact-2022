package com.team4099.robot2022.commands.led

import com.team4099.robot2022.Robot
import com.team4099.robot2022.config.constants.IntakeConstants
import com.team4099.robot2022.config.constants.LEDConstants
import com.team4099.robot2022.config.constants.ShooterConstants
import com.team4099.robot2022.config.constants.TelescopingClimberConstants
import com.team4099.robot2022.subsystems.climber.PivotClimber
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.intake.Intake
import com.team4099.robot2022.subsystems.led.Led
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj2.command.CommandBase

class LedCommand(
  val led: Led,
  val intake: Intake,
  val shooter: Shooter,
  val feeder: Feeder,
  val telescopingClimber: TelescopingClimber,
  val pivotClimber: PivotClimber
) : CommandBase() {
  private var climbDone: Boolean

  init {
    addRequirements(led)
    climbDone = false
  }

  override fun execute() {
    led.state =
        when (telescopingClimber.currentState) {
          TelescopingClimberConstants.ActualTelescopeStates.START -> {
            when (shooter.state) {
              ShooterConstants.ShooterState.SPIN_UP_UPPER,
              ShooterConstants.ShooterState.SPIN_UP_LOWER -> {
                if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
                  LEDConstants.LEDState.RED_SHOOT
                } else {
                  LEDConstants.LEDState.BLUE_SHOOT
                }
              }
              else -> {
                if (Robot.isAutonomous) {
                  LEDConstants.LEDState.AUTO
                } else if (intake.hasBall) {
                  when (intake.rollerState) {
                    IntakeConstants.RollerState.OUT -> LEDConstants.LEDState.OUTTAKING
                    IntakeConstants.RollerState.IN -> LEDConstants.LEDState.INTAKING
                    else -> LEDConstants.LEDState.IDLE
                  }
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
          TelescopingClimberConstants.ActualTelescopeStates.BETWEEN_START_AND_MAX_RETRACT,
          TelescopingClimberConstants.ActualTelescopeStates
              .BETWEEN_MAX_RETRACT_AND_MAX_EXTENSION -> {
            LEDConstants.LEDState.CLIMBING
          }
          TelescopingClimberConstants.ActualTelescopeStates.MAX_RETRACT -> {
            if (climbDone) {
              if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
                LEDConstants.LEDState.CLIMB_FINISHED_RED_ALLIANCE
              } else {
                LEDConstants.LEDState.CLIMB_FINISHED_BLUE_ALLIANCE
              }
            } else {
              LEDConstants.LEDState.CLIMBER_READY
            }
          }
          TelescopingClimberConstants.ActualTelescopeStates.MAX_EXTENSION -> {
            LEDConstants.LEDState.CLIMBER_READY
          }
        }
  }

  override fun isFinished(): Boolean {
    return false
  }

  fun climberFinished() {
    climbDone = true
  }
}
