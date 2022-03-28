package com.team4099.robot2022.commands.climber

import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.robot2022.commands.general.SysIdCommand
import com.team4099.robot2022.config.constants.TelescopingClimberConstants
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup

class TelescopingCharacterizationCommand(val telescopingClimber: TelescopingClimber) :
  SequentialCommandGroup() {
  init {
    val telescopingSetter = { voltage: Double ->
      telescopingClimber.setOpenLoop(voltage / 12.0, voltage / 12.0)
    }

    val telescopingGetter = {
      SysIdCommand.MechanismSysIdData(
        telescopingClimber.inputs.leftPosition.inMeters /
          TelescopingClimberConstants.SPOOL_RADIUS.inMeters,
        telescopingClimber.inputs.leftVelocity.inMetersPerSecond /
          TelescopingClimberConstants.SPOOL_RADIUS.inMeters
      )
    }
    addCommands(SysIdCommand(telescopingClimber, telescopingSetter, telescopingGetter))
  }
}
