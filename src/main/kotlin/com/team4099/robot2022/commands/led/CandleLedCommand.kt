package com.team4099.robot2022.commands.led

import com.team4099.robot2022.Robot
import com.team4099.robot2022.config.constants.IntakeConstants
import com.team4099.robot2022.config.constants.LEDConstants
import com.team4099.robot2022.config.constants.PneumaticConstants
import com.team4099.robot2022.config.constants.TelescopingClimberConstants
import com.team4099.robot2022.subsystems.climber.PivotClimber
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.intake.Intake
import com.team4099.robot2022.subsystems.led.CandleLed
import com.team4099.robot2022.subsystems.pneumatics.Pneumatic
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj2.command.CommandBase

class CandleLedCommand(
  val led: CandleLed,
  val intake: Intake,
  val shooter: Shooter,
  val feeder: Feeder,
  val telescopingClimber: TelescopingClimber,
  val pivotClimber: PivotClimber,
  val pneumatic: Pneumatic
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
          when (telescopingClimber.desiredState) {
            TelescopingClimberConstants.DesiredTelescopeStates.START -> {
                /*             when (shooter.state) {
                  ShooterConstants.ShooterState.SPIN_UP_UPPER,
                 ShooterConstants.ShooterState.SPIN_UP_LOWER -> {
                    if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
                     LEDConstants.CandleState.RED_SHOOT
                   } else {
                     LEDConstants.CandleState.BLUE_SHOOT
                   }
                 }
                else -> {*/
              if (Robot.isAutonomous) {
                LEDConstants.CandleState.AUTO
              } else if (intake.keepIntakingLEDState) {
                when (intake.rollerState) {
                  IntakeConstants.RollerState.OUT -> LEDConstants.CandleState.OUTTAKING
                  IntakeConstants.RollerState.IN -> LEDConstants.CandleState.INTAKING
                  else -> LEDConstants.CandleState.IDLE
                }
              } else {
                when (feeder.ballCount) {
                  0 -> LEDConstants.CandleState.STANDING_ZERO
                  1 -> LEDConstants.CandleState.STANDING_ONE
                  2 -> LEDConstants.CandleState.STANDING_TWO
                  else -> LEDConstants.CandleState.IDLE
                }
              }
              //               }
              //            }
            }
            else -> LEDConstants.CandleState.IDLE
          }
        }
        TelescopingClimberConstants.ActualTelescopeStates.BETWEEN_START_AND_MAX_RETRACT,
        TelescopingClimberConstants.ActualTelescopeStates
          .BETWEEN_MAX_RETRACT_AND_MAX_EXTENSION -> {
          LEDConstants.CandleState.CLIMBING
        }
        TelescopingClimberConstants.ActualTelescopeStates.MAX_RETRACT -> {
          when (telescopingClimber.desiredState) {
            TelescopingClimberConstants.DesiredTelescopeStates.MAX_RETRACT -> {
              if (climbDone) {
                if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
                  LEDConstants.CandleState.CLIMBING
                } else {
                  LEDConstants.CandleState.CLIMBING
                }
              } else {
                LEDConstants.CandleState.CLIMBER_READY
              }
            }
            else -> LEDConstants.CandleState.IDLE
          }
        }
        TelescopingClimberConstants.ActualTelescopeStates.MAX_EXTENSION -> {
          when (telescopingClimber.desiredState) {
            TelescopingClimberConstants.DesiredTelescopeStates.MAX_EXTENSION -> {
              when (pneumatic.allowClimb) {
                PneumaticConstants.AllowClimb.CLIMB -> LEDConstants.CandleState.CLIMBER_READY
                PneumaticConstants.AllowClimb.NO_CLIMB -> LEDConstants.CandleState.DISALLOW_CLIMB
              }
            }
            else -> LEDConstants.CandleState.IDLE
          }
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
