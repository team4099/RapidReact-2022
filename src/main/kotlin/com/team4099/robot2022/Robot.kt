package com.team4099.robot2022

import com.team4099.lib.smoothDeadband
import com.team4099.robot2021.subsystems.Intake
import com.team4099.robot2022.auto.AutonomousSelector
import com.team4099.robot2022.commands.climber.OpenLoopClimbCommand
import com.team4099.robot2022.commands.climber.OpenLoopExtendClimberCommand
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
import com.team4099.robot2022.config.constants.FeederConstants
import com.team4099.robot2022.subsystems.Drivetrain
import com.team4099.robot2022.subsystems.Feeder
import com.team4099.robot2022.subsystems.LED
import com.team4099.robot2022.subsystems.Shooter
import com.team4099.robot2022.subsystems.TelescopingClimber
import edu.wpi.first.wpilibj.Compressor
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj.RobotController
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.littletonrobotics.junction.LoggedRobot
import org.littletonrobotics.junction.Logger
import org.littletonrobotics.junction.inputs.LoggedNetworkTables
import org.littletonrobotics.junction.io.ByteLogReceiver
import org.littletonrobotics.junction.io.ByteLogReplay
import org.littletonrobotics.junction.io.LogSocketServer
import kotlin.math.pow

object Robot : LoggedRobot() {
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
    LED
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

    ControlBoard.extendTelescoping.whileActiveContinuous(OpenLoopExtendClimberCommand())
    ControlBoard.retractTelescoping.whileActiveContinuous(OpenLoopClimbCommand())

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
    // set up AdvantageKit logging
    // allow running faster than real time when replaying logs
    setUseTiming(isReal())

    // log smart dashboard values (otherwise nothing is logged by default
    LoggedNetworkTables.getInstance().addTable("/SmartDashboard")

    // metadata value (not timed -- just metadata for given log file)
    Logger.getInstance().recordMetadata("RobotName", robotName.name)

    if (isReal()) {
      // log to USB stick and network for real time data viewing on AdvantageScope
      Logger.getInstance().addDataReceiver(ByteLogReceiver("/media/sda1/"))
      Logger.getInstance().addDataReceiver(LogSocketServer(5800))
    } else {
      // if in replay mode get file path from command line and read log file
      val path = ByteLogReplay.promptForPath()
      Logger.getInstance().setReplaySource(ByteLogReplay(path))
      Logger.getInstance().addDataReceiver(ByteLogReceiver(ByteLogReceiver.addPathSuffix(path, "_sim")))
    }
    Logger.getInstance().start()


    mapDefaultCommands()

    val compressor = Compressor(PneumaticsModuleType.REVPH)
    compressor.enableAnalog(60.0, 120.0)
    //    compressor.disable()

    Drivetrain.zeroSensors()
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
  }
}
