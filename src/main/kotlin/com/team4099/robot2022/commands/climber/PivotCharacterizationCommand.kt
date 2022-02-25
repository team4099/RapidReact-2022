package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.robot2022.commands.general.SysIdCommand
import com.team4099.robot2022.subsystems.PivotClimber
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup

class PivotCharacterizationCommand : SequentialCommandGroup() {
  init {
    val pivotSetter =
        { voltage: Double -> PivotClimber.setOpenLoop(voltage / 12.0, voltage / 12.0) }

    val pivotGetter =
        {
          SysIdCommand.MechanismSysIdData(
              PivotClimber.pivotLeftArmSensor.position.inRadians,
              PivotClimber.pivotLeftArmSensor.velocity.inRadiansPerSecond)
        }
    Logger.addEvent("Climber", "Started Telescoping Characterization")
    addCommands(SysIdCommand(PivotClimber, pivotSetter, pivotGetter))
  }
}
