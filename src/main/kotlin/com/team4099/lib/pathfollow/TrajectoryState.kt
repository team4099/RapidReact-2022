package com.team4099.lib.pathfollow

import com.team4099.lib.geometry.Pose
import com.team4099.lib.units.LinearAcceleration
import com.team4099.lib.units.LinearVelocity
import com.team4099.lib.units.base.Time
import com.team4099.lib.units.derived.Angle

data class TrajectoryState(
  val timestamp: Time,
  val pose: Pose,
  val curvature: Angle,
  val linearVelocity: LinearVelocity,
  val linearAcceleration: LinearAcceleration
)
