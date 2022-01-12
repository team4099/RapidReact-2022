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

  val bottomLeftCargoPath =
      PathPlanner.loadPath(
          "topRightTaxi",
          Constants.Drivetrain.SLOW_AUTO_VEL.inMetersPerSecond,
          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)

//  val bottomLeftReturnShootPath =
//      PathPlanner.loadPath(
//          "bottomLeftReturnShoot.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//  val bottomLeftTaxiPath =
//      PathPlanner.loadPath(
//          "bottomLeftTaxi.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//
//  val bottomRightCargoPath =
//      PathPlanner.loadPath(
//          "bottomRightCargo.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//  val bottomRightReturnShootPath =
//      PathPlanner.loadPath(
//          "bottomRightReturnShoot.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//  val bottomRightTaxiPath =
//      PathPlanner.loadPath(
//          "bottomRight Taxi.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//
//  val topLeftCargoPath =
//      PathPlanner.loadPath(
//          "topLeftCargo.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//  val topLeftReturnShootPath =
//      PathPlanner.loadPath(
//          "topLeftReturnShoot.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//  val topLeftTaxiPath =
//      PathPlanner.loadPath(
//          "bottomLeftTaxi.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//
//  val topRightCargoPath =
//      PathPlanner.loadPath(
//          "topRightCargo.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//  val topRightReturnShootPath =
//      PathPlanner.loadPath(
//          "topRightReturnShoot.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//  val topRightTaxiPath =
//      PathPlanner.loadPath(
//          "topRightTaxi.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//
//  val spinPath =
//      PathPlanner.loadPath(
//          "spin.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//  val spin2Path =
//      PathPlanner.loadPath(
//          "spin2.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//  val spiral =
//      PathPlanner.loadPath(
//          "sprial.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//  val turn45 =
//      PathPlanner.loadPath(
//          "turn45.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//  val turn90 =
//      PathPlanner.loadPath(
//          "turn90.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//
//  val test1Path =
//      PathPlanner.loadPath(
//          "test1.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//  val test2Path =
//      PathPlanner.loadPath(
//          "test2.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//  val test4Path =
//      PathPlanner.loadPath(
//          "test4.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
//  val test5Path =
//      PathPlanner.loadPath(
//          "test5.path",
//          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
//          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
}
