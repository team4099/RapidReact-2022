package com.team4099.robot2022.commands.climber

import com.team4099.robot2022.subsystems.climber.PivotClimber
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand

class ClimbSequenceCommand(
  val telescopingClimber: TelescopingClimber,
  val pivotClimber: PivotClimber
) : SequentialCommandGroup() {
  init {
    addCommands(
        RetractTelescopingArmCommand(telescopingClimber),
        ExtendPivotArmCommand(pivotClimber),
        WaitCommand(0.5),
        ExtendTelescopingArmCommand(telescopingClimber),
        RetractPivotArmCommand(pivotClimber),
        WaitCommand(1.0))
  }
}
