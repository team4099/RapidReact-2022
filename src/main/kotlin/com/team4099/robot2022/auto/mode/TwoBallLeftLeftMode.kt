package com.team4099.robot2022.auto.mode

import com.team4099.lib.pathfollow.Trajectory
import com.team4099.lib.pathfollow.trajectoryFromPathPlanner
import com.team4099.lib.units.base.Time
import com.team4099.lib.units.base.inSeconds
import com.team4099.robot2022.auto.PathStore
import com.team4099.robot2022.commands.drivetrain.DrivePathCommand
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

class TwoBallLeftLeftMode(
  val drivetrain: Drivetrain,
  val intake: Intake,
  val feeder: Feeder,
  val shooter: Shooter,
  waitTime: Time
) : SequentialCommandGroup() {

  private val oneBallLeftTarmacLeftBump: Trajectory
  private val oneBallLeftTarmacLeftToFender: Trajectory
  private val twoBallLeftTarmac: Trajectory

  private var redAllianceCheck: Boolean = false

  init {
    if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
      oneBallLeftTarmacLeftBump = trajectoryFromPathPlanner(PathStore.redOneBallLeftTarmacLeftBump)
      oneBallLeftTarmacLeftToFender =
        trajectoryFromPathPlanner(PathStore.redOneBallLeftTarmacLeftShoot)
      twoBallLeftTarmac = trajectoryFromPathPlanner(PathStore.redTwoBallLeftFender)
      redAllianceCheck = true
    } else {
      oneBallLeftTarmacLeftBump = trajectoryFromPathPlanner(PathStore.blueOneBallLeftTarmacLeftBump)
      oneBallLeftTarmacLeftToFender =
        trajectoryFromPathPlanner(PathStore.blueOneBallLeftTarmacLeftShoot)
      twoBallLeftTarmac = trajectoryFromPathPlanner(PathStore.blueTwoBallLeftFender)
    }

    addCommands(
      DrivePathCommand(drivetrain, oneBallLeftTarmacLeftBump, resetPose = false),
      WaitCommand(waitTime.inSeconds),
      DrivePathCommand(drivetrain, oneBallLeftTarmacLeftToFender, resetPose = true),
      SpinUpUpperHub(shooter).andThen(AutoShootCommand(shooter, feeder).withTimeout(1.5)),
      ParallelCommandGroup(
        DrivePathCommand(drivetrain, twoBallLeftTarmac, resetPose = true)
          .deadlineWith(FeederSerialize(feeder)),
        WaitCommand(1.0).andThen((IntakeBallsCommand(intake).withTimeout(1.0)))
      ),
      SpinUpUpperHub(shooter).andThen(AutoShootCommand(shooter, feeder).withTimeout(1.5)),
    )
  }

  override fun execute() {
    super.execute()
    Logger.getInstance().recordOutput("ActiveCommands/TwoBallLeftLeftMode", true)
  }
}
