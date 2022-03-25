package com.team4099.robot2022.auto

import com.pathplanner.lib.PathPlanner
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.inMetersPerSecondPerSecond
import com.team4099.robot2022.config.constants.DrivetrainConstants

object PathStore {

  val testAutoPath =
      PathPlanner.loadPath(
          "strafeRight",
          DrivetrainConstants.SLOW_AUTO_VEL.inMetersPerSecond,
          DrivetrainConstants.SLOW_AUTO_ACCEL.inMetersPerSecondPerSecond)

  val twoBallLeftStartPath =
      PathPlanner.loadPath(
          "2 Ball Left Start",
          DrivetrainConstants.SLOW_AUTO_VEL.inMetersPerSecond,
          DrivetrainConstants.SLOW_AUTO_ACCEL.inMetersPerSecondPerSecond)

  val threeBallRightStartPath =
      PathPlanner.loadPath(
          "3 Ball Right Start",
          DrivetrainConstants.SLOW_AUTO_VEL.inMetersPerSecond,
          DrivetrainConstants.SLOW_AUTO_ACCEL.inMetersPerSecondPerSecond)

  val threeAndFourBallLeftStartPath =
      PathPlanner.loadPath(
          "3 and 4 Ball Left Start",
          DrivetrainConstants.SLOW_AUTO_VEL.inMetersPerSecond,
          DrivetrainConstants.SLOW_AUTO_ACCEL.inMetersPerSecondPerSecond)
}
