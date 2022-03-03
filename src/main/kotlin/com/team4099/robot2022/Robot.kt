package com.team4099.robot2022

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.auto.AutonomousSelector
import com.team4099.robot2022.commands.climber.SpoolBothDownCommand
import com.team4099.robot2022.commands.climber.SpoolBothUpCommand
import com.team4099.robot2022.commands.climber.SpoolLeftDownCommand
import com.team4099.robot2022.commands.climber.SpoolRightDownCommand
import com.team4099.robot2022.commands.climber.SpootLeftUpCommand
import com.team4099.robot2022.commands.climber.TelescopingIdleCommand
import com.team4099.robot2022.config.ControlBoard
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.subsystems.TelescopingClimber
import edu.wpi.first.wpilibj.Compressor
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
    Logger.addEvent("Robot", "Robot Construction (running on $robotName)")
    Logger.addSource("Robot", "Battery Voltage", RobotController::getBatteryVoltage)

    Logger.startLogging()

    //    Drivetrain.defaultCommand =
    //        OpenLoopDriveCommand(
    //            { ControlBoard.strafe.smoothDeadband(Constants.Joysticks.THROTTLE_DEADBAND) },
    //            { ControlBoard.forward.smoothDeadband(Constants.Joysticks.THROTTLE_DEADBAND) },
    //            { ControlBoard.turn.smoothDeadband(Constants.Joysticks.TURN_DEADBAND) })
    //
    //    ControlBoard.resetGyro.whileActiveOnce(ResetGyroCommand())
    //
    //    Shooter.defaultCommand = ShooterIdleCommand()
    //    ControlBoard.startShooter.whileActiveOnce(SpinUpCommand().andThen(ShootCommand()))
    //    ControlBoard.startShooterFar.whileActiveOnce(SpinUpFarCommand().andThen(ShootCommand()))
    //
    //    Intake.defaultCommand = IntakeIdleCommand()
    //
    //
    // ControlBoard.runIntake.whileActiveContinuous(IntakeBallsCommand().alongWith(FeederSerialize()))
    //    ControlBoard.prepareClimb.whileActiveContinuous(PrepareClimbCommand())
    //    ControlBoard.outTake
    //        .whileActiveContinuous(
    //            ReverseIntakeCommand().alongWith(
    //                FeederCommand(FeederConstants.FeederState.BACKWARD_FLOOR)))
    //    Feeder.defaultCommand = FeederIdleCommand()

    TelescopingClimber.defaultCommand = TelescopingIdleCommand()
    ControlBoard.leftSpoolDown.whileActiveContinuous(SpoolLeftDownCommand())
    ControlBoard.rightSpoolDown.whileActiveContinuous(SpoolRightDownCommand())
    ControlBoard.leftSpoolUp.whileActiveContinuous(SpootLeftUpCommand())
    ControlBoard.rightSpoolUp.whileActiveContinuous(SpoolRightDownCommand())
    ControlBoard.extendTelescoping.whileActiveContinuous(SpoolBothUpCommand())
    ControlBoard.retractTelescoping.whileActiveContinuous(SpoolBothDownCommand())
    //    PivotClimber.defaultCommand = PivotIdleCommand()
    //
    //
    // ControlBoard.advanceAndClimb.whileActiveOnce(AdvanceClimberCommand().andThen(RunClimbCommand()))
    //    ControlBoard.climbWithoutAdvance.whileActiveOnce(RunClimbCommand())
  }

  override fun robotInit() {
    addPeriodic({ Logger.saveLogs() }, 0.08, 0.01)

    //    Drivetrain.zeroSensors()
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
    autonomousSelector.getCommand().cancel()
    // autonomousCommand.cancel()
  }

  override fun robotPeriodic() {
    CommandScheduler.getInstance().run()
    Logger.updateShuffleboard()
  }
}
