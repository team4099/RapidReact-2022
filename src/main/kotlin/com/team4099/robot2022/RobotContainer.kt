package com.team4099.robot2022

import com.team4099.lib.smoothDeadband
import com.team4099.robot2022.auto.AutonomousSelector
import com.team4099.robot2022.commands.climber.OpenLoopClimbCommand
import com.team4099.robot2022.commands.climber.OpenLoopExtendClimberCommand
import com.team4099.robot2022.commands.climber.SpoolLeftDownCommand
import com.team4099.robot2022.commands.climber.SpoolLeftUpCommand
import com.team4099.robot2022.commands.climber.SpoolRightDownCommand
import com.team4099.robot2022.commands.climber.SpoolRightUpCommand
import com.team4099.robot2022.commands.climber.TelescopingIdleCommand
import com.team4099.robot2022.commands.drivetrain.ResetGyroCommand
import com.team4099.robot2022.commands.drivetrain.TeleopDriveCommand
import com.team4099.robot2022.commands.feeder.FeederCommand
import com.team4099.robot2022.commands.feeder.FeederSerialize
import com.team4099.robot2022.commands.feeder.FeederSerializeIdleCommand
import com.team4099.robot2022.commands.intake.IntakeBallsCommand
import com.team4099.robot2022.commands.intake.IntakeIdleCommand
import com.team4099.robot2022.commands.intake.PrepareClimbCommand
import com.team4099.robot2022.commands.intake.ReverseIntakeCommand
import com.team4099.robot2022.commands.led.LedCommand
import com.team4099.robot2022.commands.shooter.ShootCommand
import com.team4099.robot2022.commands.shooter.ShooterIdleCommand
import com.team4099.robot2022.commands.shooter.SpinUpLowerHub
import com.team4099.robot2022.commands.shooter.SpinUpNearCommand
import com.team4099.robot2022.config.ControlBoard
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.FeederConstants
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import com.team4099.robot2022.subsystems.climber.TelescopingClimberIO
import com.team4099.robot2022.subsystems.climber.TelescopingClimberIOReal
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import com.team4099.robot2022.subsystems.drivetrain.DrivetrainIO
import com.team4099.robot2022.subsystems.drivetrain.DrivetrainIOReal
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.feeder.FeederIO
import com.team4099.robot2022.subsystems.feeder.FeederIOReal
import com.team4099.robot2022.subsystems.intake.Intake
import com.team4099.robot2022.subsystems.intake.IntakeIO
import com.team4099.robot2022.subsystems.intake.IntakeIOReal
import com.team4099.robot2022.subsystems.led.Led
import com.team4099.robot2022.subsystems.led.LedIO
import com.team4099.robot2022.subsystems.led.LedIOReal
import com.team4099.robot2022.subsystems.shooter.Shooter
import com.team4099.robot2022.subsystems.shooter.ShooterIO
import com.team4099.robot2022.subsystems.shooter.ShooterIOReal
import edu.wpi.first.wpilibj.Compressor
import edu.wpi.first.wpilibj.PneumaticsModuleType
import org.littletonrobotics.junction.Logger

object RobotContainer {
  private val drivetrain: Drivetrain
  private val intake: Intake
  private val shooter: Shooter
  private val feeder: Feeder
  private val telescopingClimber: TelescopingClimber
  private val led: Led
  private var compressor: Compressor? = null

  init {
    if (Constants.Tuning.type == Constants.Tuning.RobotType.REAL) {
      compressor = Compressor(PneumaticsModuleType.REVPH)

      drivetrain = Drivetrain(DrivetrainIOReal)
      intake = Intake(IntakeIOReal)
      shooter = Shooter(ShooterIOReal)
      feeder = Feeder(FeederIOReal)
      telescopingClimber = TelescopingClimber(TelescopingClimberIOReal)
      led = Led(LedIOReal)
    } else {
      drivetrain = Drivetrain(object : DrivetrainIO {})
      intake = Intake(object : IntakeIO {})
      shooter = Shooter(object : ShooterIO {})
      feeder = Feeder(object : FeederIO {})
      telescopingClimber = TelescopingClimber(object : TelescopingClimberIO {})
      led = Led(object : LedIO {})
    }
  }

  fun startCompressor() {
    compressor?.enableAnalog(60.0, 120.0)
  }

  fun stopCompressor() {
    compressor?.disable()
  }

  fun mapDefaultCommands() {
    drivetrain.defaultCommand =
        TeleopDriveCommand(
            { ControlBoard.strafe.smoothDeadband(Constants.Joysticks.THROTTLE_DEADBAND) },
            { ControlBoard.forward.smoothDeadband(Constants.Joysticks.THROTTLE_DEADBAND) },
            { ControlBoard.turn.smoothDeadband(Constants.Joysticks.TURN_DEADBAND) },
            { ControlBoard.robotOriented },
            drivetrain)
    intake.defaultCommand = IntakeIdleCommand(intake)
    shooter.defaultCommand = ShooterIdleCommand(shooter)
    feeder.defaultCommand = FeederSerializeIdleCommand(feeder)
    telescopingClimber.defaultCommand = TelescopingIdleCommand(telescopingClimber)
    //    PivotClimber.defaultCommand = PivotIdleCommand()
    led.defaultCommand = LedCommand(led, intake, shooter)
  }

  fun zeroSensors() {
    drivetrain.zeroSensors()
  }

  fun setDriveCoastMode() {
    drivetrain.swerveModules.forEach { it.setDriveBrakeMode(false) }
  }

  fun setDriveBrakeMode() {
    drivetrain.swerveModules.forEach { it.setDriveBrakeMode(true) }
  }

  fun mapTeleopControls() {
    ControlBoard.resetGyro.whileActiveOnce(ResetGyroCommand(drivetrain))

    ControlBoard.startShooter
        .whileActiveOnce(SpinUpNearCommand(shooter).andThen(ShootCommand(shooter, feeder)))
    ControlBoard.startShooterLower
        .whileActiveOnce(SpinUpLowerHub(shooter).andThen(ShootCommand(shooter, feeder)))

    ControlBoard.runIntake
        .whileActiveContinuous(IntakeBallsCommand(intake).alongWith(FeederSerialize(feeder)))
    ControlBoard.runFeederIn.whileActiveOnce(FeederSerialize(feeder))
    ControlBoard.prepareClimb.whileActiveContinuous(PrepareClimbCommand(intake))
    ControlBoard.outTake
        .whileActiveContinuous(
            ReverseIntakeCommand(intake).alongWith(
                FeederCommand(feeder, FeederConstants.FeederState.BACKWARD_FLOOR)))

    ControlBoard.extendTelescoping
        .whileActiveContinuous(OpenLoopExtendClimberCommand(telescopingClimber))
    ControlBoard.retractTelescoping.whileActiveContinuous(OpenLoopClimbCommand(telescopingClimber))

    // ControlBoard.advanceAndClimb.whileActiveOnce(AdvanceClimberCommand().andThen(RunClimbCommand()))
    //    ControlBoard.climbWithoutAdvance.whileActiveOnce(RunClimbCommand())

    ControlBoard.leftSpoolDown.whileActiveContinuous(SpoolLeftDownCommand(telescopingClimber))
    ControlBoard.rightSpoolDown.whileActiveContinuous(SpoolRightDownCommand(telescopingClimber))
    ControlBoard.leftSpoolUp.whileActiveContinuous(SpoolLeftUpCommand(telescopingClimber))
    ControlBoard.rightSpoolUp.whileActiveContinuous(SpoolRightUpCommand(telescopingClimber))
  }

  fun mapTestControls() {}

  fun getAutonomousCommand() =
      AutonomousSelector.getCommand(drivetrain, intake, feeder, shooter, telescopingClimber)

  fun logCompressor() {
    Logger.getInstance().recordOutput("Compressor/pressurePSI", compressor?.pressure)
    Logger.getInstance().recordOutput("Compressor/isRunning", compressor?.enabled())
  }
}
