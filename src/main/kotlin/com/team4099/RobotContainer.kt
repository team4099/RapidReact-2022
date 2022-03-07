package com.team4099

import com.team4099.lib.smoothDeadband
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
import com.team4099.robot2022.subsystems.LED
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.intake.Intake
import com.team4099.robot2022.subsystems.intake.IntakeIO
import com.team4099.robot2022.subsystems.intake.IntakeIOReal
import com.team4099.robot2022.subsystems.shooter.Shooter
import com.team4099.robot2022.subsystems.shooter.ShooterIO
import com.team4099.robot2022.subsystems.shooter.ShooterIOReal
import edu.wpi.first.wpilibj.Compressor
import edu.wpi.first.wpilibj.PneumaticsModuleType

object RobotContainer {
  private val intake: Intake
  private val shooter: Shooter
  private var compressor: Compressor? = null

  init {
    if (Constants.Tuning.type == Constants.Tuning.RobotType.REAL) {
      compressor = Compressor(PneumaticsModuleType.REVPH)

      intake = Intake(IntakeIOReal)
      shooter = Shooter(ShooterIOReal)
    } else {
      intake = Intake(object : IntakeIO {})
      shooter = Shooter(object : ShooterIO {})
    }
  }

  fun startCompressor() {
    compressor?.enableAnalog(60.0, 120.0)
  }

  fun stopCompressor() {
    compressor?.disable()
  }

  fun mapDefaultCommands() {
    Drivetrain.defaultCommand =
        OpenLoopDriveCommand(
            { ControlBoard.strafe.smoothDeadband(Constants.Joysticks.THROTTLE_DEADBAND) },
            { ControlBoard.forward.smoothDeadband(Constants.Joysticks.THROTTLE_DEADBAND) },
            { ControlBoard.turn.smoothDeadband(Constants.Joysticks.TURN_DEADBAND) })
    intake.defaultCommand = IntakeIdleCommand(intake)
    shooter.defaultCommand = ShooterIdleCommand(shooter)
    Feeder.defaultCommand = FeederIdleCommand()
    TelescopingClimber.defaultCommand = TelescopingIdleCommand()
    //    PivotClimber.defaultCommand = PivotIdleCommand()
    LED
  }

  fun zeroSensors() {
    Drivetrain.zeroSensors()
  }

  fun mapTeleopControls() {
    ControlBoard.resetGyro.whileActiveOnce(ResetGyroCommand())

    ControlBoard.startShooter
        .whileActiveOnce(SpinUpNearCommand(shooter).andThen(ShootCommand(shooter)))
    ControlBoard.startShooterFar
        .whileActiveOnce(SpinUpFarCommand(shooter).andThen(ShootCommand(shooter)))

    ControlBoard.runIntake
        .whileActiveContinuous(IntakeBallsCommand(intake).alongWith(FeederSerialize()))
    ControlBoard.prepareClimb.whileActiveContinuous(PrepareClimbCommand(intake))
    ControlBoard.outTake
        .whileActiveContinuous(
            ReverseIntakeCommand(intake).alongWith(
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

  fun getAutonomousCommand() = AutonomousSelector.getCommand(Drivetrain, intake, Feeder, shooter)
}
