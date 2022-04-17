package com.team4099.robot2022.auto.mode

import com.team4099.lib.pathfollow.Trajectory
import com.team4099.lib.pathfollow.trajectoryFromPathPlanner
import com.team4099.lib.units.base.Time
import com.team4099.lib.units.base.inSeconds
import com.team4099.robot2022.auto.PathStore
import com.team4099.robot2022.commands.drivetrain.DrivePathCommand
import com.team4099.robot2022.commands.shooter.ShootCommand
import com.team4099.robot2022.commands.shooter.SpinUpUpperHub
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.intake.Intake
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import org.littletonrobotics.junction.Logger

class OneBallLeftLeftMode(
  val drivetrain: Drivetrain,
  val intake: Intake,
  val feeder: Feeder,
  val shooter: Shooter,
  val waitTime: Time
) : SequentialCommandGroup() {

  private val oneBallLeftTarmacLeftBump: Trajectory
  private val oneBallLeftTarmacLeftToFender: Trajectory

  private var redAllianceCheck: Boolean = false

  init {
    if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
      oneBallLeftTarmacLeftBump = trajectoryFromPathPlanner(PathStore.redOneBallLeftTarmacLeftBump)
      oneBallLeftTarmacLeftToFender =
        trajectoryFromPathPlanner(PathStore.redOneBallLeftTarmacLeftShoot)
      redAllianceCheck = true
    } else {
      oneBallLeftTarmacLeftBump = trajectoryFromPathPlanner(PathStore.blueOneBallLeftTarmacLeftBump)
      oneBallLeftTarmacLeftToFender =
        trajectoryFromPathPlanner(PathStore.blueOneBallLeftTarmacLeftShoot)
    }

    addCommands(
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
