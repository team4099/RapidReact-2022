package com.team4099.robot2022.auto.mode

import com.team4099.lib.pathfollow.Trajectory
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
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import org.littletonrobotics.junction.Logger

class ThreeBallRightStart(
  val drivetrain: Drivetrain,
  val intake: Intake,
  val feeder: Feeder,
  val shooter: Shooter
) : SequentialCommandGroup() {

  val trajectory: Trajectory

  private var redAllianceCheck: Boolean = false

  init {
    if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
      trajectory = trajectoryFromPathPlanner(PathStore.redThreeBallRightStartPath)
      redAllianceCheck = true
    } else {
      trajectory = trajectoryFromPathPlanner(PathStore.blueThreeBallRightStartPath)
    }

    addCommands(
      SpinUpUpperHub(shooter).andThen(ShootCommand(shooter, feeder).withTimeout(0.5)),
      ResetPoseCommand(drivetrain, trajectory.startingPose),
      ParallelCommandGroup(
        WaitCommand(2.0)
          .andThen(
            (IntakeBallsCommand(intake).alongWith(FeederSerialize(feeder))).withTimeout(
              5.0
            )
          ),
        DrivePathCommand(drivetrain, trajectory, resetPose = false)
      ),
      SpinUpUpperHub(shooter).andThen(ShootCommand(shooter, feeder).withTimeout(3.0))
    )
  }

  override fun execute() {
    Logger.getInstance().recordOutput("ActiveCommands/ThreeBallRightStart", true)
    Logger.getInstance().recordOutput("DriverStation/onRedAlliance", redAllianceCheck)
    Logger.getInstance().recordOutput("DriverStation/onBlueAlliance", !redAllianceCheck)
  }
}
