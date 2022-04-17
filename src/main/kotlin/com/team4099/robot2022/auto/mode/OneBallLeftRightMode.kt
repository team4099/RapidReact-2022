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

class OneBallLeftRightMode(
  val drivetrain: Drivetrain,
  val intake: Intake,
  val feeder: Feeder,
  val shooter: Shooter,
  val waitTime: Time
) : SequentialCommandGroup() {

  private val oneBallLeftTarmacRightBump: Trajectory
  private val oneBallLeftTarmacRightToFender: Trajectory

  private var redAllianceCheck: Boolean = false

  init {
    if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
      oneBallLeftTarmacRightBump =
        trajectoryFromPathPlanner(PathStore.redOneBallLeftTarmacRightBump)
      oneBallLeftTarmacRightToFender =
        trajectoryFromPathPlanner(PathStore.redOneBallLeftTarmacRightShoot)
      redAllianceCheck = true
    } else {
      oneBallLeftTarmacRightBump =
        trajectoryFromPathPlanner(PathStore.blueOneBallLeftTarmacRightBump)
      oneBallLeftTarmacRightToFender =
        trajectoryFromPathPlanner(PathStore.blueOneBallLeftTarmacRightShoot)
    }

    addCommands(
      DrivePathCommand(drivetrain, oneBallLeftTarmacRightBump, resetPose = false),
      WaitCommand(waitTime.inSeconds),
      DrivePathCommand(drivetrain, oneBallLeftTarmacRightToFender, resetPose = true),
      SpinUpUpperHub(shooter).andThen(ShootCommand(shooter, feeder))
    )
  }

  override fun execute() {
    super.execute()
    Logger.getInstance().recordOutput("ActiveCommands/OneBallLeftRightMode", true)
  }
}
