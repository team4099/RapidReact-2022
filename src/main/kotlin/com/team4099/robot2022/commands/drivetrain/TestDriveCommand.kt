package com.team4099.robot2022.commands.drivetrain

import com.team4099.lib.hal.Clock
import com.team4099.lib.logging.Logger
import com.team4099.lib.pathfollow.Trajectory
import com.team4099.lib.units.base.inFeet
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.base.inSeconds
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.base.seconds
import com.team4099.lib.units.derived.cos
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inDegrees
import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.derived.radians
import com.team4099.lib.units.derived.sin
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.lib.units.inRadiansPerSecondPerSecond
import com.team4099.lib.units.perSecond
import com.team4099.robot2022.config.Constants
import com.team4099.robot2022.subsystems.Drivetrain
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.controller.ProfiledPIDController
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.CommandBase
import kotlin.math.PI

class TestDriveCommand() : CommandBase() {

  init {
    addRequirements(Drivetrain)
  }

  override fun initialize() {

  }

  override fun execute() {
    Drivetrain.swerveModules.forEach { it.setOpenLoop(-90.degrees, 0.1) }
  }
  override fun isFinished(): Boolean {
    return false
  }

  override fun end(interrupted: Boolean) {

  }
}
