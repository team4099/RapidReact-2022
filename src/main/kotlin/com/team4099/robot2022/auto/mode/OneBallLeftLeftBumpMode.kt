package com.team4099.robot2022.auto.mode

import com.team4099.lib.pathfollow.Trajectory
import com.team4099.lib.pathfollow.trajectoryFromPathPlanner
import com.team4099.lib.units.base.Time
import com.team4099.lib.units.base.inSeconds
import com.team4099.robot2022.auto.PathStore
import com.team4099.robot2022.commands.drivetrain.DrivePathCommand
import com.team4099.robot2022.commands.drivetrain.ResetPoseCommand
import com.team4099.robot2022.commands.shooter.ShootCommand
import com.team4099.robot2022.commands.shooter.SpinUpUpperHub
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.intake.Intake
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import org.littletonrobotics.junction.Logger

class OneBallLeftLeftBumpMode(
  val drivetrain: Drivetrain,
  val intake: Intake,
  val feeder: Feeder,
  val shooter: Shooter,
  val waitTime: Time
) : SequentialCommandGroup() {

  private val oneBallLeftTarmacLeftBump: Trajectory =
    trajectoryFromPathPlanner(PathStore.oneBallLeftTarmacLeftBump)
  private val oneBallLeftTarmacLeftToFender: Trajectory =
    trajectoryFromPathPlanner(PathStore.oneBallLeftTarmacLeftShoot)

  init {
    addCommands(
      ResetPoseCommand(drivetrain, oneBallLeftTarmacLeftBump.startingPose),
      DrivePathCommand(drivetrain, oneBallLeftTarmacLeftBump, resetPose = false),
      WaitCommand(waitTime.inSeconds),
      DrivePathCommand(drivetrain, oneBallLeftTarmacLeftToFender, resetPose = true),
      SpinUpUpperHub(shooter).andThen(ShootCommand(shooter, feeder))
    )
  }
  override fun execute() {
    super.execute()
    Logger.getInstance().recordOutput("ActiveCommands/OneBallLeftLeftMode", true)
  }
}
