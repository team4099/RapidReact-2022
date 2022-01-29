package com.team4099.robot2022.commands.drivetrain

import com.team4099.lib.logging.Logger
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.base.inches
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.robot2022.subsystems.Drivetrain
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup

class DriveCharacterizeCommand : SequentialCommandGroup() {
  init {
    val drivetrainSetter =
        { leftPower: Double, rightPower: Double ->
          Drivetrain.wheels.forEach { it.setOpenLoop(0.degrees, leftPower / 12) }
        }
    val drivetrainGetter =
        {
          SysIdCommand.DriveTrainSysIdData(
              // TODO: create constant wheel radius
              Drivetrain.wheels[0].driveDistance.inMeters / 1.5.inches.inMeters,
              Drivetrain.wheels[1].driveDistance.inMeters / 1.5.inches.inMeters,
              Drivetrain.wheels[0].driveVelocity.inMetersPerSecond / 1.5.inches.inMeters,
              Drivetrain.wheels[1].driveVelocity.inMetersPerSecond / 1.5.inches.inMeters,
              Drivetrain.gyroOffset.inRadians,
              Drivetrain.gyroRate.inRadiansPerSecond)
        }
    Logger.addEvent("Drivetrain", "Started DriveCharacterizeCommand")
    addCommands(SysIdCommand(Drivetrain, drivetrainSetter, drivetrainGetter))
  }
}
