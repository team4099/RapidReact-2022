package com.team4099.robot2022.auto.mode

import com.team4099.lib.pathfollow.Trajectory
import com.team4099.lib.pathfollow.trajectoryFromPathPlanner
import com.team4099.robot2022.auto.PathStore
import com.team4099.robot2022.commands.drivetrain.DrivePathCommand
import com.team4099.robot2022.commands.drivetrain.ResetPoseCommand
import com.team4099.robot2022.commands.feeder.FeederSerialize
import com.team4099.robot2022.commands.intake.IntakeBallsCommand
import com.team4099.robot2022.commands.shooter.AutoShootCommand
import com.team4099.robot2022.commands.shooter.SpinUpUpperHub
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.intake.Intake
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import org.littletonrobotics.junction.Logger

class FiveBallRightStart(
  val drivetrain: Drivetrain,
  val intake: Intake,
  val feeder: Feeder,
  val shooter: Shooter
) : SequentialCommandGroup() {

  private val threeBallRightStartFasterTrajectory: Trajectory
  private val fiveBallRightStartTrajectory: Trajectory

  private var redAllianceCheck: Boolean = false

  init {
    if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
      threeBallRightStartFasterTrajectory =
        trajectoryFromPathPlanner(PathStore.redThreeBallRightStartFasterPath)
      fiveBallRightStartTrajectory = trajectoryFromPathPlanner(PathStore.redFiveBallRightStart)
      redAllianceCheck = true
    } else {
      threeBallRightStartFasterTrajectory =
        trajectoryFromPathPlanner(PathStore.blueThreeBallRightStartFasterPath)
      fiveBallRightStartTrajectory = trajectoryFromPathPlanner(PathStore.blueFiveBallRightStart)
    }

    addCommands(
      SpinUpUpperHub(shooter)
        .deadlineWith(FeederSerialize(feeder))
        .andThen(AutoShootCommand(shooter, feeder).withTimeout(0.5)), // 1.775 seconds
      // three ball
      ResetPoseCommand(drivetrain, threeBallRightStartFasterTrajectory.startingPose),
      ParallelCommandGroup(
        WaitCommand(0.75).andThen((IntakeBallsCommand(intake)).withTimeout(2.25)),
        DrivePathCommand(drivetrain, threeBallRightStartFasterTrajectory, resetPose = false)
          .deadlineWith(FeederSerialize(feeder))
      ),
      SpinUpUpperHub(shooter)
        .deadlineWith(FeederSerialize(feeder))
        .andThen(AutoShootCommand(shooter, feeder).withTimeout(1.5)),

      // four and five ball
      ParallelCommandGroup(
        WaitCommand(1.0).andThen(IntakeBallsCommand(intake).withTimeout(3.5)),
        DrivePathCommand(drivetrain, fiveBallRightStartTrajectory, resetPose = true)
          .deadlineWith(FeederSerialize(feeder))
      ),
      SpinUpUpperHub(shooter)
        .deadlineWith(FeederSerialize(feeder))
        .andThen(AutoShootCommand(shooter, feeder).withTimeout(1.5))
    )
  }

  override fun execute() {
    super.execute()
    Logger.getInstance().recordOutput("ActiveCommands/FiveBallRightStart", true)
    //    Logger.getInstance().recordOutput("Pathfollow/redAllianceCheck", redAllianceCheck)
    //    Logger.getInstance().recordOutput("Pathfollow/blueAllianceCheck", !redAllianceCheck)
  }
}
