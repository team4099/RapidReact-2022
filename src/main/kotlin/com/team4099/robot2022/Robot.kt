package com.team4099.robot2022

import com.team4099.robot2022.auto.AutonomousSelector
import com.team4099.robot2022.auto.PathStore
import com.team4099.robot2022.config.constants.Constants
import edu.wpi.first.wpilibj.PowerDistribution
import edu.wpi.first.wpilibj.livewindow.LiveWindow
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.littletonrobotics.junction.LoggedRobot
import org.littletonrobotics.junction.Logger
import org.littletonrobotics.junction.inputs.LoggedNetworkTables
import org.littletonrobotics.junction.inputs.LoggedSystemStats
import org.littletonrobotics.junction.io.ByteLogReceiver
import org.littletonrobotics.junction.io.ByteLogReplay
import org.littletonrobotics.junction.io.LogSocketServer

object Robot : LoggedRobot() {
  val robotName: Constants.Tuning.RobotType

  init {
    robotName = Constants.Tuning.type
  }

  override fun robotInit() {
    // set up AdvantageKit logging

    val logger = Logger.getInstance()

    // allow running faster than real time when replaying logs
    setUseTiming(isReal())

    // log smart dashboard values (otherwise nothing is logged by default
    LoggedNetworkTables.getInstance().addTable("/SmartDashboard")

    // metadata value (not timed -- just metadata for given log file)
    logger.recordMetadata("RobotName", robotName.name)
    logger.recordMetadata("TuningMode", Constants.Tuning.TUNING_MODE.toString())
    logger.recordMetadata("RuntimeType", getRuntimeType().name)

    logger.recordMetadata("ProjectName", MAVEN_NAME)
    logger.recordMetadata("BuildDate", BUILD_DATE)
    logger.recordMetadata("GitSHA", GIT_SHA)
    logger.recordMetadata("GitBranch", GIT_BRANCH)
    when (DIRTY) {
      0 -> logger.recordMetadata("GitDirty", "All changes committed")
      1 -> logger.recordMetadata("GitDirty", "Uncommitted changes")
      else -> logger.recordMetadata("GitDirty", "Unknown")
    }

    if (isReal()) {
      // log to USB stick and network for real time data viewing on AdvantageScope
      logger.addDataReceiver(ByteLogReceiver("/media/sda1"))
      logger.addDataReceiver(LogSocketServer(5800))
      LoggedSystemStats.getInstance()
        .setPowerDistributionConfig(1, PowerDistribution.ModuleType.kRev)
    } else {
      // if in replay mode get file path from command line and read log file
      val path = ByteLogReplay.promptForPath()
      logger.setReplaySource(ByteLogReplay(path))
      logger.addDataReceiver(ByteLogReceiver(ByteLogReceiver.addPathSuffix(path, "_sim")))
    }
    logger.start()

    LiveWindow.disableAllTelemetry()

    RobotContainer
    AutonomousSelector
    PathStore
    RobotContainer.mapDefaultCommands()
    RobotContainer.zeroSensors()
  }

  override fun autonomousInit() {
    // autonomousCommand.schedule()
    RobotContainer.setDriveBrakeMode()
    RobotContainer.zeroSteering()
    RobotContainer.getAutonomousCommand().schedule()
  }

  override fun disabledInit() {
    RobotContainer.getAutonomousCommand().cancel()
    RobotContainer.setDriveBrakeMode()
    // autonomousCommand.cancel()
  }

  override fun teleopInit() {
    RobotContainer.mapTeleopControls()
    RobotContainer.getAutonomousCommand().cancel()
    RobotContainer.setDriveBrakeMode() // change to coast
    RobotContainer.zeroSteering()
    // autonomousCommand.cancel()
  }

  override fun testInit() {
    RobotContainer.mapTestControls()
    RobotContainer.getAutonomousCommand().cancel()
  }

  override fun robotPeriodic() {
    CommandScheduler.getInstance().run()

    //    Logger.getInstance()
    //      .recordOutput(
    //        "ActiveCommands/Scheduler",
    //        NetworkTableInstance.getDefault()
    //          .getEntry("/LiveWindow/Ungrouped/Scheduler/Names")
    //          .getStringArray(emptyArray())
    //      )

    //    RobotContainer.logDriverController()
    //    RobotContainer.logOperatorController()
  }
}
