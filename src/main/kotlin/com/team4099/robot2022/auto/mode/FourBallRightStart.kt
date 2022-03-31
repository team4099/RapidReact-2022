package com.team4099.robot2022.auto.mode

import com.team4099.lib.pathfollow.trajectoryFromPathPlanner
import com.team4099.robot2022.auto.PathStore
import com.team4099.robot2022.commands.drivetrain.DrivePathCommand
import com.team4099.robot2022.commands.drivetrain.ResetPoseCommand
import com.team4099.robot2022.commands.feeder.FeederSerialize
import com.team4099.robot2022.commands.intake.IntakeBallsCommand
import com.team4099.robot2022.commands.shooter.ShootCommand
import com.team4099.robot2022.commands.shooter.SpinUpUpperHub
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.intake.Intake
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand

class FourBallRightStart(
  val drivetrain: Drivetrain,
  val intake: Intake,
  val feeder: Feeder,
  val shooter: Shooter
) : SequentialCommandGroup() {
  val twoBallRightTrajectory = trajectoryFromPathPlanner(PathStore.twoBallRightStartPath)
  val fourBallRightTrajectory = trajectoryFromPathPlanner(PathStore.fourBallRightStartPath)

  init {
    addCommands(
      ResetPoseCommand(drivetrain, twoBallRightTrajectory.startingPose),
      ParallelCommandGroup(
        (IntakeBallsCommand(intake).alongWith(FeederSerialize(feeder))).withTimeout(2.0),
        DrivePathCommand(drivetrain, twoBallRightTrajectory, resetPose = false),
      ),
      SpinUpUpperHub(shooter).andThen(ShootCommand(shooter, feeder).withTimeout(1.0)),
      ParallelCommandGroup(
        WaitCommand(1.0)
          .andThen(
            (IntakeBallsCommand(intake).alongWith(FeederSerialize(feeder))).withTimeout(
              4.5
            )
          ),
        DrivePathCommand(drivetrain, fourBallRightTrajectory, resetPose = false)
      ),
      SpinUpUpperHub(shooter).andThen(ShootCommand(shooter, feeder).withTimeout(1.0))
    )
  }
}
