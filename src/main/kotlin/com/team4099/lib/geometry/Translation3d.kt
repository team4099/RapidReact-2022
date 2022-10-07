package com.team4099.lib.geometry

import com.team4099.lib.units.base.Length
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.base.meters
import com.team4099.lib.wpilib3d.Quaternion
import com.team4099.lib.wpilib3d.Rotation3d

typealias External = com.team4099.lib.wpilib3d.Translation3d

class Translation3d(val m_x: Length, val m_y: Length, val m_z: Length) {
  constructor(
    translation3d: External
  ) : this(
    translation3d.getX().meters,
    translation3d.getY().meters,
    translation3d.getZ().meters,
  )

  constructor() : this(0.meters, 0.meters, 0.meters)

  constructor(
    distance: Length,
    angle: Rotation3d
  ) : this(
    Translation3d(distance, 0.meters, 0.meters).rotateBy(angle).getX(),
    Translation3d(distance, 0.meters, 0.meters).rotateBy(angle).getY(),
    Translation3d(distance, 0.meters, 0.meters).rotateBy(angle).getZ()
  )

  fun rotateBy(other: Rotation3d): Translation3d {
    val p = Quaternion(0.0, m_x.inMeters, m_y.inMeters, m_z.inMeters)
    val qprime: Quaternion = other.getQuaternion().times(p).times(other.getQuaternion().inverse())
    return Translation3d(qprime.x.meters, qprime.y.meters, qprime.z.meters)
  }

  fun toTranslation2d(): Translation {
    return Translation(m_x, m_y)
  }

  fun getX(): Length {
    return m_x
  }

  fun getY(): Length {
    return m_y
  }

  fun getZ(): Length {
    return m_z
  }

  fun plus(other: Translation3d): Translation3d {
    return Translation3d(m_x + other.getX(), m_y + other.getY(), m_z + other.getZ())
  }

  fun minus(other: Translation3d): Translation3d {
    return Translation3d(m_x - other.getX(), m_y - other.getY(), m_z - other.getZ())
  }

  fun getDistance(other: Translation3d): Length {
    val dist: Translation3d = other.minus(Translation3d(m_x, m_y, m_z))
    return Math.sqrt(
      (
        dist.m_x * dist.m_x.inMeters +
          dist.m_y * dist.m_y.inMeters +
          dist.m_z * dist.m_z.inMeters
        ) / 1.meters
    )
      .meters
  }

  fun getNorm(): Length {
    return Math.sqrt((m_x * m_x.inMeters + m_y * m_y.inMeters + m_z * m_z.inMeters).inMeters).meters
  }

  fun unaryMinus(): Translation3d {
    return Translation3d(-m_x, -m_y, -m_z)
  }

  fun times(scalar: Double): Translation3d {
    return Translation3d(m_x * scalar, m_y * scalar, m_z * scalar)
  }

  fun div(scalar: Double): Translation3d {
    return Translation3d(m_x / scalar, m_y / scalar, m_z / scalar)
  }

  fun toTranslation3d(): External {
    return External(m_x.inMeters, m_y.inMeters, m_z.inMeters)
  }
}
