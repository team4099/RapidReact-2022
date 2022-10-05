package com.team4099.robot2022.auto.mode

import com.team4099.lib.pathfollow.Trajectory
import com.team4099.lib.pathfollow.trajectoryFromPathPlanner
import com.team4099.robot2022.auto.PathStore
import com.team4099.robot2022.commands.drivetrain.DrivePathCommand
import com.team4099.robot2022.commands.drivetrain.ResetPoseCommand
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.littletonrobotics.junction.Logger

class TwoMeterWheelCharacterization(val drivetrain: Drivetrain) : SequentialCommandGroup() {

  private val twoMeterWheelForward: Trajectory =
    trajectoryFromPathPlanner(PathStore.twoMeterWheelTrajectory)

  init {
    addCommands(
      ResetPoseCommand(drivetrain, twoMeterWheelForward.startingPose),
      DrivePathCommand(drivetrain, twoMeterWheelForward, resetPose = false)
    )
  }

  override fun execute() {
    super.execute()
    Logger.getInstance().recordOutput("ActiveCommands/TwoMeterWheelCharacterization", true)
  }
}
