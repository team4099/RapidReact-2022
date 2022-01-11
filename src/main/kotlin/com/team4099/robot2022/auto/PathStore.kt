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
          "bottomLeftCargo.path",
          Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
          Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
  val bottomLeftReturnShootPath =
    PathPlanner.loadPath(
      "bottomLeftReturnShoot.path",
      Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
      Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
  val bottomLeftTaxiPath =
    PathPlanner.loadPath(
      "bottomLeftTaxi.path",
      Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
      Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)


  val bottomRightCargoPath =
    PathPlanner.loadPath(
      "bottomRightCargo.path",
      Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
      Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
  val bottomRightReturnShootPath =
    PathPlanner.loadPath(
      "bottomRightReturnShoot.path",
      Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
      Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
  val bottomRightTaxiPath =
    PathPlanner.loadPath(
      "bottomRight Taxi.path",
      Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
      Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)


  val topLeftCargoPath =
    PathPlanner.loadPath(
      "topLeftCargo.path",
      Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
      Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
  val topLeftReturnShootPath =
    PathPlanner.loadPath(
      "topLeftReturnShoot.path",
      Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
      Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
  val topLeftTaxiPath =
    PathPlanner.loadPath(
      "bottomLeftTaxi.path",
      Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
      Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)


  val topRightCargoPath =
    PathPlanner.loadPath(
      "topRightCargo.path",
      Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
      Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
  val topRightReturnShootPath =
    PathPlanner.loadPath(
      "topRightReturnShoot.path",
      Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
      Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
  val topRightTaxiPath =
    PathPlanner.loadPath(
      "topRightTaxi.path",
      Constants.Drivetrain.MAX_AUTO_VEL.inMetersPerSecond,
      Constants.Drivetrain.MAX_AUTO_ACCEL.inMetersPerSecondPerSecond)
}
