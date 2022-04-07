package com.team4099.robot2022.auto.mode

import com.team4099.lib.pathfollow.trajectoryFromPathPlanner
import com.team4099.robot2022.auto.PathStore
import com.team4099.robot2022.commands.drivetrain.DrivePathCommand
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.littletonrobotics.junction.Logger

class TestAutoPath(val drivetrain: Drivetrain) : SequentialCommandGroup() {
  init {
    addRequirements(drivetrain)

    addCommands(
      DrivePathCommand(
        drivetrain, trajectoryFromPathPlanner(PathStore.testAutoPath), resetPose = true
      )
    )
  }

  override fun execute() {
    Logger.getInstance().recordOutput("ActiveCommands/TestAutoPath", true)
  }
}
