package com.team4099.robot2022.auto

import com.team4099.lib.units.derived.Angle
import com.team4099.lib.units.derived.degrees
import com.team4099.robot2022.auto.mode.TestAutoPath
import com.team4099.robot2022.commands.climber.PivotCharacterizationCommand
import com.team4099.robot2022.commands.climber.TelescopingCharacterizationCommand
import com.team4099.robot2022.commands.drivetrain.DriveCharacterizeCommand
import com.team4099.robot2022.commands.shooter.ShooterCharacterizeCommand
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.InstantCommand

object AutonomousSelector {
  private var orientationChooser: SendableChooser<Angle> = SendableChooser()
  private var autonomousModeChooser: SendableChooser<AutonomousMode> = SendableChooser()

  init {
    val autoTab = Shuffleboard.getTab("Auto settings")
    orientationChooser.setDefaultOption("Forward", 0.degrees)
    orientationChooser.addOption("Backwards", 180.degrees)
    orientationChooser.addOption("Left", 90.degrees)
    orientationChooser.addOption("Right", 270.degrees)
    autoTab.add("Starting Orientation", orientationChooser)
    autonomousModeChooser.addOption("Test", AutonomousMode.TEST_AUTO_PATH)
    autonomousModeChooser.addOption(
        "Characterize Drivetrain", AutonomousMode.CHARACTERIZE_DRIVETRAIN)
    autonomousModeChooser.addOption("Characterize Shooter", AutonomousMode.CHARACTERIZE_SHOOTER)
    autoTab.add("Mode", autonomousModeChooser)
  }

  fun getCommand(): CommandBase {
    val mode = autonomousModeChooser.selected
    when (mode) {
      AutonomousMode.TEST_AUTO_PATH -> return TestAutoPath()
      AutonomousMode.CHARACTERIZE_DRIVETRAIN -> return DriveCharacterizeCommand()
      AutonomousMode.CHARACTERIZE_SHOOTER -> return ShooterCharacterizeCommand()
      AutonomousMode.CHARACTERIZE_CLIMBER_TELESCOPE -> return TelescopingCharacterizationCommand()
      AutonomousMode.CHARACTERIZE_CLIMBER_PIVOT -> return PivotCharacterizationCommand()
      else -> println("ERROR: unexpected auto mode: $mode")
    }
    return InstantCommand()
  }

  private enum class AutonomousMode {
    TEST_AUTO_PATH,
    CHARACTERIZE_DRIVETRAIN,
    CHARACTERIZE_SHOOTER,
    CHARACTERIZE_CLIMBER_TELESCOPE,
    CHARACTERIZE_CLIMBER_PIVOT
  }
}
