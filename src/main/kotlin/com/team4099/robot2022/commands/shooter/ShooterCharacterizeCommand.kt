package com.team4099.robot2022.commands.shooter

import com.team4099.lib.logging.Logger
import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.robot2022.commands.general.SysIdCommand
import com.team4099.robot2022.subsystems.Shooter
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup

class ShooterCharacterizeCommand : SequentialCommandGroup() {
  init {
    val shooterSetter = { voltage: Double ->
      Shooter.setOpenLoop(voltage / 12.0)
//      println("ShooterSetter voltage $voltage")
    }

    val shooterGetter =
      {
        SysIdCommand.MechanismSysIdData(
          Shooter.shooterPosition.inRadians, Shooter.shooterVelocity.inRadiansPerSecond
        )
      }
    Logger.addEvent("Shooter", "Started ShooterCharacterizeCommand")
    addCommands(SysIdCommand(Shooter, shooterSetter, shooterGetter))
  }
}
