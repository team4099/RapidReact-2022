package com.team4099.robot2022

import com.team4099.lib.logging.Logger
import com.team4099.lib.smoothDeadband
import com.team4099.robot2022.commands.drivetrain.OpenLoopDriveCommand
import com.team4099.robot2022.commands.drivetrain.ResetGyroCommand
import com.team4099.robot2022.config.Constants
import com.team4099.robot2022.config.ControlBoard
import com.team4099.robot2022.subsystems.Drivetrain
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.RobotController
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.CommandScheduler
import kotlin.math.pow

object Robot : TimedRobot() {
  val robotName: Constants.Tuning.RobotName
  val autonomousSelector: AutonomousSelector = AutonomousSelector

  init {
    val robotId =
      Constants.Tuning
        .ROBOT_ID_PINS
        .withIndex()
        .map { (i, pin) -> if (DigitalInput(pin).get()) 0 else 2.0.pow(i).toInt() }
        .sum()
    robotName =
      Constants.Tuning.ROBOT_ID_MAP.getOrDefault(robotId, Constants.Tuning.RobotName.COMPETITION)
    Logger.addEvent("Robot", "Robot Construction (running on $robotName)")
    Logger.addSource("Robot", "Battery Voltage", RobotController::getBatteryVoltage)

    Logger.startLogging()

    Feeder.defaultCommand = FeederSerialize()

    ControlBoard.runFeederIn.whileActiveOnce(FeederCommand(FeederConstants.FeederState.FORWARD_ALL))
    ControlBoard.runFeederOut
        .whileActiveOnce(FeederCommand(FeederConstants.FeederState.BACKWARD_ALL))

    Drivetrain.defaultCommand =
        OpenLoopDriveCommand(
            { ControlBoard.strafe.smoothDeadband(Constants.Joysticks.THROTTLE_DEADBAND) },
            { ControlBoard.forward.smoothDeadband(Constants.Joysticks.THROTTLE_DEADBAND) },
            { ControlBoard.turn.smoothDeadband(Constants.Joysticks.TURN_DEADBAND) })

    ControlBoard.resetGyro.whileActiveOnce(ResetGyroCommand())

    // Shooter controls
    Shooter.defaultCommand = ShooterIdleCommand()
    ControlBoard.startShooter
      .whileActiveOnce(
        SequentialCommandGroup(
          SpinUpCommand(),
          // TODO: Add feed up command here
          ShootCommand()
        )
      )
    //    ControlBoard.idleShooter.whileActiveOnce(IdleCommand())

    Intake.defaultCommand = IntakeIdleCommand()

    ControlBoard.runIntake.whileActiveContinuous(IntakeBallsCommand())
    //        .whenInactive(LiftIntakeCommand())
    ControlBoard.prepareClimb.whileActiveContinuous(PrepareClimbCommand())
  }

  override fun robotInit() {
    Drivetrain.zeroSensors()
    val compressor = Compressor(PneumaticsModuleType.REVPH)
    compressor.enableAnalog(60.0, 120.0)
  }

  override fun autonomousInit() {
    // autonomousCommand.schedule()
    autonomousSelector.getCommand().schedule()
  }

  override fun disabledInit() {
    // autonomousCommand.cancel()
  }

  override fun teleopInit() {
    // autonomousCommand.cancel()
  }

  override fun robotPeriodic() {
    CommandScheduler.getInstance().run()
    Logger.saveLogs()
    Logger.updateShuffleboard()
  }
}
