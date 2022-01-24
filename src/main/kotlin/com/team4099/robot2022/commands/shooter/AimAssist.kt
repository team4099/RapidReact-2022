package com.team4099.robot2022.commands.shooter

import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.derived.rotations
import com.team4099.lib.units.inRadiansPerSecond
import com.team4099.lib.units.perSecond
import com.team4099.robot2022.config.Constants.ShooterConstants
import com.team4099.robot2022.subsystems.Drivetrain
import com.team4099.robot2022.subsystems.Shooter
import com.team4099.robot2022.subsystems.Vision
import edu.wpi.first.wpilibj2.command.CommandBase
import org.photonvision.targeting.PhotonTrackedTarget

class AimAssist : CommandBase() {

  init {
    addRequirements(Shooter)
//    addRequirements(Drivetrain)
//    addRequirements(Feeder)
  }

  override fun execute() {
    val target = Vision.bestTarget
    val rangeToTarget = Vision.getRangeToBestTarget()
    val offsetToTarget = Vision.getOffsetToBestTarget()

    // projectile motion 🤪
    val vt =
      Shooter.shooterVelocity.inRadiansPerSecond * ShooterConstants.FLYWHEEL_RADIUS.inMeters
  }

  override fun isFinished(): Boolean {
    return Vision.bestTarget == null // TODO: Update with feeder indexing status
  }
}
