package com.team4099.robot2022.auto

import com.team4099.lib.units.base.Time
import com.team4099.lib.units.base.inSeconds
import com.team4099.lib.units.base.seconds
import com.team4099.robot2022.auto.mode.EightEightEightMode
import com.team4099.robot2022.auto.mode.FiveBallRightStart
import com.team4099.robot2022.auto.mode.FourBallRightStart
import com.team4099.robot2022.auto.mode.OneBallFenderShotThenTaxiAndObstruct
import com.team4099.robot2022.auto.mode.OneBallLeftLeftMode
import com.team4099.robot2022.auto.mode.OneBallLeftRightMode
import com.team4099.robot2022.auto.mode.TestAutoPath
import com.team4099.robot2022.auto.mode.ThreeBallRightStart
import com.team4099.robot2022.auto.mode.ThreeBallRightStartFaster
import com.team4099.robot2022.auto.mode.TwoBallLeftLeftMode
import com.team4099.robot2022.auto.mode.TwoBallLeftRightMode
import com.team4099.robot2022.auto.mode.TwoBallLeftStartMode
import com.team4099.robot2022.commands.climber.TelescopingCharacterizationCommand
import com.team4099.robot2022.commands.drivetrain.DriveCharacterizeCommand
import com.team4099.robot2022.commands.shooter.ShooterCharacterizeCommand
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
      "One Ball Fender Shot Then Taxi", AutonomousMode.ONE_BALL_FENDER_SHOT_THEN_TAXI
    )
    autonomousModeChooser.addOption(
      "One Ball Left Taxi Then Fender Shot", AutonomousMode.ONE_BALL_LEFT_LEFT_MODE
    )
    autonomousModeChooser.addOption(
      "One Ball Right Taxi Then Fender Shot", AutonomousMode.ONE_BALL_LEFT_RIGHT_MODE
    )
    autonomousModeChooser.addOption(
      "Two Ball Left Taxi Then Fender Shot", AutonomousMode.TWO_BALL_LEFT_LEFT_MODE
    )
    autonomousModeChooser.addOption(
      "Two Ball Right Taxi Then Fender Shot", AutonomousMode.TWO_BALL_LEFT_RIGHT_MODE
    )
    autonomousModeChooser.addOption("Two Ball: Left", AutonomousMode.TWO_BALL_LEFT_START)
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
        .withPosition(2, 0)
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
  ): CommandBase {

    val mode = autonomousModeChooser.selected
    println("${getWaitTime().inSeconds} wait command")
    when (mode) {
      AutonomousMode.TEST_AUTO_PATH ->
        return WaitCommand(getWaitTime().inSeconds).andThen(TestAutoPath(drivetrain))
      AutonomousMode.TWO_BALL_LEFT_START ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(
            TwoBallLeftStartMode(drivetrain, intake, feeder, shooter, getSecondaryWaitTime())
          )
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
      AutonomousMode.ONE_BALL_FENDER_SHOT_THEN_TAXI ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(
            OneBallFenderShotThenTaxiAndObstruct(
              drivetrain, feeder, shooter, intake, getSecondaryWaitTime()
            )
          )
      AutonomousMode.FOUR_BALL_RIGHT_START ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(FourBallRightStart(drivetrain, intake, feeder, shooter))
      AutonomousMode.FIVE_BALL_RIGHT_START ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(FiveBallRightStart(drivetrain, intake, feeder, shooter))
      AutonomousMode.EIGHT_EIGHT_EIGHT_MODE ->
        return WaitCommand(getWaitTime().inSeconds)
          .andThen(EightEightEightMode(drivetrain, intake, feeder, shooter))
      AutonomousMode.ONE_BALL_LEFT_LEFT_MODE ->
        return OneBallLeftLeftMode(drivetrain, intake, feeder, shooter, getWaitTime())
      AutonomousMode.ONE_BALL_LEFT_RIGHT_MODE ->
        return OneBallLeftRightMode(drivetrain, intake, feeder, shooter, getWaitTime())
      AutonomousMode.TWO_BALL_LEFT_LEFT_MODE ->
        return TwoBallLeftLeftMode(drivetrain, intake, feeder, shooter, getWaitTime())
      AutonomousMode.TWO_BALL_LEFT_RIGHT_MODE ->
        return TwoBallLeftRightMode(drivetrain, intake, feeder, shooter, getWaitTime())
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
    ONE_BALL_FENDER_SHOT_THEN_TAXI,
    TWO_BALL_LEFT_START,
    THREE_BALL_RIGHT_START_FASTER,
    THREE_BALL_RIGHT_START,
    FOUR_BALL_RIGHT_START,
    FIVE_BALL_RIGHT_START,
    EIGHT_EIGHT_EIGHT_MODE,
    ONE_BALL_LEFT_LEFT_MODE,
    ONE_BALL_LEFT_RIGHT_MODE,
    TWO_BALL_LEFT_LEFT_MODE,
    TWO_BALL_LEFT_RIGHT_MODE,
  }
}
