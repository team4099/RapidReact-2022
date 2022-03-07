package com.team4099.robot2022.commands.drivetrain

import com.team4099.robot2022.config.constants.DrivetrainConstants
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import edu.wpi.first.wpilibj2.command.CommandBase
import kotlin.math.sign

class OpenLoopDriveCommand(
  val driveX: () -> Double,
  val driveY: () -> Double,
  val turn: () -> Double
) : CommandBase() {

  init {
    addRequirements(Drivetrain)
  }

  override fun initialize() {}

  override fun execute() {
    val speed =
        Pair(
            //          0.0.meters.perSecond,
            DrivetrainConstants.DRIVE_SETPOINT_MAX * driveX() * driveX() * sign(driveX()),
            DrivetrainConstants.DRIVE_SETPOINT_MAX * driveY() * driveY() * sign(driveY()))
    val direction = DrivetrainConstants.TURN_SETPOINT_MAX * turn() * turn() * sign(turn())
    //      0.0.radians.perSecond
    Drivetrain.setOpenLoop(direction, speed, fieldOriented = true)
  }
  override fun isFinished(): Boolean {
    return false
  }
}
