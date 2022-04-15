package com.team4099.robot2022.auto.mode

import com.team4099.lib.pathfollow.Trajectory
import com.team4099.lib.pathfollow.trajectoryFromPathPlanner
import com.team4099.lib.units.base.Time
import com.team4099.lib.units.base.inSeconds
import com.team4099.robot2022.auto.PathStore
import com.team4099.robot2022.commands.drivetrain.DrivePathCommand
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

class OneBallFenderShotThenTaxiAndObstruct(
  val drivetrain: Drivetrain,
  val feeder: Feeder,
  val shooter: Shooter,
  val intake: Intake,
  waitTime: Time
) : SequentialCommandGroup() {

  private val oneBallFenderShotThenTaxi: Trajectory
  private var redAllianceCheck: Boolean = false

  init {
    if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
      oneBallFenderShotThenTaxi = trajectoryFromPathPlanner(PathStore.redPickUpOpponentCargoPath)
      redAllianceCheck = true
    } else {
      oneBallFenderShotThenTaxi = trajectoryFromPathPlanner(PathStore.bluePickUpOpponentCargoPath)
    }
    addCommands(
      SpinUpUpperHub(shooter).andThen(ShootCommand(shooter, feeder).withTimeout(2.0)),
      WaitCommand(waitTime.inSeconds),
      ParallelCommandGroup(
        DrivePathCommand(drivetrain, oneBallFenderShotThenTaxi, resetPose = false),
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
    Logger.getInstance().recordOutput("ActiveCommands/OneBallFenderShotThenTaxi", true)
  }
}
