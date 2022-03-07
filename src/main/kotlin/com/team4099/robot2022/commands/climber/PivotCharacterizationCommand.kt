package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.robot2022.commands.general.SysIdCommand
import com.team4099.robot2022.subsystems.climber.PivotClimber
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup

class PivotCharacterizationCommand(val pivotClimber: PivotClimber) : SequentialCommandGroup() {
  init {
    val pivotSetter =
        { voltage: Double -> pivotClimber.setOpenLoop(voltage / 12.0, voltage / 12.0) }

    val pivotGetter =
        {
          SysIdCommand.MechanismSysIdData(
              pivotClimber.pivotLeftArmSensor.position.inRadians,
              pivotClimber.pivotLeftArmSensor.velocity.inRadiansPerSecond)
        }
    Logger.addEvent("Climber", "Started Telescoping Characterization")
    addCommands(SysIdCommand(pivotClimber, pivotSetter, pivotGetter))
  }
}
