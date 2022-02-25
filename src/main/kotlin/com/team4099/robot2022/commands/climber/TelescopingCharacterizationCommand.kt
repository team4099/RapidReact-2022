package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.robot2022.commands.general.SysIdCommand
import com.team4099.robot2022.subsystems.TelescopingClimber
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup

class TelescopingCharacterizationCommand : SequentialCommandGroup() {
  init {
    val telescopingSetter =
        { voltage: Double -> TelescopingClimber.setOpenLoop(voltage / 12.0, voltage / 12.0) }

    val telescopingGetter =
        {
          SysIdCommand.MechanismSysIdData(
              TelescopingClimber.telescopingLeftArmSensor.position.inMeters,
              TelescopingClimber.telescopingLeftArmSensor.velocity.inMetersPerSecond)
        }
    Logger.addEvent("Climber", "Started Telescoping Characterization")
    addCommands(SysIdCommand(TelescopingClimber, telescopingSetter, telescopingGetter))
  }
}
