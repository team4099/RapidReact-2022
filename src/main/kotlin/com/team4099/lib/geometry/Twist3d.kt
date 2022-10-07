package com.team4099.lib.geometry

import com.team4099.lib.units.AngularVelocity
import com.team4099.lib.units.LinearVelocity
import com.team4099.lib.units.base.Time
import com.team4099.lib.units.base.inSeconds
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.derived.radians
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.lib.units.perSecond
import com.team4099.lib.wpilib3d.Twist3d

data class Twist3d(
  val dx: LinearVelocity,
  val dy: LinearVelocity,
  val dz: LinearVelocity,
  val rx: AngularVelocity,
  val ry: AngularVelocity,
  val rz: AngularVelocity
) {
  constructor(
    translationSpeeds: Triple<LinearVelocity, LinearVelocity, LinearVelocity>,
    rotationalSpeeds: Triple<AngularVelocity, AngularVelocity, AngularVelocity>
  ) : this(
    translationSpeeds.first,
    translationSpeeds.second,
    translationSpeeds.third,
    rotationalSpeeds.first,
    rotationalSpeeds.second,
    rotationalSpeeds.third
  )

  constructor(
    twist: Twist3d,
    timestep: Time
  ) : this(
    (twist.dx / timestep.inSeconds).meters.perSecond,
    (twist.dy / timestep.inSeconds).meters.perSecond,
    (twist.dz / timestep.inSeconds).meters.perSecond,
    (twist.rx / timestep.inSeconds).radians.perSecond,
    (twist.ry / timestep.inSeconds).radians.perSecond,
    (twist.rz / timestep.inSeconds).radians.perSecond
  )

  fun toTwist3d(timestep: Time): Twist3d {
    return (
      Twist3d(
        dx.inMetersPerSecond * timestep.inSeconds,
        dy.inMetersPerSecond * timestep.inSeconds,
        dz.inMetersPerSecond * timestep.inSeconds,
        rx.inRadiansPerSecond * timestep.inSeconds,
        ry.inRadiansPerSecond * timestep.inSeconds,
        rz.inRadiansPerSecond * timestep.inSeconds
      )
      )
  }
}
