package com.team4099.robot2022.auto

import com.team4099.lib.pathfollow.trajectoryFromPathfinder
import com.team4099.robot2022.commands.drivetrain.AutoDriveCommand
import com.team4099.robot2022.subsystems.Drivetrain
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj2.command.Command

class AutonomousSelector {
  companion object {
    private var orientationChooser: SendableChooser<Rotation2d>? = null
    private var autonomousModeChooser: SendableChooser<AutonomousMode>? = null

    init {
      val autoTab = Shuffleboard.getTab("Auto settings")
      orientationChooser = SendableChooser()
      orientationChooser!!.setDefaultOption("Forward", Rotation2d.fromDegrees(0.0))
      orientationChooser!!.addOption("Backwards", Rotation2d.fromDegrees(180.0))
      orientationChooser!!.addOption("Left", Rotation2d.fromDegrees(90.0))
      orientationChooser!!.addOption("Right", Rotation2d.fromDegrees(270.0))
      autoTab.add("Starting Orientation", orientationChooser)
      autonomousModeChooser = SendableChooser()
      autonomousModeChooser!!.addOption("Test", AutonomousMode.TEST_AUTO_PATH)
      autoTab.add("Mode", autonomousModeChooser)
    }
  }

  fun getCommand(drivetrain: Drivetrain?): Command? {
    val mode = autonomousModeChooser!!.selected
    when (mode) {
      AutonomousMode.TEST_AUTO_PATH ->
          return AutoDriveCommand(trajectoryFromPathfinder(PathStore.testAutoPath))
      else -> println("ERROR: unexpected auto mode: $mode")
    }
    return null
  }

  private enum class AutonomousMode {
    TEST_AUTO_PATH
  }
}
