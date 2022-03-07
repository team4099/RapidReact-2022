package com.team4099.robot2022.auto.commands

import com.team4099.lib.units.derived.Angle
import com.team4099.robot2022.commands.drivetrain.OpenLoopReverseCommand
import com.team4099.robot2022.commands.drivetrain.ResetGyroCommand
import com.team4099.robot2022.commands.shooter.ShootCommand
import com.team4099.robot2022.commands.shooter.SpinUpNearCommand
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand

class OneBallFenderShotThenTaxi(
  val drivetrain: Drivetrain,
  val feeder: Feeder,
  val shooter: Shooter,
  val startingAngle: Angle
) : SequentialCommandGroup() {
  init {
    addCommands(
        ResetGyroCommand(drivetrain, startingAngle),
        ParallelDeadlineGroup(
            WaitCommand(3.0), SpinUpNearCommand(shooter).andThen(ShootCommand(shooter, feeder))),
        OpenLoopReverseCommand(drivetrain).withTimeout(4.0))
  }
}
