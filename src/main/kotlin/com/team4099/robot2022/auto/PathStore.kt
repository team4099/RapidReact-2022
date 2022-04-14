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
      DrivetrainConstants.SLOW_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val blueTwoBallLeftStartPath =
    PathPlanner.loadPath(
      "2 Ball Left Start Blue",
      DrivetrainConstants.SLOW_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.SLOW_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val blueThreeBallRightStartPath =
    PathPlanner.loadPath(
      "3 Ball Right Start Blue",
      DrivetrainConstants.SLOW_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.SLOW_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val blueThreeBallRightStartFasterPath =
    PathPlanner.loadPath(
      "3 Ball Right Start Faster Blue",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val blueThreeAndFourBallLeftStartPath =
    PathPlanner.loadPath(
      "3 and 4 Ball Left Start Blue",
      DrivetrainConstants.SLOW_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.SLOW_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val blueTwoBallRightStartPath =
    PathPlanner.loadPath(
      "2 Ball Right Start Blue",
      DrivetrainConstants.SLOW_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.SLOW_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val blueFourBallRightStartPath =
    PathPlanner.loadPath(
      "4 Ball Right Start Blue",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val blueFiveBallRightStart =
    PathPlanner.loadPath(
      "4 and 5 Ball Blue",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val redTwoBallLeftStartPath =
    PathPlanner.loadPath(
      "2 Ball Left Start Red",
      DrivetrainConstants.SLOW_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.SLOW_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val redThreeBallRightStartPath =
    PathPlanner.loadPath(
      "3 Ball Right Start Red",
      DrivetrainConstants.SLOW_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.SLOW_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val redThreeBallRightStartFasterPath =
    PathPlanner.loadPath(
      "3 Ball Right Start Faster Red",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val redThreeAndFourBallLeftStartPath =
    PathPlanner.loadPath(
      "3 and 4 Ball Left Start Red",
      DrivetrainConstants.SLOW_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.SLOW_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val redTwoBallRightStartPath =
    PathPlanner.loadPath(
      "2 Ball Right Start Red",
      DrivetrainConstants.SLOW_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.SLOW_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val redFourBallRightStartPath =
    PathPlanner.loadPath(
      "4 Ball Right Start Red",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val redFiveBallRightStart =
    PathPlanner.loadPath(
      "4 and 5 Ball Red",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val blueOneBallLeftTarmacLeftBump =
    PathPlanner.loadPath(
      "1 Ball Left Tarmac Left Bump Blue",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val redOneBallLeftTarmacLeftBump =
    PathPlanner.loadPath(
      "1 Ball Left Tarmac Left Bump Red",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val blueOneBallLeftTarmacLeftShoot =
    PathPlanner.loadPath(
      "1 Ball Left Tarmac Left Shoot Path Blue",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val redOneBallLeftTarmacLeftShoot =
    PathPlanner.loadPath(
      "1 Ball Left Tarmac Left Shoot Path Red",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val blueOneBallLeftTarmacRightBump =
    PathPlanner.loadPath(
      "1 Ball Left Tarmac Right Bump Blue",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val redOneBallLeftTarmacRightBump =
    PathPlanner.loadPath(
      "1 Ball Left Tarmac Right Bump Red",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val blueOneBallLeftTarmacRightShoot =
    PathPlanner.loadPath(
      "1 Ball Left Tarmac Right Shoot Path Blue",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val redOneBallLeftTarmacRightShoot =
    PathPlanner.loadPath(
      "1 Ball Left Tarmac Right Shoot Path Red",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val blueTwoBallLeftFender =
    PathPlanner.loadPath(
      "2 Ball Left From Fender Blue",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val redTwoBallLeftFender =
    PathPlanner.loadPath(
      "2 Ball Left From Fender Red",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val blueOneBallFenderShotThenTaxi =
    PathPlanner.loadPath(
      "1 Ball Left Fender Then Taxi Blue",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val redOneBallFenderShotThenTaxi =
    PathPlanner.loadPath(
      "1 Ball Left Fender Then Taxi Red",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )
}
