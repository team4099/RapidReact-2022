package com.team4099.robot2022.auto

import com.team4099.lib.units.base.Time
import com.team4099.lib.units.base.inSeconds
import com.team4099.lib.units.base.seconds
import com.team4099.lib.units.derived.Angle
import com.team4099.lib.units.derived.degrees
import com.team4099.robot2022.auto.mode.OneBallFenderShotThenTaxi
import com.team4099.robot2022.auto.mode.TestAutoPath
import com.team4099.robot2022.auto.mode.ThreeBallRightStart
import com.team4099.robot2022.auto.mode.TwoBallLeftStartMode
import com.team4099.robot2022.commands.climber.TelescopingCharacterizationCommand
import com.team4099.robot2022.commands.drivetrain.DriveCharacterizeCommand
import com.team4099.robot2022.commands.shooter.ShooterCharacterizeCommand
import com.team4099.robot2022.subsystems.climber.PivotClimber
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.intake.Intake
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.WaitCommand

object AutonomousSelector {
  private var orientationChooser: SendableChooser<Angle> = SendableChooser()
  private var autonomousModeChooser: SendableChooser<AutonomousMode> = SendableChooser()
  private var waitBeforeCommandSlider: Time = 0.0.seconds

  init {
    val autoTab = Shuffleboard.getTab("Auto settings")
    orientationChooser.setDefaultOption("Forward", 0.degrees)
    orientationChooser.addOption("Backwards", 180.degrees)
    orientationChooser.addOption("Left", 90.degrees)
    orientationChooser.addOption("Right", 270.degrees)
    autoTab.add("Starting Orientation", orientationChooser)
    autonomousModeChooser.addOption(
        "One Ball Fender Shot Then Taxi: Left", AutonomousMode.ONE_BALL_FENDER_SHOT_THEN_TAXI_LEFT)
    autonomousModeChooser.addOption(
        "One Ball Fender Shot Then Taxi: Right",
        AutonomousMode.ONE_BALL_FENDER_SHOT_THEN_TAXI_RIGHT)
    autonomousModeChooser.addOption("Two Ball: Left", AutonomousMode.TWO_BALL_LEFT_START)
    autonomousModeChooser.addOption("Three Ball: Left Start", AutonomousMode.THREE_BALL_RIGHT_START)
    autonomousModeChooser.addOption("Test", AutonomousMode.TEST_AUTO_PATH)
    autonomousModeChooser.addOption(
        "Characterize Drivetrain", AutonomousMode.CHARACTERIZE_DRIVETRAIN)
    autonomousModeChooser.addOption(
        "Characterize Telescoping Arms", AutonomousMode.CHARACTERIZE_CLIMBER_TELESCOPE)
    autonomousModeChooser.addOption("Characterize Shooter", AutonomousMode.CHARACTERIZE_SHOOTER)
    autoTab.add("Mode", autonomousModeChooser)
    waitBeforeCommandSlider =
        autoTab.add("Wait Time before Running Auto", 0)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(mapOf("min" to 0, "max" to 15))
            .entry
            .getDouble(0.0)
            .seconds
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
    when (mode) {
      AutonomousMode.TEST_AUTO_PATH ->
          return WaitCommand(waitBeforeCommandSlider.inSeconds).andThen(TestAutoPath(drivetrain))
      AutonomousMode.TWO_BALL_LEFT_START ->
          return WaitCommand(waitBeforeCommandSlider.inSeconds).andThen(
              TwoBallLeftStartMode(drivetrain, intake, feeder, shooter))
      AutonomousMode.THREE_BALL_RIGHT_START ->
          return WaitCommand(waitBeforeCommandSlider.inSeconds).andThen(
              ThreeBallRightStart(drivetrain, intake, feeder, shooter))
      AutonomousMode.CHARACTERIZE_DRIVETRAIN ->
          return WaitCommand(waitBeforeCommandSlider.inSeconds).andThen(
              DriveCharacterizeCommand(drivetrain))
      AutonomousMode.CHARACTERIZE_SHOOTER ->
          return WaitCommand(waitBeforeCommandSlider.inSeconds).andThen(
              ShooterCharacterizeCommand(shooter))
      AutonomousMode.CHARACTERIZE_CLIMBER_TELESCOPE ->
          return WaitCommand(waitBeforeCommandSlider.inSeconds).andThen(
              TelescopingCharacterizationCommand(telescopingClimber))
      //      AutonomousMode.CHARACTERIZE_CLIMBER_PIVOT -> return
      // PivotCharacterizationCommand(pivotClimber)
      AutonomousMode.ONE_BALL_FENDER_SHOT_THEN_TAXI_LEFT ->
          return WaitCommand(waitBeforeCommandSlider.inSeconds).andThen(
              OneBallFenderShotThenTaxi(drivetrain, feeder, shooter, -24.degrees))
      AutonomousMode.ONE_BALL_FENDER_SHOT_THEN_TAXI_RIGHT ->
          return WaitCommand(waitBeforeCommandSlider.inSeconds).andThen(
              OneBallFenderShotThenTaxi(drivetrain, feeder, shooter, 66.degrees))
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
    ONE_BALL_FENDER_SHOT_THEN_TAXI_LEFT,
    ONE_BALL_FENDER_SHOT_THEN_TAXI_RIGHT,
    TWO_BALL_LEFT_START,
    THREE_BALL_RIGHT_START
  }
}
