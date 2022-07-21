package com.team4099.robot2022.commands.climber

import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand

class FixTelescopingInFrontOfRungCommand(val telescopingClimber: TelescopingClimber) :
  SequentialCommandGroup() {
  init {
    addCommands(
      RetractTelescopingArmCommand(telescopingClimber).withTimeout(0.5),
      WaitCommand(0.25),
      ExtendTelescopingArmCommand(telescopingClimber),
      RetractTelescopingArmCommand(telescopingClimber).withTimeout(1.0)
    )
  }
}
