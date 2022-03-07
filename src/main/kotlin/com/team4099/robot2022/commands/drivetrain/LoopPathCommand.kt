package com.team4099.robot2022.commands.drivetrain

import com.team4099.lib.pathfollow.Trajectory
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import edu.wpi.first.wpilibj2.command.CommandBase

class LoopPathCommand(vararg trajectories: Trajectory) : CommandBase() {
  private val loopedDriveCommands = trajectories.map { AutoDriveCommand(it) }
  private var pathIndex = 0

  init {
    addRequirements(Drivetrain)
  }

  override fun initialize() {
    pathIndex = 0
    loopedDriveCommands[0].initialize()
  }

  override fun execute() {
    if (loopedDriveCommands[pathIndex].isFinished) {
      pathIndex++
      pathIndex %= loopedDriveCommands.size
      loopedDriveCommands[pathIndex].initialize()
    }
    loopedDriveCommands[pathIndex].execute()
  }
}
