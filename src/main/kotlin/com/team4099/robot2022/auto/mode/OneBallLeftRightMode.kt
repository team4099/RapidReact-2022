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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import org.littletonrobotics.junction.Logger

class OneBallLeftRightMode(
  val drivetrain: Drivetrain,
  val intake: Intake,
  val feeder: Feeder,
  val shooter: Shooter,
  val waitTime: Time
) : SequentialCommandGroup() {

  private val oneBallLeftTarmacRightEject: Trajectory =
    trajectoryFromPathPlanner(PathStore.oneBallRightEjectOneTrajectory)
  private val oneBallLeftTarmacRightToFender: Trajectory =
    trajectoryFromPathPlanner(PathStore.oneBallRightShootTrajectory)

  init {
    addCommands(
      ResetPoseCommand(drivetrain, oneBallLeftTarmacRightEject.startingPose),
      ParallelCommandGroup(
        DrivePathCommand(drivetrain, oneBallLeftTarmacRightEject, resetPose = false),
        IntakeBallsCommand(intake).withTimeout(2.0)
      ),
      ReverseIntakeCommand(intake).withTimeout(3.0),
      WaitCommand(waitTime.inSeconds),
      DrivePathCommand(drivetrain, oneBallLeftTarmacRightToFender, resetPose = false),
      SpinUpUpperHub(shooter).andThen(ShootCommand(shooter, feeder))
    )
  }

  override fun execute() {
    super.execute()
    Logger.getInstance().recordOutput("ActiveCommands/OneBallLeftRightMode", true)
  }
}
