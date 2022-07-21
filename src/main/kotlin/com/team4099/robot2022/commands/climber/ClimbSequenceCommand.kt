package com.team4099.robot2022.commands.climber

import com.team4099.robot2022.subsystems.climber.PivotClimber
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand

class ClimbSequenceCommand(
  val telescopingClimber: TelescopingClimber,
  val pivotClimber: PivotClimber,
) : SequentialCommandGroup() {

  init {
    addCommands(
      // mid to high
      RetractTelescopingArmCommand(telescopingClimber).withTimeout(0.75),
      WaitCommand(0.25),
      RetractTelescopingArmCommand(telescopingClimber),
      ExtendPivotArmCommand(pivotClimber),
      WaitCommand(0.25),
      ExtendTelescopingArmCommand(telescopingClimber).withTimeout(0.5),
      WaitCommand(1.0),
      ExtendTelescopingArmCommand(telescopingClimber),
      RetractPivotArmCommand(pivotClimber),
      WaitCommand(1.0),

      // high to traversal
      RetractTelescopingArmCommand(telescopingClimber).withTimeout(0.75),
      WaitCommand(0.25),
      RetractTelescopingArmCommand(telescopingClimber),
      ExtendPivotArmCommand(pivotClimber),
      WaitCommand(0.5),
      OpenLoopExtendClimberCommand(telescopingClimber).withTimeout(0.6),
      WaitCommand(1.75),
      ExtendTelescopingArmCommand(telescopingClimber),
      RetractPivotArmCommand(pivotClimber),
      WaitCommand(1.0),
      OpenLoopClimbCommand(telescopingClimber).withTimeout(0.75)
    )
  }
}
