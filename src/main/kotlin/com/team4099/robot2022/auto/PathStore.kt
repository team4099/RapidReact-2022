package com.team4099.robot2022.auto

import com.pathplanner.lib.PathPlanner
import com.team4099.lib.pathfollow.TrajectoryConfig
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.inMetersPerSecondPerSecond
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

  val testAutoPath =
      PathPlanner.loadPath(
          "testpath",
          Constants.Drivetrain.SLOW_AUTO_VEL.inMetersPerSecond,
          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
}
