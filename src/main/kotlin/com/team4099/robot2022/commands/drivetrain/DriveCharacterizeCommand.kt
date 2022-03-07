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

class DriveCharacterizeCommand : SequentialCommandGroup() {
  init {
    val drivetrainSetter =
        { leftPower: Double, rightPower: Double ->
          Drivetrain.swerveModules.forEach { it.setOpenLoop(0.degrees, leftPower / 12) }
        }
    val drivetrainGetter =
        {
          SysIdCommand.DriveTrainSysIdData(
              Drivetrain.swerveModules[0].driveDistance.inMeters /
                  (DrivetrainConstants.WHEEL_DIAMETER.inMeters / 2),
              Drivetrain.swerveModules[1].driveDistance.inMeters /
                  (DrivetrainConstants.WHEEL_DIAMETER.inMeters / 2),
              Drivetrain.swerveModules[0].driveVelocity.inMetersPerSecond /
                  (DrivetrainConstants.WHEEL_DIAMETER.inMeters / 2),
              Drivetrain.swerveModules[1].driveVelocity.inMetersPerSecond /
                  (DrivetrainConstants.WHEEL_DIAMETER.inMeters / 2),
              Drivetrain.gyroAngle.inRadians,
              Drivetrain.gyroRate.inRadiansPerSecond)
        }
    Logger.addEvent("Drivetrain", "Started DriveCharacterizeCommand")
    addCommands(SysIdCommand(Drivetrain, drivetrainSetter, drivetrainGetter))
  }
}
