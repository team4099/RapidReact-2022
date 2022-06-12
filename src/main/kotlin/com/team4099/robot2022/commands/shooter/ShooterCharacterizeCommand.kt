package com.team4099.robot2022.commands.shooter

import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.robot2022.commands.general.SysIdCommand
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup

class ShooterCharacterizeCommand(val shooter: Shooter) : SequentialCommandGroup() {
  init {
    val shooterSetter = { voltage: Double ->
      shooter.setOpenLoop(voltage / 12.0, 0.0)
      println("ShooterSetter voltage $voltage")
    }

    val shooterGetter = {
      SysIdCommand.MechanismSysIdData(
        shooter.inputs.flywheelPosition.inRadians,
        shooter.inputs.flywheelVelocity.inRadiansPerSecond
      )
    }
    addCommands(SysIdCommand(shooter, shooterSetter, shooterGetter))
  }
}
