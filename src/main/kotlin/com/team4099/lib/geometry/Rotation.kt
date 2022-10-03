package com.team4099.lib.geometry

import com.team4099.lib.units.derived.Angle
import com.team4099.lib.units.derived.angle
import edu.wpi.first.math.geometry.Rotation2d

data class Rotation(val theta: Angle) {
  constructor(rotation2d: Rotation2d) : this(rotation2d.angle)
}
