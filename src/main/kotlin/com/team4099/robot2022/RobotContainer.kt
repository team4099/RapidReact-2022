package com.team4099.robot2022

import com.team4099.robot2022.config.ControlBoard
import com.team4099.robot2022.config.constants.Constants

object RobotContainer {

  init {
    if (Constants.Tuning.type == Constants.Tuning.RobotType.REAL) {} else {}
  }

  fun zeroSteering() {}

  fun mapDefaultCommands() {
    // TODO map default commands
  }

  fun zeroSensors() {}

  fun setDriveCoastMode() {}

  fun setDriveBrakeMode() {}

  fun mapTeleopControls() {}

  fun mapTestControls() {}

  fun getAutonomousCommand() {}

  fun logOperatorController() = ControlBoard.logOperatorController()
  fun logDriverController() = ControlBoard.logDriverController()
}
