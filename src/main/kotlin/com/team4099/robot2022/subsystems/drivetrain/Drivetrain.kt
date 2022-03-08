package com.team4099.robot2022.subsystems.drivetrain

import com.team4099.lib.geometry.Pose
import com.team4099.lib.geometry.Translation
import com.team4099.lib.units.AngularAcceleration
import com.team4099.lib.units.AngularVelocity
import com.team4099.lib.units.LinearAcceleration
import com.team4099.lib.units.LinearVelocity
import com.team4099.lib.units.base.feet
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.base.inches
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.derived.Angle
import com.team4099.lib.units.derived.cos
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.derived.inRotation2ds
import com.team4099.lib.units.derived.radians
import com.team4099.lib.units.derived.sin
import com.team4099.lib.units.derived.times
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.perSecond
import com.team4099.robot2022.config.constants.DrivetrainConstants
import edu.wpi.first.math.kinematics.SwerveDriveKinematics
import edu.wpi.first.math.kinematics.SwerveDriveOdometry
import edu.wpi.first.math.kinematics.SwerveModuleState
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class Drivetrain(val io: DrivetrainIO) : SubsystemBase() {
  val inputs = DrivetrainIO.DrivetrainIOInputs()

  val swerveModules = io.getSwerveModules()

  init {
    // Wheel speeds
    zeroSteering()
  }

  private val wheelSpeeds =
      mutableListOf(0.feet.perSecond, 0.feet.perSecond, 0.feet.perSecond, 0.feet.perSecond)

  private val wheelAngles = mutableListOf(0.radians, 0.radians, 0.radians, 0.radians)

  private val wheelAccelerations =
      mutableListOf(
          0.feet.perSecond.perSecond,
          0.feet.perSecond.perSecond,
          0.feet.perSecond.perSecond,
          0.feet.perSecond.perSecond)

  private val frontLeftWheelLocation =
      Translation(
          -DrivetrainConstants.DRIVETRAIN_WIDTH / 2, DrivetrainConstants.DRIVETRAIN_LENGTH / 2)
  private val frontRightWheelLocation =
      Translation(
          DrivetrainConstants.DRIVETRAIN_WIDTH / 2, DrivetrainConstants.DRIVETRAIN_LENGTH / 2)
  private val backLeftWheelLocation =
      Translation(
          -DrivetrainConstants.DRIVETRAIN_WIDTH / 2, -DrivetrainConstants.DRIVETRAIN_LENGTH / 2)
  private val backRightWheelLocation =
      Translation(
          DrivetrainConstants.DRIVETRAIN_WIDTH / 2, -DrivetrainConstants.DRIVETRAIN_LENGTH / 2)

  val swerveDriveKinematics =
      SwerveDriveKinematics(
          frontLeftWheelLocation.translation2d,
          frontRightWheelLocation.translation2d,
          backLeftWheelLocation.translation2d,
          backRightWheelLocation.translation2d)

  private val swerveDriveOdometry =
      SwerveDriveOdometry(
          swerveDriveKinematics,
          inputs.gyroAngle.inRotation2ds,
          Pose(0.meters, 0.meters, 0.degrees).pose2d)

  var pose: Pose
    get() = Pose(swerveDriveOdometry.poseMeters)
    set(value) {
      swerveDriveOdometry.resetPosition(value.pose2d, inputs.gyroAngle.inRotation2ds)
      zeroGyro(pose.theta)
    }

  override fun periodic() {
    io.updateInputs(inputs)
    swerveModules.forEach { it.periodic() }
    updateOdometry()

    Logger.getInstance().processInputs("Drivetrain", inputs)

    Logger.getInstance()
        .recordOutput("Drivetrain/frontLeftSpeedMetersPerSecond", wheelSpeeds[0].inMetersPerSecond)
    Logger.getInstance()
        .recordOutput("Drivetrain/frontRightSpeedMetersPerSecond", wheelSpeeds[1].inMetersPerSecond)
    Logger.getInstance()
        .recordOutput("Drivetrain/backLeftSpeedMetersPerSecond", wheelSpeeds[2].inMetersPerSecond)
    Logger.getInstance()
        .recordOutput("Drivetrain/backRightSpeedMetersPerSecond", wheelSpeeds[3].inMetersPerSecond)

    // Wheel angles
    Logger.getInstance().recordOutput("Drivetrain/frontLeftAngleRadians", wheelAngles[0].inRadians)
    Logger.getInstance().recordOutput("Drivetrain/frontRightAngleRadians", wheelAngles[1].inRadians)
    Logger.getInstance().recordOutput("Drivetrain/backLeftAngleRadians", wheelAngles[2].inRadians)
    Logger.getInstance().recordOutput("Drivetrain/backRightAngleRadians", wheelAngles[3].inRadians)

    Logger.getInstance().recordOutput("Drivetrain/poseXMeters", pose.x.inMeters)
    Logger.getInstance().recordOutput("Drivetrain/poseYMeters", pose.y.inMeters)
    Logger.getInstance().recordOutput("Drivetrain/poseThetaRadians", pose.theta.inRadians)
  }

  /**
   * Sets the drivetrain to the specified angular and X & Y velocities based on the current angular
   * and linear acceleration. Calculates both angular and linear velocities and acceleration and
   * calls set for each SwerveModule object.
   *
   * @param angularVelocity The angular velocity of a specified drive
   * @param driveVector.first The linear velocity on the X axis
   * @param driveVector.second The linear velocity on the Y axis
   * @param angularAcceleration The angular acceleration of a specified drive
   * @param driveAcceleration.first The linear acceleration on the X axis
   * @param driveAcceleration.second The linear acceleration on the Y axis
   */
  fun set(
    angularVelocity: AngularVelocity,
    driveVector: Pair<LinearVelocity, LinearVelocity>,
    fieldOriented: Boolean = true,
    angularAcceleration: AngularAcceleration = 0.0.radians.perSecond.perSecond,
    driveAcceleration: Pair<LinearAcceleration, LinearAcceleration> =
        Pair(0.0.meters.perSecond.perSecond, 0.0.meters.perSecond.perSecond)
  ) {

    // Logger.addEvent("Drivetrain", "setting with $driveVector and $angularVelocity")
    //    Logger.addEvent("Drivetrain", "gyro angle: ${(-gyroAngle).inDegrees}")
    val vX =
        if (fieldOriented) {
          driveVector.first * (-inputs.gyroAngle).cos - driveVector.second * (-inputs.gyroAngle).sin
        } else {
          driveVector.first
        }
    val vY =
        if (fieldOriented) {
          driveVector.second * (-inputs.gyroAngle).cos + driveVector.first * (-inputs.gyroAngle).sin
        } else {
          driveVector.second
        }

    val aY =
        if (fieldOriented) {
          driveAcceleration.second * (-inputs.gyroAngle).cos +
              driveAcceleration.first * (-inputs.gyroAngle).sin
        } else {
          driveAcceleration.second
        }
    val aX =
        if (fieldOriented) {
          driveAcceleration.first * (-inputs.gyroAngle).cos -
              driveAcceleration.second * (-inputs.gyroAngle).sin
        } else {
          driveAcceleration.first
        }

    val a = vX + angularVelocity * DrivetrainConstants.DRIVETRAIN_LENGTH / 2
    val b = vX - angularVelocity * DrivetrainConstants.DRIVETRAIN_LENGTH / 2
    val c = vY + angularVelocity * DrivetrainConstants.DRIVETRAIN_WIDTH / 2
    val d = vY - angularVelocity * DrivetrainConstants.DRIVETRAIN_WIDTH / 2
    // Logger.addEvent("Drivetrain", "vX: $vX, angular velocity: $angularVelocity")

    wheelSpeeds[0] = hypot(b, d)
    wheelSpeeds[1] = hypot(b, c)
    wheelSpeeds[2] = hypot(a, d)
    wheelSpeeds[3] = hypot(a, c)

    val aA =
        aX +
            (angularAcceleration.value * DrivetrainConstants.DRIVETRAIN_LENGTH.value / 2).inches
                .perSecond
                .perSecond
    val aB =
        aX -
            (angularAcceleration.value * DrivetrainConstants.DRIVETRAIN_LENGTH.value / 2).inches
                .perSecond
                .perSecond
    val aC =
        aY +
            (angularAcceleration.value * DrivetrainConstants.DRIVETRAIN_WIDTH.value / 2).inches
                .perSecond
                .perSecond
    val aD =
        aY -
            (angularAcceleration.value * DrivetrainConstants.DRIVETRAIN_WIDTH.value / 2).inches
                .perSecond
                .perSecond

    wheelAccelerations[0] = kotlin.math.hypot(aB.value, aD.value).feet.perSecond.perSecond
    wheelAccelerations[1] = kotlin.math.hypot(aB.value, aC.value).feet.perSecond.perSecond
    wheelAccelerations[2] = kotlin.math.hypot(aA.value, aD.value).feet.perSecond.perSecond
    wheelAccelerations[3] = kotlin.math.hypot(aA.value, aC.value).feet.perSecond.perSecond

    val maxWheelSpeed = wheelSpeeds.maxOrNull()
    if (maxWheelSpeed != null && maxWheelSpeed > DrivetrainConstants.DRIVE_SETPOINT_MAX) {
      for (i in 0 until DrivetrainConstants.WHEEL_COUNT) {
        wheelSpeeds[i] = wheelSpeeds[i] / maxWheelSpeed.inMetersPerSecond
      }
    }
    wheelAngles[0] = atan2(b, d)
    wheelAngles[1] = atan2(b, c)
    wheelAngles[2] = atan2(a, d)
    wheelAngles[3] = atan2(a, c)
    //    Logger.addEvent("Drivetrain", "wheel angle: $wheelAngles")

    swerveModules[0].set(wheelAngles[0], wheelSpeeds[0], wheelAccelerations[0])
    swerveModules[1].set(wheelAngles[1], wheelSpeeds[1], wheelAccelerations[1])
    swerveModules[2].set(wheelAngles[2], wheelSpeeds[2], wheelAccelerations[2])
    swerveModules[3].set(wheelAngles[3], wheelSpeeds[3], wheelAccelerations[3])
  }

  fun setOpenLoop(
    angularVelocity: AngularVelocity,
    driveVector: Pair<LinearVelocity, LinearVelocity>,
    fieldOriented: Boolean = true
  ) {
    // Logger.addEvent("Drivetrain", "setting open loop with $driveVector and $angularVelocity")

    val vX =
        if (fieldOriented) {
          driveVector.first * (-inputs.gyroAngle).cos - driveVector.second * (-inputs.gyroAngle).sin
        } else {
          driveVector.first
        }
    val vY =
        if (fieldOriented) {
          driveVector.second * (-inputs.gyroAngle).cos + driveVector.first * (-inputs.gyroAngle).sin
        } else {
          driveVector.second
        }

    val a = vX + angularVelocity * DrivetrainConstants.DRIVETRAIN_LENGTH / 2
    val b = vX - angularVelocity * DrivetrainConstants.DRIVETRAIN_LENGTH / 2
    val c = vY + angularVelocity * DrivetrainConstants.DRIVETRAIN_WIDTH / 2
    val d = vY - angularVelocity * DrivetrainConstants.DRIVETRAIN_WIDTH / 2

    wheelSpeeds[0] = hypot(b, d)
    wheelSpeeds[1] = hypot(b, c)
    wheelSpeeds[2] = hypot(a, d)
    wheelSpeeds[3] = hypot(a, c)

    val maxWheelSpeed = wheelSpeeds.maxOrNull()
    if (maxWheelSpeed != null && maxWheelSpeed > DrivetrainConstants.DRIVE_SETPOINT_MAX) {
      for (i in 0 until DrivetrainConstants.WHEEL_COUNT) {
        wheelSpeeds[i] =
            wheelSpeeds[i] / maxWheelSpeed.inMetersPerSecond *
                DrivetrainConstants.DRIVE_SETPOINT_MAX.inMetersPerSecond
      }
    }
    wheelAngles[0] = atan2(b, d)
    wheelAngles[1] = atan2(b, c)
    wheelAngles[2] = atan2(a, d)
    wheelAngles[3] = atan2(a, c)

    swerveModules[0].setOpenLoop(
        wheelAngles[0], wheelSpeeds[0] / DrivetrainConstants.DRIVE_SETPOINT_MAX)
    swerveModules[1].setOpenLoop(
        wheelAngles[1], wheelSpeeds[1] / DrivetrainConstants.DRIVE_SETPOINT_MAX)
    swerveModules[2].setOpenLoop(
        wheelAngles[2], wheelSpeeds[2] / DrivetrainConstants.DRIVE_SETPOINT_MAX)
    swerveModules[3].setOpenLoop(
        wheelAngles[3], wheelSpeeds[3] / DrivetrainConstants.DRIVE_SETPOINT_MAX)
  }

  private fun updateOdometry() {
    swerveDriveOdometry.update(
        inputs.gyroAngle.inRotation2ds,
        SwerveModuleState(
            swerveModules[0].inputs.driveVelocity.inMetersPerSecond,
            swerveModules[0].inputs.steeringPosition.inRotation2ds),
        SwerveModuleState(
            swerveModules[1].inputs.driveVelocity.inMetersPerSecond,
            swerveModules[1].inputs.steeringPosition.inRotation2ds),
        SwerveModuleState(
            swerveModules[2].inputs.driveVelocity.inMetersPerSecond,
            swerveModules[2].inputs.steeringPosition.inRotation2ds),
        SwerveModuleState(
            swerveModules[3].inputs.driveVelocity.inMetersPerSecond,
            swerveModules[3].inputs.steeringPosition.inRotation2ds))
  }

  private fun hypot(a: LinearVelocity, b: LinearVelocity): LinearVelocity {
    return kotlin.math.hypot(a.inMetersPerSecond, b.inMetersPerSecond).meters.perSecond
  }

  private fun atan2(a: LinearVelocity, b: LinearVelocity): Angle {
    return kotlin.math.atan2(a.inMetersPerSecond, b.inMetersPerSecond).radians
  }

  fun resetModuleZero() {
    swerveModules.forEach { it.resetModuleZero() }
  }

  /** Zeros all the sensors on the drivetrain. */
  fun zeroSensors() {
    zeroGyro()
    zeroSteering()
    zeroDrive()
  }

  /**
   * Sets the gyroOffset in such a way that when added to the gyro angle it gives back toAngle.
   *
   * @param toAngle Zeros the gyro to the value
   */
  fun zeroGyro(toAngle: Angle = 0.degrees) {
    io.zeroGyro(toAngle)
  }

  /** Zeros the steering motors for each swerve module. */
  fun zeroSteering() {
    swerveModules.forEach { it.zeroSteering() }
  }

  /** Zeros the drive motors for each swerve module. */
  private fun zeroDrive() {
    swerveModules.forEach { it.zeroDrive() }
  }
}
