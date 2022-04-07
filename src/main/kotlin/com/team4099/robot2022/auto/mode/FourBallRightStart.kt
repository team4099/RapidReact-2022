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

class FourBallRightStart(
  val drivetrain: Drivetrain,
  val intake: Intake,
  val feeder: Feeder,
  val shooter: Shooter
) : SequentialCommandGroup() {
  private val twoBallRightTrajectory: Trajectory
  private val fourBallRightTrajectory: Trajectory

  private var redAllianceCheck: Boolean = false

  init {
    if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
      twoBallRightTrajectory = trajectoryFromPathPlanner(PathStore.redTwoBallRightStartPath)
      fourBallRightTrajectory = trajectoryFromPathPlanner(PathStore.redFourBallRightStartPath)
      redAllianceCheck = true
    } else {
      twoBallRightTrajectory = trajectoryFromPathPlanner(PathStore.blueTwoBallRightStartPath)
      fourBallRightTrajectory = trajectoryFromPathPlanner(PathStore.blueFourBallRightStartPath)
    }

    addCommands(
      ResetPoseCommand(drivetrain, twoBallRightTrajectory.startingPose),
      ParallelCommandGroup(
        IntakeBallsCommand(intake).withTimeout(1.25),
        DrivePathCommand(drivetrain, twoBallRightTrajectory, resetPose = false)
          .deadlineWith(FeederSerialize(feeder)),
      ),
      SpinUpUpperHub(shooter).andThen(AutoShootCommand(shooter, feeder).withTimeout(1.5)),
      ParallelCommandGroup(
        WaitCommand(1.0).andThen((IntakeBallsCommand(intake).withTimeout(4.5))),
        DrivePathCommand(drivetrain, fourBallRightTrajectory, resetPose = false)
          .deadlineWith(FeederSerialize(feeder))
      ),
      SpinUpUpperHub(shooter).andThen(AutoShootCommand(shooter, feeder).withTimeout(1.5))
    )
  }

  override fun execute() {
    super.execute()
    Logger.getInstance().recordOutput("ActiveCommands/FourBallRightStart", true)
    Logger.getInstance().recordOutput("Pathfollow/redAllianceCheck", redAllianceCheck)
    Logger.getInstance().recordOutput("Pathfollow/blueAllianceCheck", !redAllianceCheck)
  }
}
