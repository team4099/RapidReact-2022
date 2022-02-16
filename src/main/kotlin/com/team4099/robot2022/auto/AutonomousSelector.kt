package com.team4099.robot2022.auto

import com.team4099.lib.units.derived.Angle
import com.team4099.lib.units.derived.degrees
import com.team4099.robot2022.auto.mode.TestAutoPath
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
    autoTab.add("Mode", autonomousModeChooser)
  }

  fun getCommand(): CommandBase {
    val mode = autonomousModeChooser.selected
    when (mode) {
      AutonomousMode.TEST_AUTO_PATH -> return TestAutoPath()
      else -> println("ERROR: unexpected auto mode: $mode")
    }
    return InstantCommand()
  }

  private enum class AutonomousMode {
    TEST_AUTO_PATH
  }
}
