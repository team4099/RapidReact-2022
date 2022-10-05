package com.team4099

import com.team4099.lib.geometry.Pose
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inDegrees
import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.derived.radians
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class Pose2dTest {
  private val kEpsilon = 1E-9
  //test commit

  //  @Test
  //  fun testTransformBy() {
  //    val initial = Pose(Translation(Translation2d(1.0, 2.0)),
  // Rotation2d.fromDegrees(45.0).radians)
  //    val transformation =
  //      Transform2d(Translation(Translation2d(5.0, 0.0)), Rotation2d.fromDegrees(5.0))
  //    val transformed = initial.plus(transformation)
  //    assertAll(
  //      { assertEquals(1.0 + 5.0 / Math.sqrt(2.0), transformed.x, kEpsilon) },
  //      { assertEquals(2.0 + 5.0 / Math.sqrt(2.0), transformed.y, kEpsilon) },
  //      { assertEquals(50.0, transformed.rotation.degrees, kEpsilon) }
  //    )
  //  }
  //
  @Test
  fun testRelativeTo() {
    val initial = Pose(0.0.meters, 0.0.meters, 45.degrees)
    val last = Pose(5.0.meters, 5.0.meters, 45.degrees)
    val finalRelativeToInitial = last.relativeTo(initial)
    assertAll(
      { assertEquals(5.0 * Math.sqrt(2.0), finalRelativeToInitial.x.inMeters, kEpsilon) },
      { assertEquals(0.0, finalRelativeToInitial.y.inMeters, kEpsilon) },
      { assertEquals(0.0, finalRelativeToInitial.theta.inDegrees, kEpsilon) }
    )
  }

  @Test
  fun testEquality() {
    val one = Pose(0.0.meters, 5.0.meters, 43.degrees)
    val two = Pose(0.0.meters, 5.0.meters, 43.degrees)
    assertEquals(one, two)
  }

  @Test
  fun testInequality() {
    val one = Pose(0.0.meters, 5.0.meters, 43.degrees)
    val two = Pose(0.0.meters, 1.524.meters, 43.degrees)
    assertNotEquals(one, two)
  }

  @Test
  fun testMinus() {
    val initial = Pose(0.0.meters, 0.0.meters, 45.0.degrees.inRadians.radians)
    val last = Pose(5.0.meters, 5.0.meters, 45.0.degrees.inRadians.radians)
    val transform = last.minus(initial)
    assertAll(
      { assertEquals(5.0, transform.x.inMeters, kEpsilon) },
      { assertEquals(5.0, transform.y.inMeters, kEpsilon) },
      { assertEquals(0.0, transform.theta.inDegrees, kEpsilon) }
    )
  }
}
