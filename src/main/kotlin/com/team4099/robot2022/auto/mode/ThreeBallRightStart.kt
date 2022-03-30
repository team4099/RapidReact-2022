package com.team4099.robot2022.auto.mode

import com.team4099.lib.pathfollow.trajectoryFromPathPlanner
import com.team4099.robot2022.auto.PathStore
import com.team4099.robot2022.commands.drivetrain.ResetPoseCommand
import com.team4099.robot2022.commands.feeder.FeederSerialize
import com.team4099.robot2022.commands.intake.IntakeBallsCommand
import com.team4099.robot2022.commands.shooter.ShootCommand
import com.team4099.robot2022.commands.shooter.SpinUpLowerHub
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.intake.Intake
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand

class ThreeBallRightStart(
  val drivetrain: Drivetrain,
  val intake: Intake,
  val feeder: Feeder,
  val shooter: Shooter
) : SequentialCommandGroup() {

  val trajectory = trajectoryFromPathPlanner(PathStore.threeBallRightStartPath)

  init {
    addCommands(
      SpinUpLowerHub(shooter).andThen(ShootCommand(shooter, feeder).withTimeout(1.0)),
      ResetPoseCommand(drivetrain, trajectory.startingPose),
      ParallelCommandGroup(
        WaitCommand(2.0)
          .andThen(
            (IntakeBallsCommand(intake).alongWith(FeederSerialize(feeder))).withTimeout(
              5.0
            )
          ),
      ),
      SpinUpLowerHub(shooter).andThen(ShootCommand(shooter, feeder).withTimeout(3.0))
    )
  }
}
