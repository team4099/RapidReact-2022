package com.team4099.robot2022.auto.mode

import com.team4099.lib.pathfollow.Trajectory
import com.team4099.lib.pathfollow.trajectoryFromPathPlanner
import com.team4099.robot2022.auto.PathStore
import com.team4099.robot2022.commands.drivetrain.DrivePathCommand
import com.team4099.robot2022.commands.shooter.ShootCommand
import com.team4099.robot2022.commands.shooter.SpinUpUpperHub
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.littletonrobotics.junction.Logger

class OneBallFenderShotThenTaxi(
  val drivetrain: Drivetrain,
  val feeder: Feeder,
  val shooter: Shooter,
) : SequentialCommandGroup() {

  private val oneBallFenderShotThenTaxi: Trajectory
  private var redAllianceCheck: Boolean = false

  init {
    if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
      oneBallFenderShotThenTaxi = trajectoryFromPathPlanner(PathStore.redOneBallFenderShotThenTaxi)
      redAllianceCheck = true
    } else {
      oneBallFenderShotThenTaxi = trajectoryFromPathPlanner(PathStore.blueOneBallFenderShotThenTaxi)
    }
    addCommands(
      SpinUpUpperHub(shooter).andThen(ShootCommand(shooter, feeder).withTimeout(2.0)),
      DrivePathCommand(drivetrain, oneBallFenderShotThenTaxi, resetPose = false)
    )
  }

  override fun execute() {
    super.execute()
    Logger.getInstance().recordOutput("ActiveCommands/OneBallFenderShotThenTaxi", true)
  }
}
