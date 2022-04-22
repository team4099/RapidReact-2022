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

class OneBallFenderThenTaxi(
  val drivetrain: Drivetrain,
  val feeder: Feeder,
  val shooter: Shooter,
  val intake: Intake,
  waitTime: Time
) : SequentialCommandGroup() {

  private val oneBallFenderShotThenTaxi: Trajectory =
    trajectoryFromPathPlanner(PathStore.oneBallFenderThenTaxi)

  init {
    addCommands(
      SpinUpUpperHub(shooter).andThen(ShootCommand(shooter, feeder).withTimeout(1.25)),
      WaitCommand(waitTime.inSeconds),
      ResetPoseCommand(drivetrain, oneBallFenderShotThenTaxi.startingPose),
      DrivePathCommand(drivetrain, oneBallFenderShotThenTaxi, resetPose = false),
    )
  }

  override fun execute() {
    super.execute()
    Logger.getInstance().recordOutput("ActiveCommands/OneBallFenderThenTaxi", true)
  }
}
