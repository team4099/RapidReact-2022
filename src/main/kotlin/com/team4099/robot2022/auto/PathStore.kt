package com.team4099.robot2022.auto

import com.pathplanner.lib.PathPlanner
import com.team4099.lib.pathfollow.TrajectoryConfig
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.inMetersPerSecondPerSecond
import com.team4099.robot2022.config.constants.DrivetrainConstants

object PathStore {
  private val trajectoryConfig =
      TrajectoryConfig(
          DrivetrainConstants.SLOW_AUTO_VEL,
          DrivetrainConstants.MAX_AUTO_ACCEL,
          DrivetrainConstants.PID.MAX_AUTO_ANGULAR_VEL,
          DrivetrainConstants.PID.MAX_AUTO_ANGULAR_ACCEL)

  private val slowTrajectoryConfig =
      TrajectoryConfig(
          DrivetrainConstants.SLOW_AUTO_VEL,
          DrivetrainConstants.MAX_AUTO_ACCEL,
          DrivetrainConstants.PID.MAX_AUTO_ANGULAR_VEL,
          DrivetrainConstants.PID.MAX_AUTO_ANGULAR_ACCEL)

  val testAutoPath =
      PathPlanner.loadPath(
          "strafeRight",
          DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
          DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)

  val twoBallLeftStartPath =
      PathPlanner.loadPath(
          "2 Ball Left Start",
          DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
          DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)

  val threeBallRightStartPath =
      PathPlanner.loadPath(
          "3 Ball Right Start",
          DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
          DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)

  val threeAndFourBallLeftStartPath =
      PathPlanner.loadPath(
          "3 and 4 Ball Left Start",
          DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
          DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
}
