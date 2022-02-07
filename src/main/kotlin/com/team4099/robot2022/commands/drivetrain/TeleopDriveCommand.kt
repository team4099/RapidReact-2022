package com.team4099.robot2022.commands.drivetrain

import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.subsystems.Drivetrain
import edu.wpi.first.wpilibj2.command.CommandBase
import kotlin.math.sign

class TeleopDriveCommand(
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
            Constants.Drivetrain.DRIVE_SETPOINT_MAX * driveX() * driveX() * sign(driveX()),
            Constants.Drivetrain.DRIVE_SETPOINT_MAX * driveY() * driveY() * sign(driveY()))
    val direction = Constants.Drivetrain.TURN_SETPOINT_MAX * turn() * turn() * sign(turn())
    Drivetrain.set(direction, speed, fieldOriented = true)
  }
  override fun isFinished(): Boolean {
    return false
  }
}
