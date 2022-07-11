package com.team4099.robot2022.auto.mode

import com.team4099.lib.pathfollow.Trajectory
import com.team4099.lib.pathfollow.trajectoryFromPathPlanner
import com.team4099.robot2022.auto.PathStore
import com.team4099.robot2022.commands.drivetrain.DrivePathCommand
import com.team4099.robot2022.commands.drivetrain.ResetPoseCommand
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.littletonrobotics.junction.Logger

class TestAutoPath(val drivetrain: Drivetrain) : SequentialCommandGroup() {

  private val trajectory: Trajectory

  init {
    trajectory = trajectoryFromPathPlanner(PathStore.testAutoPath)

    addRequirements(drivetrain)

    addCommands(
      ResetPoseCommand(drivetrain, trajectory.startingPose),
      DrivePathCommand(
        drivetrain, trajectory, resetPose = true
      )
    )
  }

  override fun execute() {
    super.execute()
    Logger.getInstance().recordOutput("ActiveCommands/TestAutoPath", true)
  }
}
