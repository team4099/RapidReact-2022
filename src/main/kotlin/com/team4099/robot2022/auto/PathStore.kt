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

  val twoBallLeftStartPath =
    PathPlanner.loadPath(
      "2 Ball Left Start",
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

  //  val blueOneBallLeftTarmacLeftBump =
  //    PathPlanner.loadPath(
  //      "1 Ball Left Tarmac Left Bump Blue",
  //      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
  //      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
  //    )
  //
  val oneBallLeftTarmacLeftBump =
    PathPlanner.loadPath(
      "1 Ball Left Tarmac Left Bump",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )
  //
  //  val blueOneBallLeftTarmacLeftShoot =
  //    PathPlanner.loadPath(
  //      "1 Ball Left Tarmac Left Shoot Path Blue",
  //      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
  //      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
  //    )

  val oneBallLeftTarmacLeftShoot =
    PathPlanner.loadPath(
      "1 Ball Left Tarmac Left Shoot Path",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  //  val blueOneBallLeftTarmacRightBump =
  //    PathPlanner.loadPath(
  //      "1 Ball Left Tarmac Right Bump Blue",
  //      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
  //      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
  //    )
  //
  //  val oneBallLeftTarmacRightBump =
  //    PathPlanner.loadPath(
  //      "1 Ball Left Tarmac Right Bump",
  //      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
  //      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
  //    )
  //
  //  val blueOneBallLeftTarmacRightShoot =
  //    PathPlanner.loadPath(
  //      "1 Ball Left Tarmac Right Shoot Path Blue",
  //      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
  //      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
  //    )
  //
  //  val oneBallLeftTarmacRightShoot =
  //    PathPlanner.loadPath(
  //      "1 Ball Left Tarmac Right Shoot Path",
  //      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
  //      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
  //    )
  //
  //  val blueTwoBallLeftFender =
  //    PathPlanner.loadPath(
  //      "2 Ball Left From Fender Blue",
  //      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
  //      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
  //    )
  //
  val twoBallLeftFender =
    PathPlanner.loadPath(
      "2 Ball Left From Fender",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  //  val bluePickUpOpponentCargoPath =
  //    PathPlanner.loadPath(
  //      "1 or 2 Ball Left Tarmac Outtake Opp Cargo Blue",
  //      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
  //      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
  //    )

  val pickUpBothOpponentCargoPath =
    PathPlanner.loadPath(
      "1 or 2 Ball Left Tarmac Outtake Opp Cargo",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val oneBallStealTrajectory =
    PathPlanner.loadPath(
      "1 Ball Left Tarmac Left Outtake Opponent",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val oneBallShootTrajectory =
    PathPlanner.loadPath(
      "1 Ball Left Tarmac Left Outtake Shoot",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val twoBallEjectOneTrajectory =
    PathPlanner.loadPath(
      "2 Ball Eject 1 Ball",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val oneBallEjectOneTrajectory =
    PathPlanner.loadPath(
      "1 Ball Left Fender Then Eject Opp Cargo",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val oneBallRightEjectOneTrajectory =
    PathPlanner.loadPath(
      "1 Ball Left Tarmac Right Eject",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val oneBallRightShootTrajectory =
    PathPlanner.loadPath(
      "1 Ball Left Tarmac Right Shoot",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val twoBallRightShootTrajectory =
    PathPlanner.loadPath(
      "2 Ball Left Tarmac Right Shoot",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )

  val oneBallFenderThenTaxi =
    PathPlanner.loadPath(
      "1 Ball Fender Then Taxi",
      DrivetrainConstants.MAX_AUTO_VEL.inMetersPerSecond,
      DrivetrainConstants.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond
    )
}
