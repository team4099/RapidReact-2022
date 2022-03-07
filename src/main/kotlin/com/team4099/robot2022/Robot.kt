package com.team4099.robot2022

import com.team4099.RobotContainer
import com.team4099.robot2022.auto.AutonomousSelector
import com.team4099.robot2022.config.constants.Constants
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.littletonrobotics.junction.LoggedRobot
import org.littletonrobotics.junction.Logger
import org.littletonrobotics.junction.inputs.LoggedNetworkTables
import org.littletonrobotics.junction.io.ByteLogReceiver
import org.littletonrobotics.junction.io.ByteLogReplay
import org.littletonrobotics.junction.io.LogSocketServer

object Robot : LoggedRobot() {
  val robotName: Constants.Tuning.RobotType

  init {
    robotName = Constants.Tuning.type

    RobotContainer
    AutonomousSelector
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
      Logger.getInstance()
          .addDataReceiver(ByteLogReceiver(ByteLogReceiver.addPathSuffix(path, "_sim")))
    }
    Logger.getInstance().start()

    RobotContainer.mapDefaultCommands()
    RobotContainer.zeroSensors()
  }

  override fun autonomousInit() {
    // autonomousCommand.schedule()
    RobotContainer.getAutonomousCommand().schedule()
  }

  override fun disabledInit() {
    RobotContainer.getAutonomousCommand().cancel()
    // autonomousCommand.cancel()
  }

  override fun teleopInit() {
    RobotContainer.mapTeleopControls()
    RobotContainer.getAutonomousCommand().cancel()
    // autonomousCommand.cancel()
  }

  override fun testInit() {
    RobotContainer.mapTestControls()
    RobotContainer.getAutonomousCommand().cancel()
  }

  override fun robotPeriodic() {
    CommandScheduler.getInstance().run()
  }
}
