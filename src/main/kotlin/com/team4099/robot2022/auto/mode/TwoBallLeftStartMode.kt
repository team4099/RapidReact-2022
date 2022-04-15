package com.team4099.robot2022.auto.mode

import com.team4099.lib.pathfollow.Trajectory
import com.team4099.lib.pathfollow.trajectoryFromPathPlanner
import com.team4099.lib.units.base.Time
import com.team4099.lib.units.base.inSeconds
import com.team4099.robot2022.auto.PathStore
import com.team4099.robot2022.commands.drivetrain.DrivePathCommand
import com.team4099.robot2022.commands.drivetrain.ResetPoseCommand
import com.team4099.robot2022.commands.intake.IntakeBallsCommand
import com.team4099.robot2022.commands.intake.ReverseIntakeCommand
import com.team4099.robot2022.commands.shooter.ShootCommand
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

class TwoBallLeftStartMode(
  val drivetrain: Drivetrain,
  val intake: Intake,
  val feeder: Feeder,
  val shooter: Shooter,
  waitTime: Time
) : SequentialCommandGroup() {

  val trajectory: Trajectory
  val intakeOpponentCargoTrajectory: Trajectory

  private var redAllianceCheck: Boolean = false

  init {
    if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
      trajectory = trajectoryFromPathPlanner(PathStore.redTwoBallLeftStartPath)
      intakeOpponentCargoTrajectory =
        trajectoryFromPathPlanner(PathStore.redPickUpOpponentCargoPath)
      redAllianceCheck = true
    } else {
      trajectory = trajectoryFromPathPlanner(PathStore.blueTwoBallLeftStartPath)
      intakeOpponentCargoTrajectory =
        trajectoryFromPathPlanner(PathStore.bluePickUpOpponentCargoPath)
    }

    addCommands(
      ResetPoseCommand(drivetrain, trajectory.startingPose),
      ParallelCommandGroup(
        IntakeBallsCommand(intake).withTimeout(1.5),
        DrivePathCommand(drivetrain, trajectory, resetPose = false)
      ),
      SpinUpUpperHub(shooter).andThen(ShootCommand(shooter, feeder).withTimeout(2.0)),
      WaitCommand(waitTime.inSeconds),
      ParallelCommandGroup(
        DrivePathCommand(drivetrain, intakeOpponentCargoTrajectory, resetPose = true),
        SequentialCommandGroup(
          WaitCommand(1.0),
          IntakeBallsCommand(intake).withTimeout(1.0),
          WaitCommand(1.5),
          IntakeBallsCommand(intake).withTimeout(1.0)
        )
      ),
      ReverseIntakeCommand(intake).withTimeout(5.0)
    )
  }

  override fun execute() {
    super.execute()
    Logger.getInstance().recordOutput("ActiveCommands/TwoBallLeftStartMode", true)
    Logger.getInstance().recordOutput("Pathfollow/redAllianceCheck", redAllianceCheck)
    Logger.getInstance().recordOutput("Pathfollow/blueAllianceCheck", !redAllianceCheck)
  }
}
