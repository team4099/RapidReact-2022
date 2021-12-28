package com.team4099.robot2022.auto

import com.pathplanner.lib.PathPlanner
import com.team4099.lib.pathfollow.Trajectory
import com.team4099.lib.pathfollow.TrajectoryConfig
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.inMetersPerSecondPerSecond
import com.team4099.robot2022.commands.drivetrain.AutoDriveCommand
import com.team4099.robot2022.config.Constants

object PathStore {
  private val trajectoryConfig =
    TrajectoryConfig(
      Constants.Drivetrain.SLOW_AUTO_VEL,
      Constants.Drivetrain.MAX_AUTO_ACCEL,
      Constants.Drivetrain.MAX_AUTO_ANGULAR_VEL,
      Constants.Drivetrain.MAX_AUTO_ANGULAR_ACCEL)

  private val slowTrajectoryConfig =
    TrajectoryConfig(
      Constants.Drivetrain.SLOW_AUTO_VEL,
      Constants.Drivetrain.MAX_AUTO_ACCEL,
      Constants.Drivetrain.MAX_AUTO_ANGULAR_VEL,
      Constants.Drivetrain.MAX_AUTO_ANGULAR_ACCEL)


  val examplePats = PathPlanner.loadPath("testPath1.path",Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
    Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond) as Trajectory

  val squarepath = AutoDriveCommand(examplePats)
}
