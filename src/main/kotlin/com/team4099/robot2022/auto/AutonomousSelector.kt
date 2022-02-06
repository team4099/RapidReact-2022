package com.team4099.robot2022.auto

import com.team4099.lib.pathfollow.trajectoryFromPathPlanner
import com.team4099.robot2022.commands.drivetrain.AutoDriveCommand
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.InstantCommand

object AutonomousSelector {
  private var orientationChooser: SendableChooser<Rotation2d> = SendableChooser()
  private var autonomousModeChooser: SendableChooser<AutonomousMode> = SendableChooser()

  init {
    val autoTab = Shuffleboard.getTab("Auto settings")
    orientationChooser.setDefaultOption("Forward", Rotation2d.fromDegrees(0.0))
    orientationChooser.addOption("Backwards", Rotation2d.fromDegrees(180.0))
    orientationChooser.addOption("Left", Rotation2d.fromDegrees(90.0))
    orientationChooser.addOption("Right", Rotation2d.fromDegrees(270.0))
    autoTab.add("Starting Orientation", orientationChooser)
    autonomousModeChooser.addOption("Test", AutonomousMode.TEST_AUTO_PATH)
    autoTab.add("Mode", autonomousModeChooser)
  }

  fun getCommand(): CommandBase {
    val mode = autonomousModeChooser.selected
    when (mode) {
      AutonomousMode.TEST_AUTO_PATH ->
          return AutoDriveCommand(trajectoryFromPathPlanner(PathStore.testAutoPath))
      else -> println("ERROR: unexpected auto mode: $mode")
    }
    return InstantCommand()
  }

  private enum class AutonomousMode {
    TEST_AUTO_PATH
  }
}
