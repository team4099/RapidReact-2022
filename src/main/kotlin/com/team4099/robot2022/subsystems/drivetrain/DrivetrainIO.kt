package com.team4099.robot2022.subsystems.drivetrain

import com.team4099.lib.units.derived.Angle
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inDegrees
import com.team4099.lib.units.derived.radians
import com.team4099.lib.units.inDegreesPerSecond
import com.team4099.lib.units.perSecond
import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs

interface DrivetrainIO {
  class DrivetrainIOInputs : LoggableInputs {

    var gyroAngle = 0.0.radians
    var gyroVelocity = 0.0.radians.perSecond

    var gyroConnected = true

    override fun toLog(table: LogTable?) {
      table?.put("gyroAngleDegrees", gyroAngle.inDegrees)
      table?.put("gyroVelocityDegreesPerSecond", gyroVelocity.inDegreesPerSecond)
      table?.put("gyroConnected", gyroConnected)
    }

    override fun fromLog(table: LogTable?) {
      table?.getDouble("gyroAngleDegrees", gyroAngle.inDegrees)?.let { gyroAngle = it.degrees }
      table?.getDouble("gyroVelocityDegreesPerSecond", gyroVelocity.inDegreesPerSecond)?.let {
        gyroVelocity.inDegreesPerSecond
      }
      table?.getBoolean("gyroConnected", gyroConnected)?.let { gyroConnected = it }
    }
  }

  fun getSwerveModules(): List<SwerveModule> {
    return listOf(
        SwerveModule(
            object : SwerveModuleIO {
              override val label = "Front Left Wheel"
            }),
        SwerveModule(
            object : SwerveModuleIO {
              override val label = "Front Right Wheel"
            }),
        SwerveModule(
            object : SwerveModuleIO {
              override val label = "Back Left Wheel"
            }),
        SwerveModule(
            object : SwerveModuleIO {
              override val label = "Back Right Wheel"
            }))
  }

  fun updateInputs(inputs: DrivetrainIOInputs) {}

  fun zeroGyro(toAngle: Angle) {}
}
