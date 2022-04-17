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
import com.team4099.robot2022.subsystems.led.BlinkinLed
import com.team4099.robot2022.subsystems.pneumatics.Pneumatic
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj2.command.CommandBase

class BlinkinLedCommand(
  val led: BlinkinLed,
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
                     LEDConstants.BlinkinLEDState.RED_SHOOT
                   } else {
                     LEDConstants.BlinkinLEDState.BLUE_SHOOT
                   }
                 }
                else -> {*/
              if (Robot.isAutonomous) {
                LEDConstants.BlinkinLEDState.AUTO
              } else if (intake.keepIntakingLEDState) {
                when (intake.rollerState) {
                  IntakeConstants.RollerState.OUT -> LEDConstants.BlinkinLEDState.OUTTAKING
                  IntakeConstants.RollerState.IN -> LEDConstants.BlinkinLEDState.INTAKING
                  else -> LEDConstants.BlinkinLEDState.IDLE
                }
              } else {
                when (feeder.ballCount) {
                  0 -> LEDConstants.BlinkinLEDState.STANDING_ZERO
                  1 -> LEDConstants.BlinkinLEDState.STANDING_ONE
                  2 -> LEDConstants.BlinkinLEDState.STANDING_TWO
                  else -> LEDConstants.BlinkinLEDState.IDLE
                }
              }
              //               }
              //            }
            }
            else -> LEDConstants.BlinkinLEDState.IDLE
          }
        }
        TelescopingClimberConstants.ActualTelescopeStates.BETWEEN_START_AND_MAX_RETRACT,
        TelescopingClimberConstants.ActualTelescopeStates
          .BETWEEN_MAX_RETRACT_AND_MAX_EXTENSION -> {
          LEDConstants.BlinkinLEDState.CLIMBING
        }
        TelescopingClimberConstants.ActualTelescopeStates.MAX_RETRACT -> {
          when (telescopingClimber.desiredState) {
            TelescopingClimberConstants.DesiredTelescopeStates.MAX_RETRACT -> {
              if (climbDone) {
                if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
                  LEDConstants.BlinkinLEDState.CLIMB_FINISHED_RED_ALLIANCE
                } else {
                  LEDConstants.BlinkinLEDState.CLIMB_FINISHED_BLUE_ALLIANCE
                }
              } else {
                LEDConstants.BlinkinLEDState.CLIMBER_READY
              }
            }
            else -> LEDConstants.BlinkinLEDState.IDLE
          }
        }
        TelescopingClimberConstants.ActualTelescopeStates.MAX_EXTENSION -> {
          when (telescopingClimber.desiredState) {
            TelescopingClimberConstants.DesiredTelescopeStates.MAX_EXTENSION -> {
              when (pneumatic.allowClimb) {
                PneumaticConstants.AllowClimb.CLIMB -> LEDConstants.BlinkinLEDState.CLIMBER_READY
                PneumaticConstants.AllowClimb.NO_CLIMB ->
                  LEDConstants.BlinkinLEDState.DISALLOW_CLIMB
              }
            }
            else -> LEDConstants.BlinkinLEDState.IDLE
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
