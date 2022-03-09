package com.team4099.robot2022.commands.drivetrain

import com.team4099.lib.hal.Clock
import com.team4099.lib.logging.TunableNumber
import com.team4099.lib.pathfollow.Trajectory
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.base.inSeconds
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.base.seconds
import com.team4099.lib.units.derived.cos
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.derived.radians
import com.team4099.lib.units.derived.sin
import com.team4099.lib.units.inDegreesPerSecond
import com.team4099.lib.units.inDegreesPerSecondPerSecond
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.lib.units.inRadiansPerSecondPerSecond
import com.team4099.lib.units.perSecond
import com.team4099.robot2022.config.constants.DrivetrainConstants
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.controller.ProfiledPIDController
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.CommandBase
import kotlin.math.PI
import org.littletonrobotics.junction.Logger

class DrivePathCommand(
  val drivetrain: Drivetrain,
  private val trajectory: Trajectory,
  val resetPose: Boolean = false
) : CommandBase() {
  private val xPID: PIDController
  private val yPID: PIDController

  private val thetaPID: ProfiledPIDController

  private var trajCurTime = 0.0.seconds
  private var trajStartTime = 0.0.seconds

  val thetakP = TunableNumber("Pathfollow/thetakP", DrivetrainConstants.PID.AUTO_THETA_PID_KP)
  val thetakI = TunableNumber("Pathfollow/thetakI", DrivetrainConstants.PID.AUTO_THETA_PID_KI)
  val thetakD = TunableNumber("Pathfollow/thetakD", DrivetrainConstants.PID.AUTO_THETA_PID_KD)

  val thetaMaxVel =
      TunableNumber(
          "Pathfollow/thetaMaxVel", DrivetrainConstants.PID.MAX_AUTO_ANGULAR_VEL.inDegreesPerSecond)
  val thetaMaxAccel =
      TunableNumber(
          "Pathfollow/thetaMaxAccel",
          DrivetrainConstants.PID.MAX_AUTO_ANGULAR_ACCEL.inDegreesPerSecondPerSecond)

  val poskP = TunableNumber("Pathfollow/poskP", DrivetrainConstants.PID.AUTO_POS_KP)
  val poskI = TunableNumber("Pathfollow/poskI", DrivetrainConstants.PID.AUTO_POS_KI)
  val poskD = TunableNumber("Pathfollow/poskD", DrivetrainConstants.PID.AUTO_POS_KD)

  init {
    addRequirements(drivetrain)

    xPID = PIDController(poskP.value, poskD.value, poskI.value)
    yPID = PIDController(poskP.value, poskD.value, poskI.value)
    thetaPID =
        ProfiledPIDController(
            thetakP.value,
            thetakI.value,
            thetakD.value,
            TrapezoidProfile.Constraints(
                thetaMaxVel.value.degrees.perSecond.inRadiansPerSecond,
                thetaMaxAccel.value.degrees.perSecond.perSecond.inRadiansPerSecondPerSecond))

    thetaPID.enableContinuousInput(-PI, PI)
  }

  override fun initialize() {
    if (resetPose) {
      drivetrain.pose = trajectory.startingPose
    }
    trajStartTime = Clock.fpgaTime + trajectory.startTime
  }

  override fun execute() {
    trajCurTime = Clock.fpgaTime - trajStartTime
    val desiredState = trajectory.sample(trajCurTime)
    val xFF = desiredState.linearVelocity * desiredState.curvature.cos
    val yFF = desiredState.linearVelocity * desiredState.curvature.sin
    val thetaFF =
        thetaPID.calculate(
                -drivetrain.pose.theta.inRadians,
                TrapezoidProfile.State(
                    desiredState.pose.theta.inRadians,
                    desiredState.angularVelocity.inRadiansPerSecond))
            .radians
            .perSecond

    // Calculate feedback velocities (based on position error).
    val xFeedback =
        xPID.calculate(drivetrain.pose.x.inMeters, desiredState.pose.x.inMeters).meters.perSecond
    val yFeedback =
        yPID.calculate(drivetrain.pose.y.inMeters, desiredState.pose.y.inMeters).meters.perSecond

    val xAccel = desiredState.linearAcceleration * desiredState.curvature.cos
    val yAccel = desiredState.linearAcceleration * desiredState.curvature.sin

    drivetrain.targetPose = desiredState.pose

    drivetrain.set(
        thetaFF,
        Pair(-yFF - yFeedback, -xFF - xFeedback),
        true,
        0.radians.perSecond.perSecond,
        Pair(yAccel, xAccel))

    Logger.getInstance().recordOutput("Pathfollow/Start Time", trajStartTime.inSeconds)
    Logger.getInstance().recordOutput("Pathfollow/Current Time", trajCurTime.inSeconds)

    if (thetakP.hasChanged()) thetaPID.p = thetakP.value
    if (thetakI.hasChanged()) thetaPID.i = thetakI.value
    if (thetakD.hasChanged()) thetaPID.d = thetakD.value

    if (poskP.hasChanged()) {
      xPID.p = poskP.value
      yPID.p = poskP.value
    }
    if (poskI.hasChanged()) {
      xPID.i = poskI.value
      yPID.i = poskI.value
    }
    if (poskD.hasChanged()) {
      xPID.d = poskD.value
      yPID.d = poskD.value
    }

    if (thetaMaxAccel.hasChanged() || thetaMaxVel.hasChanged()) {
      thetaPID.setConstraints(
          TrapezoidProfile.Constraints(
              thetaMaxVel.value.degrees.perSecond.inRadiansPerSecond,
              thetaMaxAccel.value.degrees.perSecond.perSecond.inRadiansPerSecondPerSecond))
    }
  }

  override fun isFinished(): Boolean {
    trajCurTime = Clock.fpgaTime - trajStartTime
    return trajCurTime > trajectory.endTime
  }

  override fun end(interrupted: Boolean) {
    if (interrupted) {
      // Stop where we are if interrupted
      drivetrain.set(0.degrees.perSecond, Pair(0.meters.perSecond, 0.meters.perSecond))
    } else {
      // Execute one last time to end up in the final state of the trajectory
      // Since we weren't interrupted, we know curTime > endTime
      execute()
    }
  }
}
