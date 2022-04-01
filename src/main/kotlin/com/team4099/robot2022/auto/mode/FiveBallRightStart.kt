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

class FiveBallRightStart(
  val drivetrain: Drivetrain,
  val intake: Intake,
  val feeder: Feeder,
  val shooter: Shooter
) : ParallelCommandGroup() {
  init {
    val threeBallRightStartFasterTrajectory =
      trajectoryFromPathPlanner(PathStore.threeBallRightStartFasterPath)
    val fiveBallRightStartTrajectory = trajectoryFromPathPlanner(PathStore.fiveBallRightStart)

    addCommands(
      SpinUpUpperHub(shooter), // 1.775 seconds
      SequentialCommandGroup(
        // three ball
        ShootCommand(shooter, feeder).withTimeout(0.5),
        ResetPoseCommand(drivetrain, threeBallRightStartFasterTrajectory.startingPose),
        ParallelCommandGroup(
          WaitCommand(1.0)
            .andThen(
              (IntakeBallsCommand(intake).alongWith(FeederSerialize(feeder))).withTimeout(
                2.5
              )
            ),
          DrivePathCommand(
            drivetrain, threeBallRightStartFasterTrajectory, resetPose = false
          )
        ),
        ShootCommand(shooter, feeder).withTimeout(1.25),

        // four and five ball
        ParallelCommandGroup(
          WaitCommand(2.0)
            .andThen(IntakeBallsCommand(intake).alongWith(FeederSerialize(feeder)))
            .withTimeout(2.5),
          DrivePathCommand(drivetrain, fiveBallRightStartTrajectory, resetPose = false)
        ),
        ShootCommand(shooter, feeder).withTimeout(1.25)
      )
    )
  }
}
