package com.team4099.robot2022.auto.mode

import com.team4099.lib.pathfollow.trajectoryFromPathPlanner
import com.team4099.robot2022.auto.PathStore
import com.team4099.robot2022.commands.drivetrain.DrivePathCommand
import com.team4099.robot2022.commands.drivetrain.ResetPoseCommand
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

class FourBallLeftStart(
  val drivetrain: Drivetrain,
  val intake: Intake,
  val feeder: Feeder,
  val shooter: Shooter
) : SequentialCommandGroup() {

  val twoBallTrajectory = trajectoryFromPathPlanner(PathStore.twoBallLeftStartPath)
  val threeAndFourBallTrajectory =
      trajectoryFromPathPlanner(PathStore.threeAndFourBallLeftStartPath)

  init {
    addCommands(
        ResetPoseCommand(drivetrain, twoBallTrajectory.startingPose),
        ParallelCommandGroup(
            IntakeBallsCommand(intake).withTimeout(1.5),
            DrivePathCommand(drivetrain, twoBallTrajectory, resetPose = false)),
        SpinUpUpperHub(shooter).andThen(ShootCommand(shooter, feeder).withTimeout(3.0)),
        ParallelCommandGroup(
            WaitCommand(2.0).andThen(IntakeBallsCommand(intake).withTimeout(2.0)),
            DrivePathCommand(drivetrain, threeAndFourBallTrajectory, resetPose = false)),
        SpinUpUpperHub(shooter).andThen(ShootCommand(shooter, feeder).withTimeout(3.0)))
  }
}
