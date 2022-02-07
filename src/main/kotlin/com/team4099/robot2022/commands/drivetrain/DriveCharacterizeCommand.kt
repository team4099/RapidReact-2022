package com.team4099.robot2022.commands.drivetrain

import com.team4099.lib.logging.Logger
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.robot2022.config.Constants
import com.team4099.robot2022.subsystems.Drivetrain
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
                  (Constants.Drivetrain.WHEEL_DIAMETER.inMeters / 2),
              Drivetrain.swerveModules[1].driveDistance.inMeters /
                  (Constants.Drivetrain.WHEEL_DIAMETER.inMeters / 2),
              Drivetrain.swerveModules[0].driveVelocity.inMetersPerSecond /
                  (Constants.Drivetrain.WHEEL_DIAMETER.inMeters / 2),
              Drivetrain.swerveModules[1].driveVelocity.inMetersPerSecond /
                  (Constants.Drivetrain.WHEEL_DIAMETER.inMeters / 2),
              Drivetrain.gyroOffset.inRadians,
              Drivetrain.gyroRate.inRadiansPerSecond)
        }
    Logger.addEvent("Drivetrain", "Started DriveCharacterizeCommand")
    addCommands(SysIdCommand(Drivetrain, drivetrainSetter, drivetrainGetter))
  }
}
