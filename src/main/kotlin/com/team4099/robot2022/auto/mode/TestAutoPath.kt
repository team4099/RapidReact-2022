package com.team4099.robot2022.auto.mode

import com.team4099.lib.pathfollow.trajectoryFromPathPlanner
import com.team4099.robot2022.auto.PathStore
import com.team4099.robot2022.commands.drivetrain.AutoDriveCommand
import com.team4099.robot2022.subsystems.Drivetrain
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup

class TestAutoPath: SequentialCommandGroup() {
  init{
    addRequirements(Drivetrain)

    addCommands(
      AutoDriveCommand(trajectoryFromPathPlanner(PathStore.testAutoPath))
    )
  }
}
