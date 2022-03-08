package com.team4099.robot2022.commands.drivetrain

import com.team4099.lib.logging.Logger
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.robot2022.commands.general.SysIdCommand
import com.team4099.robot2022.config.constants.DrivetrainConstants
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup

class DriveCharacterizeCommand(val drivetrain: Drivetrain) : SequentialCommandGroup() {
  init {
    val drivetrainSetter =
        { leftPower: Double, rightPower: Double ->
          drivetrain.swerveModules.forEach { it.setOpenLoop(0.degrees, leftPower / 12) }
        }
    val drivetrainGetter =
        {
          SysIdCommand.DriveTrainSysIdData(
              drivetrain.swerveModules[0].inputs.drivePosition.inMeters /
                  (DrivetrainConstants.WHEEL_DIAMETER.inMeters / 2),
              drivetrain.swerveModules[1].inputs.drivePosition.inMeters /
                  (DrivetrainConstants.WHEEL_DIAMETER.inMeters / 2),
              drivetrain.swerveModules[0].inputs.driveVelocity.inMetersPerSecond /
                  (DrivetrainConstants.WHEEL_DIAMETER.inMeters / 2),
              drivetrain.swerveModules[1].inputs.driveVelocity.inMetersPerSecond /
                  (DrivetrainConstants.WHEEL_DIAMETER.inMeters / 2),
              drivetrain.inputs.gyroAngle.inRadians,
              drivetrain.inputs.gyroVelocity.inRadiansPerSecond)
        }
    Logger.addEvent("Drivetrain", "Started DriveCharacterizeCommand")
    addCommands(SysIdCommand(drivetrain, drivetrainSetter, drivetrainGetter))
  }
}
