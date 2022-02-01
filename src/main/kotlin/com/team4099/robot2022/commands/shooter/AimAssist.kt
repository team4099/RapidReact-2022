package com.team4099.robot2022.commands.shooter

import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.derived.rotations
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.lib.units.perSecond
import com.team4099.robot2022.commands.drivetrain.OpenLoopDriveCommand
import com.team4099.robot2022.config.Constants.VisionConstants
import com.team4099.robot2022.config.Constants.ShooterConstants
import com.team4099.robot2022.subsystems.Drivetrain
import com.team4099.robot2022.subsystems.Shooter
import com.team4099.robot2022.subsystems.Vision
import edu.wpi.first.wpilibj2.command.CommandBase
import org.photonvision.targeting.PhotonTrackedTarget

class AimAssist : CommandBase() {
  private var rangeToTarget = 0.0.meters
  private var diff = 0.0.meters

  init {
    addRequirements(Shooter)
//    addRequirements(Drivetrain)
//    addRequirements(Feeder)
  }

  override fun execute() {
    rangeToTarget = Vision.getRangeToBestTarget()
    diff = VisionConstants.TARGET_RANGE - rangeToTarget
    val target = Vision.bestTarget
    val offsetToTarget = Vision.getOffsetToBestTarget()
    OpenLoopDriveCommand({ rangeToTarget.inMeters / 2 }, { 0.0 }, { 0.0 })
  }

  override fun isFinished(): Boolean {
    val diff = VisionConstants.TARGET_RANGE - rangeToTarget
    return (diff <= VisionConstants.RANGE_THRESHOLD && diff > 0.0.meters) || Vision.bestTarget == null
  }
}
