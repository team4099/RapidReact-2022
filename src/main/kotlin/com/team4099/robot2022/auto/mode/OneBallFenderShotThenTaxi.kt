package com.team4099.robot2022.auto.mode

import com.team4099.lib.units.derived.Angle
import com.team4099.robot2022.commands.drivetrain.OpenLoopReverseCommand
import com.team4099.robot2022.commands.drivetrain.ResetGyroCommand
import com.team4099.robot2022.commands.shooter.ShootCommand
import com.team4099.robot2022.commands.shooter.SpinUpNearCommand
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand

class OneBallFenderShotThenTaxi(var startingAngle: Angle) : SequentialCommandGroup() {
  init {
    addCommands(
        ResetGyroCommand(startingAngle),
        ParallelDeadlineGroup(WaitCommand(3.0), SpinUpNearCommand().andThen(ShootCommand())),
        OpenLoopReverseCommand().withTimeout(4.0))
  }
}
