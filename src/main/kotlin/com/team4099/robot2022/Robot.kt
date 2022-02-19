package com.team4099.robot2022

import com.team4099.lib.logging.Logger
import com.team4099.lib.smoothDeadband
import com.team4099.robot2021.subsystems.com.team4099.robot2022.subsystems.Intake
import com.team4099.robot2022.auto.AutonomousSelector
import com.team4099.robot2022.commands.drivetrain.OpenLoopDriveCommand
import com.team4099.robot2022.commands.drivetrain.ResetGyroCommand
import com.team4099.robot2022.commands.feeder.FeederIdleCommand
import com.team4099.robot2022.commands.feeder.FeederSerialize
import com.team4099.robot2022.commands.intake.IntakeBallsCommand
import com.team4099.robot2022.commands.intake.IntakeIdleCommand
import com.team4099.robot2022.commands.intake.PrepareClimbCommand
import com.team4099.robot2022.commands.shooter.ShootCommand
import com.team4099.robot2022.commands.shooter.ShooterIdleCommand
import com.team4099.robot2022.commands.shooter.SpinUpCommand
import com.team4099.robot2022.config.ControlBoard
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.subsystems.Drivetrain
import com.team4099.robot2022.subsystems.Feeder
import com.team4099.robot2022.subsystems.Shooter
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

    Drivetrain.defaultCommand =
        OpenLoopDriveCommand(
            { ControlBoard.strafe.smoothDeadband(Constants.Joysticks.THROTTLE_DEADBAND) },
            { ControlBoard.forward.smoothDeadband(Constants.Joysticks.THROTTLE_DEADBAND) },
            { ControlBoard.turn.smoothDeadband(Constants.Joysticks.TURN_DEADBAND) })

    ControlBoard.resetGyro.whileActiveOnce(ResetGyroCommand())

    Shooter.defaultCommand = ShooterIdleCommand()
    ControlBoard.startShooter.whileActiveOnce(SpinUpCommand().andThen(ShootCommand()))

    Intake.defaultCommand = IntakeIdleCommand()
    ControlBoard.runIntake.whileActiveContinuous(IntakeBallsCommand().alongWith(FeederSerialize()))
    //        .whenInactive(LiftIntakeCommand())
    ControlBoard.prepareClimb.whileActiveContinuous(PrepareClimbCommand())

    Feeder.defaultCommand = FeederIdleCommand()
    //
    // ControlBoard.runFeederIn.whileActiveOnce(FeederCommand(FeederConstants.FeederState.FORWARD_ALL))
    //    ControlBoard.runFeederOut
    //      .whileActiveOnce(FeederCommand(FeederConstants.FeederState.BACKWARD_ALL))
  }

  override fun robotInit() {
    //    Drivetrain.zeroSensors()
    //    val compressor = Compressor(PneumaticsModuleType.REVPH)
    //    compressor.enableAnalog(60.0, 120.0)
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
