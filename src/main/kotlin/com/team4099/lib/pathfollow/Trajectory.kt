package com.team4099.lib.pathfollow

import com.team4099.lib.geometry.Pose
import com.team4099.lib.geometry.Translation
import com.team4099.lib.geometry.interpolate
import com.team4099.lib.interpolate
import com.team4099.lib.units.LinearVelocity
import com.team4099.lib.units.base.Time
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.base.seconds
import com.team4099.lib.units.derived.angle
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.inMetersPerSecondPerSecond
import com.team4099.lib.units.perSecond
import edu.wpi.first.wpilibj.trajectory.TrajectoryParameterizer

/**
 * A wrapper around the WPILib trajectory class that handles smooth heading changes for holonomic
 * drivetrains.
 */
class Trajectory(
  private val startVelocity: LinearVelocity,
  private val path: Path,
  private val endVelocity: LinearVelocity,
  private val trajectoryConfig: TrajectoryConfig
) {
  val states: List<TrajectoryState>
  val startTime: Time
    get() = states[0].timestamp
  val endTime: Time
    get() = states[states.size - 1].timestamp

  val startingPose = path.startingPose
  val endingPose = path.endingPose

  init {
    if (!path.built) path.build()

    val wpilibStates =
        TrajectoryParameterizer.timeParameterizeTrajectory(
                path.splinePoints,
                trajectoryConfig.constraints,
                startVelocity.inMetersPerSecond,
                endVelocity.inMetersPerSecond,
                trajectoryConfig.maxLinearVelocity.inMetersPerSecond,
                trajectoryConfig.maxLinearAcceleration.inMetersPerSecondPerSecond,
                false)
            .states

    states =
        wpilibStates.mapIndexed { index, state ->
          var headingTarget =
              if (index == 0) {
                path.startingPose.theta
              } else if (index == wpilibStates.size - 1) {
                path.endingPose.theta
              } else {
                val tailMap = path.headingPointMap.tailMap(index)
                if (tailMap.size == 0) {
                  path.endingPose.theta
                } else {
                  path.headingPointMap[tailMap.firstKey()]
                }
              }

          if (headingTarget == null) {
            headingTarget = path.endingPose.theta
          }

          TrajectoryState(
              state.timeSeconds.seconds,
              Pose(Translation(state.poseMeters.translation), headingTarget),
              state.poseMeters.rotation.angle,
              state.velocityMetersPerSecond.meters.perSecond,
              state.accelerationMetersPerSecondSq.meters.perSecond.perSecond)
        }
  }

  fun sample(time: Time): TrajectoryState {
    if (time <= startTime) {
      return states[0]
    }

    if (time >= endTime) {
      return states[states.size - 1]
    }

    var low = 1
    var high = states.size - 1

    // Binary search to find the closest timestamp
    while (low != high) {
      val mid = (low + high) / 2
      if (states[mid].timestamp < time) {
        low = mid + 1
      } else {
        high = mid
      }
    }

    val lowState = states[low - 1]
    val highState = states[low]
    val lerpScalar = (time - lowState.timestamp) / (highState.timestamp - lowState.timestamp)

    return TrajectoryState(
        time,
        interpolate(lowState.pose, highState.pose, lerpScalar),
        interpolate(lowState.curvature, highState.curvature, lerpScalar),
        interpolate(lowState.linearVelocity, highState.linearVelocity, lerpScalar),
        interpolate(lowState.linearAcceleration, highState.linearAcceleration, lerpScalar))
  }
}
