package com.team4099.robot2022

import com.team4099.lib.logging.Logger
import com.team4099.lib.smoothDeadband
import com.team4099.robot2021.subsystems.Intake
import com.team4099.robot2022.auto.AutonomousSelector
import com.team4099.robot2022.commands.climber.SpoolLeftDownCommand
import com.team4099.robot2022.commands.climber.SpoolRightDownCommand
import com.team4099.robot2022.commands.climber.SpootLeftUpCommand
import com.team4099.robot2022.commands.climber.TelescopingIdleCommand
import com.team4099.robot2022.commands.drivetrain.OpenLoopDriveCommand
import com.team4099.robot2022.commands.drivetrain.ResetGyroCommand
import com.team4099.robot2022.commands.feeder.FeederCommand
import com.team4099.robot2022.commands.feeder.FeederIdleCommand
import com.team4099.robot2022.commands.feeder.FeederSerialize
import com.team4099.robot2022.commands.intake.IntakeBallsCommand
import com.team4099.robot2022.commands.intake.IntakeIdleCommand
import com.team4099.robot2022.commands.intake.PrepareClimbCommand
import com.team4099.robot2022.commands.intake.ReverseIntakeCommand
import com.team4099.robot2022.commands.shooter.ShootCommand
import com.team4099.robot2022.commands.shooter.ShooterIdleCommand
import com.team4099.robot2022.commands.shooter.SpinUpFarCommand
import com.team4099.robot2022.commands.shooter.SpinUpNearCommand
import com.team4099.robot2022.config.ControlBoard
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.subsystems.TelescopingClimber
import edu.wpi.first.wpilibj.Compressor
import com.team4099.robot2022.config.constants.FeederConstants
import com.team4099.robot2022.subsystems.Drivetrain
import com.team4099.robot2022.subsystems.Feeder
import com.team4099.robot2022.subsystems.Shooter
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.PneumaticsModuleType
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
  }

  fun mapDefaultCommands() {
    Drivetrain.defaultCommand =
        OpenLoopDriveCommand(
            { ControlBoard.strafe.smoothDeadband(Constants.Joysticks.THROTTLE_DEADBAND) },
            { ControlBoard.forward.smoothDeadband(Constants.Joysticks.THROTTLE_DEADBAND) },
            { ControlBoard.turn.smoothDeadband(Constants.Joysticks.TURN_DEADBAND) })
    Shooter.defaultCommand = ShooterIdleCommand()
    Intake.defaultCommand = IntakeIdleCommand()
    Feeder.defaultCommand = FeederIdleCommand()
    TelescopingClimber.defaultCommand = TelescopingIdleCommand()
    //    PivotClimber.defaultCommand = PivotIdleCommand()
  }

  fun mapTeleopControls() {
    ControlBoard.resetGyro.whileActiveOnce(ResetGyroCommand())

    ControlBoard.startShooter.whileActiveOnce(SpinUpNearCommand().andThen(ShootCommand()))
    ControlBoard.startShooterFar.whileActiveOnce(SpinUpFarCommand().andThen(ShootCommand()))

    ControlBoard.runIntake.whileActiveContinuous(IntakeBallsCommand().alongWith(FeederSerialize()))
    ControlBoard.prepareClimb.whileActiveContinuous(PrepareClimbCommand())
    ControlBoard.outTake
        .whileActiveContinuous(
            ReverseIntakeCommand().alongWith(
                FeederCommand(FeederConstants.FeederState.BACKWARD_FLOOR)))

    // ControlBoard.advanceAndClimb.whileActiveOnce(AdvanceClimberCommand().andThen(RunClimbCommand()))
    //    ControlBoard.climbWithoutAdvance.whileActiveOnce(RunClimbCommand())
  }

  fun mapTestControls() {
    ControlBoard.leftSpoolDown.whileActiveContinuous(SpoolLeftDownCommand())
    ControlBoard.rightSpoolDown.whileActiveContinuous(SpoolRightDownCommand())
    ControlBoard.leftSpoolUp.whileActiveContinuous(SpootLeftUpCommand())
    ControlBoard.rightSpoolUp.whileActiveContinuous(SpoolRightDownCommand())
  }

  override fun robotInit() {
    Logger.startLogging()
    Logger.addEvent("Robot", "Robot Construction (running on $robotName)")
    Logger.addSource("Robot", "Battery Voltage", RobotController::getBatteryVoltage)

    addPeriodic({ Logger.saveLogs() }, 0.08, 0.01)

    mapDefaultCommands()

    Drivetrain.zeroSensors()
    val compressor = Compressor(PneumaticsModuleType.REVPH)
    //    compressor.enableAnalog(60.0, 120.0)
    compressor.disable()
  }

  override fun autonomousInit() {
    // autonomousCommand.schedule()
    autonomousSelector.getCommand().schedule()
  }

  override fun disabledInit() {
    autonomousSelector.getCommand().cancel()
    // autonomousCommand.cancel()
  }

  override fun teleopInit() {
    mapTeleopControls()
    autonomousSelector.getCommand().cancel()
    // autonomousCommand.cancel()
  }

  override fun testInit() {
    mapTestControls()
    autonomousSelector.getCommand().cancel()
  }

  override fun robotPeriodic() {
    CommandScheduler.getInstance().run()
    Logger.updateShuffleboard()
  }
}
