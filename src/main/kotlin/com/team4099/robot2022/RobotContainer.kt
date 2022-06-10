package com.team4099.robot2022

import com.team4099.lib.smoothDeadband
import com.team4099.robot2022.auto.AutonomousSelector
import com.team4099.robot2022.commands.climber.ClimbSequenceCommand
import com.team4099.robot2022.commands.climber.ExtendPivotArmCommand
import com.team4099.robot2022.commands.climber.ExtendTelescopingArmCommand
import com.team4099.robot2022.commands.climber.OpenLoopClimbCommand
import com.team4099.robot2022.commands.climber.PivotArmIdleCommand
import com.team4099.robot2022.commands.climber.RetractPivotArmCommand
import com.team4099.robot2022.commands.climber.RetractTelescopingArmCommand
import com.team4099.robot2022.commands.climber.SpoolLeftDownCommand
import com.team4099.robot2022.commands.climber.SpoolLeftUpCommand
import com.team4099.robot2022.commands.climber.SpoolRightDownCommand
import com.team4099.robot2022.commands.climber.SpoolRightUpCommand
import com.team4099.robot2022.commands.climber.TelescopingIdleCommand
import com.team4099.robot2022.commands.drivetrain.DriveBrakeModeCommand
import com.team4099.robot2022.commands.drivetrain.ResetGyroPitchCommand
import com.team4099.robot2022.commands.drivetrain.ResetGyroYawCommand
import com.team4099.robot2022.commands.drivetrain.TeleopDriveCommand
import com.team4099.robot2022.commands.feeder.FeederCommand
import com.team4099.robot2022.commands.feeder.FeederSerialize
import com.team4099.robot2022.commands.feeder.FeederSerializeIdleCommand
import com.team4099.robot2022.commands.feeder.ResetBallCountCommand
import com.team4099.robot2022.commands.intake.IntakeBallsCommand
import com.team4099.robot2022.commands.intake.IntakeIdleCommand
import com.team4099.robot2022.commands.intake.ReverseIntakeCommand
import com.team4099.robot2022.commands.led.LedCommand
import com.team4099.robot2022.commands.pneumatics.PneumaticIdleCommand
import com.team4099.robot2022.commands.pneumatics.UseLowThresholdClimbCommand
import com.team4099.robot2022.commands.shooter.ShootCommand
import com.team4099.robot2022.commands.shooter.ShooterIdleCommand
import com.team4099.robot2022.commands.shooter.ShooterUnjamCommand
import com.team4099.robot2022.commands.shooter.SpinUpLowerHub
import com.team4099.robot2022.commands.shooter.SpinUpUpperHub
import com.team4099.robot2022.config.ControlBoard
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.FeederConstants
import com.team4099.robot2022.subsystems.climber.PivotClimber
import com.team4099.robot2022.subsystems.climber.PivotClimberIO
import com.team4099.robot2022.subsystems.climber.PivotClimberIOReal
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
import com.team4099.robot2022.subsystems.led.LedIOCandle
import com.team4099.robot2022.subsystems.pneumatics.Pneumatic
import com.team4099.robot2022.subsystems.pneumatics.PneumaticIO
import com.team4099.robot2022.subsystems.pneumatics.PneumaticsIOReal
import com.team4099.robot2022.subsystems.shooter.Shooter
import com.team4099.robot2022.subsystems.shooter.ShooterIO
import com.team4099.robot2022.subsystems.shooter.ShooterIOReal

object RobotContainer {
  private val drivetrain: Drivetrain
  private val intake: Intake
  private val shooter: Shooter
  private val feeder: Feeder
  private val telescopingClimber: TelescopingClimber
  private val pivotClimber: PivotClimber
  private val led: Led
  private val pneumatic: Pneumatic

  init {
    if (Constants.Tuning.type == Constants.Tuning.RobotType.REAL) {
      drivetrain = Drivetrain(DrivetrainIOReal)
      intake = Intake(IntakeIOReal)
      shooter = Shooter(ShooterIOReal)
      feeder = Feeder(FeederIOReal)
      telescopingClimber = TelescopingClimber(TelescopingClimberIOReal)
      pivotClimber = PivotClimber(PivotClimberIOReal)
      led = Led(LedIOCandle)
      pneumatic = Pneumatic(PneumaticsIOReal)
    } else {
      drivetrain = Drivetrain(object : DrivetrainIO {})
      intake = Intake(object : IntakeIO {})
      shooter = Shooter(object : ShooterIO {})
      feeder = Feeder(object : FeederIO {})
      telescopingClimber = TelescopingClimber(object : TelescopingClimberIO {})
      pivotClimber = PivotClimber(object : PivotClimberIO {})
      led = Led(object : LedIO {})
      pneumatic = Pneumatic(object : PneumaticIO {})
    }
  }

  fun zeroSteering() {
    drivetrain.zeroSteering()
  }

  fun mapDefaultCommands() {
    drivetrain.defaultCommand =
      TeleopDriveCommand(
        { ControlBoard.strafe.smoothDeadband(Constants.Joysticks.THROTTLE_DEADBAND) },
        { ControlBoard.forward.smoothDeadband(Constants.Joysticks.THROTTLE_DEADBAND) },
        { ControlBoard.turn.smoothDeadband(Constants.Joysticks.TURN_DEADBAND) },
        { ControlBoard.robotOriented },
        drivetrain
      )
    intake.defaultCommand = IntakeIdleCommand(intake)
    shooter.defaultCommand = ShooterIdleCommand(shooter)
    feeder.defaultCommand = FeederSerializeIdleCommand(feeder)
    telescopingClimber.defaultCommand = TelescopingIdleCommand(telescopingClimber)
    pivotClimber.defaultCommand = PivotArmIdleCommand(pivotClimber)
    //    PivotClimber.defaultCommand = PivotIdleCommand()
    led.defaultCommand =
      LedCommand(led, intake, shooter, feeder, telescopingClimber, pivotClimber, pneumatic)
    pneumatic.defaultCommand = PneumaticIdleCommand(pneumatic)
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
    ControlBoard.resetGyro.whileActiveOnce(ResetGyroYawCommand(drivetrain))

    ControlBoard.startShooter.whileActiveOnce(
      DriveBrakeModeCommand(drivetrain)
        .alongWith(SpinUpUpperHub(shooter).andThen(ShootCommand(shooter, feeder)))
    )
    ControlBoard.startShooterLower.whileActiveOnce(
      DriveBrakeModeCommand(drivetrain)
        .alongWith(SpinUpLowerHub(shooter).andThen(ShootCommand(shooter, feeder)))
    )
    ControlBoard.shooterUnjam.whileActiveOnce(ShooterUnjamCommand(shooter))

    ControlBoard.runIntake.whileActiveContinuous(
      IntakeBallsCommand(intake).alongWith(FeederSerialize(feeder))
    )
    //    ControlBoard.runFeederIn.whileActiveOnce(FeederSerialize(feeder))
    //    ControlBoard.resetBallCount.whileActiveOnce(ResetBallCountCommand(feeder))
    ControlBoard.outTake.whileActiveContinuous(
      ReverseIntakeCommand(intake)
        .alongWith(FeederCommand(feeder, FeederConstants.FeederState.BACKWARD_FLOOR))
    )
    ControlBoard.resetBallCount.whileActiveOnce(ResetBallCountCommand(feeder))

    ControlBoard.extendTelescoping.whileActiveOnce(ExtendTelescopingArmCommand(telescopingClimber))
    ControlBoard.retractTelescoping.whileActiveOnce(
      RetractTelescopingArmCommand(telescopingClimber)
    )
    ControlBoard.extendPivot.whileActiveOnce(ExtendPivotArmCommand(pivotClimber))
    ControlBoard.retractPivot.whileActiveOnce(RetractPivotArmCommand(pivotClimber))
    ControlBoard.startClimbSequence.whileActiveOnce(
      //      PneumaticClimbCheckCommand(pneumatic)
      //        .andThen(
      UseLowThresholdClimbCommand(pneumatic)
        .alongWith(
          ResetGyroPitchCommand(drivetrain)
            .andThen(
              ClimbSequenceCommand(telescopingClimber, pivotClimber)
                .andThen(
                  ClimbSequenceCommand(telescopingClimber, pivotClimber)
                    .andThen(RetractTelescopingArmCommand(telescopingClimber))
                )
            )
        )
      //        )
    )

    //
    // ControlBoard.advanceAndClimb.whileActiveOnce(AdvanceClimberCommand().andThen(RunClimbCommand()))
    //        ControlBoard.climbWithoutAdvance.whileActiveOnce(RunClimbCommand())

    ControlBoard.leftSpoolDown.whileActiveContinuous(SpoolLeftDownCommand(telescopingClimber))
    ControlBoard.rightSpoolDown.whileActiveContinuous(SpoolRightDownCommand(telescopingClimber))
    ControlBoard.leftSpoolUp.whileActiveContinuous(SpoolLeftUpCommand(telescopingClimber))
    ControlBoard.rightSpoolUp.whileActiveContinuous(SpoolRightUpCommand(telescopingClimber))
  }

  fun mapTestControls() {}

  fun getAutonomousCommand() =
    AutonomousSelector.getCommand(
      drivetrain, intake, feeder, shooter, telescopingClimber, pivotClimber
    )

  fun logOperatorController() = ControlBoard.logOperatorController()
  fun logDriverController() = ControlBoard.logDriverController()
}
