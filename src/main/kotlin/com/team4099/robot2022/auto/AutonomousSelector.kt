package com.team4099.robot2022.auto

import com.team4099.lib.units.base.Time
import com.team4099.lib.units.base.inSeconds
import com.team4099.lib.units.base.seconds
import com.team4099.robot2022.auto.mode.EightEightEightMode
import com.team4099.robot2022.auto.mode.FiveBallRightStart
import com.team4099.robot2022.auto.mode.FourBallRightStart
import com.team4099.robot2022.auto.mode.KhetarpalMode
import com.team4099.robot2022.auto.mode.OneBallFenderShotOneSteal
import com.team4099.robot2022.auto.mode.OneBallFenderThenTaxi
import com.team4099.robot2022.auto.mode.OneBallLeftLeftBumpMode
import com.team4099.robot2022.auto.mode.OneBallLeftLeftSteal
import com.team4099.robot2022.auto.mode.OneBallLeftRightMode
import com.team4099.robot2022.auto.mode.TestAutoPath
import com.team4099.robot2022.auto.mode.ThreeBallRightStart
import com.team4099.robot2022.auto.mode.ThreeBallRightStartFaster
import com.team4099.robot2022.auto.mode.TwoBallLeftMiddleOneStealMode
import com.team4099.robot2022.auto.mode.TwoBallLeftMiddleStartMode
import com.team4099.robot2022.auto.mode.TwoBallLeftMiddleTwoStealMode
import com.team4099.robot2022.auto.mode.TwoBallLeftRightSteal
import com.team4099.robot2022.commands.climber.TelescopingCharacterizationCommand
import com.team4099.robot2022.commands.drivetrain.DriveCharacterizeCommand
import com.team4099.robot2022.commands.shooter.ShooterCharacterizeCommand
import com.team4099.robot2022.subsystems.climber.PivotClimber
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.intake.Intake
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.WaitCommand

object AutonomousSelector {
  //  private var orientationChooser: SendableChooser<Angle> = SendableChooser()
  private var autonomousModeChooser: SendableChooser<AutonomousMode> = SendableChooser()
  private var waitBeforeCommandSlider: NetworkTableEntry
  private var secondaryWaitInAuto: NetworkTableEntry

  init {
    val autoTab = Shuffleboard.getTab("Auto settings")
    //    orientationChooser.setDefaultOption("Forward", 0.degrees)
    //    orientationChooser.addOption("Backwards", 180.degrees)
    //    orientationChooser.addOption("Left", 90.degrees)
    //    orientationChooser.addOption("Right", 270.degrees)
    //    autoTab.add("Starting Orientation", orientationChooser)
    autonomousModeChooser.addOption(
      "One Ball From Middle Bump Opponent Cargo", AutonomousMode.ONE_BALL_MIDDLE_BUMP
    )
    autonomousModeChooser.addOption(
      "One Ball From Middle Steal Opponent Cargo", AutonomousMode.ONE_BALL_MIDDLE_STEAL
    )
    autonomousModeChooser.addOption(
      "One Ball From Left Steal Opponent Cargo", AutonomousMode.ONE_BALL_RIGHT_STEAL
    )
    autonomousModeChooser.addOption(
      "One Ball From Fender Then Steal Middle Opponent Cargo",
      AutonomousMode.ONE_BALL_FENDER_THEN_MIDDLE_STEAL
    )
    autonomousModeChooser.addOption(
      "One Ball From Fender Then Taxi", AutonomousMode.ONE_BALL_FENDER_THEN_TAXI
    )
    autonomousModeChooser.addOption(
      "Two Ball From Left Tarmac Center Then One Steal",
      AutonomousMode.TWO_BALL_LEFT_MIDDLE_ONE_STEAL
    )
    autonomousModeChooser.addOption(
      "Two Ball From Left Tarmac Center No Steal", AutonomousMode.TWO_BALL_LEFT_MIDDLE_NO_STEAL
    )
    autonomousModeChooser.addOption(
      "Two Ball From Left Tarmac Center Then Two Steal",
      AutonomousMode.TWO_BALL_LEFT_MIDDLE_TWO_STEAL
    )
    autonomousModeChooser.addOption(
      "Two Ball From Left Tarmac Then One Steal", AutonomousMode.TWO_BALL_LEFT_RIGHT_ONE_STEAL
    )
    autonomousModeChooser.addOption(
      "Three Ball: Right Start Faster", AutonomousMode.THREE_BALL_RIGHT_START_FASTER
    )
    autonomousModeChooser.addOption(
      "Three Ball: Right Start", AutonomousMode.THREE_BALL_RIGHT_START
    )
    autonomousModeChooser.addOption("Test", AutonomousMode.TEST_AUTO_PATH)
    autonomousModeChooser.addOption(
      "Characterize Drivetrain", AutonomousMode.CHARACTERIZE_DRIVETRAIN
    )
    autonomousModeChooser.addOption(
      "Characterize Telescoping Arms", AutonomousMode.CHARACTERIZE_CLIMBER_TELESCOPE
    )
    autonomousModeChooser.addOption("KhetarpalMode", AutonomousMode.KHETARPAL_PATH_ONE_METER)
    autonomousModeChooser.addOption("Characterize Shooter", AutonomousMode.CHARACTERIZE_SHOOTER)
    autonomousModeChooser.addOption("Four Ball Right Start", AutonomousMode.FOUR_BALL_RIGHT_START)
    autonomousModeChooser.addOption("Five Ball Right Start", AutonomousMode.FIVE_BALL_RIGHT_START)
    autonomousModeChooser.addOption("888 Mode", AutonomousMode.EIGHT_EIGHT_EIGHT_MODE)
    autoTab.add("Mode", autonomousModeChooser).withSize(5, 2).withPosition(3, 0)
    waitBeforeCommandSlider =
      autoTab
        .add("Wait Time before Running Auto", 0)
        .withSize(3, 2)
        .withPosition(0, 0)
        .withWidget(BuiltInWidgets.kTextView)
        .entry
    secondaryWaitInAuto =
      autoTab
        .add("Secondary Wait Time During Auto Path", 0)
        .withSize(3, 2)
        .withPosition(0, 2)
        .withWidget(BuiltInWidgets.kTextView)
        .entry
  }

  fun getWaitTime(): Time {
    return waitBeforeCommandSlider.getDouble(0.0).seconds
  }

  fun getSecondaryWaitTime(): Time {
    return secondaryWaitInAuto.getDouble(0.0).seconds
  }

  fun getCommand(
    drivetrain: Drivetrain,
    intake: Intake,
    feeder: Feeder,
    shooter: Shooter,
    telescopingClimber: TelescopingClimber,
    pivotClimber: PivotClimber
  ): CommandBase {

    val mode = autonomousModeChooser.selected
    //    println("${getWaitTime().inSeconds} wait command")
    when (mode) {
      AutonomousMode.TEST_AUTO_PATH ->
        return WaitCommand(getWaitTime().inSeconds).andThen(TestAutoPath(drivetrain))
      AutonomousMode.THREE_BALL_RIGHT_START_FASTER ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(ThreeBallRightStartFaster(drivetrain, intake, feeder, shooter))
      AutonomousMode.THREE_BALL_RIGHT_START ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(ThreeBallRightStart(drivetrain, intake, feeder, shooter))
      AutonomousMode.CHARACTERIZE_DRIVETRAIN ->
        return WaitCommand(getWaitTime().inSeconds).andThen(DriveCharacterizeCommand(drivetrain))
      AutonomousMode.CHARACTERIZE_SHOOTER ->
        return WaitCommand(getWaitTime().inSeconds).andThen(ShooterCharacterizeCommand(shooter))
      AutonomousMode.CHARACTERIZE_CLIMBER_TELESCOPE ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(TelescopingCharacterizationCommand(telescopingClimber))
      //      AutonomousMode.CHARACTERIZE_CLIMBER_PIVOT -> return
      // PivotCharacterizationCommand(pivotClimber)
      AutonomousMode.FOUR_BALL_RIGHT_START ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(FourBallRightStart(drivetrain, intake, feeder, shooter))
      AutonomousMode.FIVE_BALL_RIGHT_START ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(FiveBallRightStart(drivetrain, intake, feeder, shooter))
      AutonomousMode.EIGHT_EIGHT_EIGHT_MODE ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(EightEightEightMode(drivetrain, intake, feeder, shooter))
      AutonomousMode.ONE_BALL_MIDDLE_BUMP ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(
            OneBallLeftLeftBumpMode(
              drivetrain, intake, feeder, shooter, getSecondaryWaitTime()
            )
          )
      AutonomousMode.ONE_BALL_MIDDLE_STEAL ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(
            OneBallLeftLeftSteal(drivetrain, feeder, shooter, intake, getSecondaryWaitTime())
          )
      AutonomousMode.ONE_BALL_RIGHT_STEAL ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(
            OneBallLeftRightMode(drivetrain, intake, feeder, shooter, getSecondaryWaitTime())
          )
      AutonomousMode.ONE_BALL_FENDER_THEN_MIDDLE_STEAL ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(
            OneBallFenderShotOneSteal(
              drivetrain, feeder, shooter, intake, getSecondaryWaitTime()
            )
          )
      AutonomousMode.TWO_BALL_LEFT_MIDDLE_ONE_STEAL ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(
            TwoBallLeftMiddleOneStealMode(
              drivetrain, intake, feeder, shooter, getSecondaryWaitTime()
            )
          )
      AutonomousMode.TWO_BALL_LEFT_MIDDLE_NO_STEAL ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(TwoBallLeftMiddleStartMode(drivetrain, intake, feeder, shooter))
      AutonomousMode.TWO_BALL_LEFT_MIDDLE_TWO_STEAL ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(
            TwoBallLeftMiddleTwoStealMode(
              drivetrain, intake, feeder, shooter, getSecondaryWaitTime()
            )
          )
      AutonomousMode.TWO_BALL_LEFT_RIGHT_ONE_STEAL ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(
            TwoBallLeftRightSteal(
              drivetrain, intake, feeder, shooter, pivotClimber, getSecondaryWaitTime()
            )
          )
      AutonomousMode.ONE_BALL_FENDER_THEN_TAXI ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(
            OneBallFenderThenTaxi(
              drivetrain, feeder, shooter, intake, getSecondaryWaitTime()
            )
          )
      AutonomousMode.KHETARPAL_PATH_ONE_METER ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(KhetarpalMode(drivetrain, feeder, shooter, intake, getSecondaryWaitTime()))
      else -> println("ERROR: unexpected auto mode: $mode")
    }
    return InstantCommand()
  }

  private enum class AutonomousMode {
    TEST_AUTO_PATH,
    CHARACTERIZE_DRIVETRAIN,
    CHARACTERIZE_SHOOTER,
    CHARACTERIZE_CLIMBER_TELESCOPE,
    CHARACTERIZE_CLIMBER_PIVOT,
    THREE_BALL_RIGHT_START_FASTER,
    THREE_BALL_RIGHT_START,
    FOUR_BALL_RIGHT_START,
    FIVE_BALL_RIGHT_START,
    EIGHT_EIGHT_EIGHT_MODE,
    ONE_BALL_FENDER_THEN_MIDDLE_STEAL,
    ONE_BALL_MIDDLE_BUMP,
    ONE_BALL_MIDDLE_STEAL,
    ONE_BALL_RIGHT_STEAL,
    ONE_BALL_FENDER_THEN_TAXI,
    TWO_BALL_LEFT_MIDDLE_ONE_STEAL,
    TWO_BALL_LEFT_MIDDLE_NO_STEAL,
    TWO_BALL_LEFT_MIDDLE_TWO_STEAL,
    TWO_BALL_LEFT_RIGHT_ONE_STEAL,
    KHETARPAL_PATH_ONE_METER
  }
}
