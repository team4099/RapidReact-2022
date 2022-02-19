package com.team4099.robot2022.commands.shooter

import com.team4099.lib.units.base.meters
import com.team4099.lib.units.derived.rotations
import com.team4099.lib.units.perSecond
import com.team4099.robot2022.config.constants.VisionConstants
import com.team4099.robot2022.subsystems.Drivetrain
import com.team4099.robot2022.subsystems.Shooter
import com.team4099.robot2022.subsystems.Vision
import edu.wpi.first.wpilibj2.command.CommandBase

class AimAssist : CommandBase() {
  var diff = 0.0.meters
  var rangeToTarget = 0.0.meters

  init {
    addRequirements(Shooter)
    //    addRequirements(Drivetrain)
    //    addRequirements(Feeder)
  }

  override fun execute() {
    // get x and y distance from target
    // determine whether to go back or forward
    // drive drivetrain forward or backward
    rangeToTarget = Vision.getRangeToBestTarget()
    diff = VisionConstants.TARGET_RANGE - rangeToTarget
    val angleOffset = Vision.yaw
    var toMoveY =
        if (rangeToTarget < VisionConstants.TARGET_RANGE) {
          rangeToTarget - VisionConstants.TARGET_RANGE
        } else {
          rangeToTarget
        }

    Drivetrain.set(
        -angleOffset!!.rotations.perSecond,
        Pair(0.0.meters.perSecond, toMoveY.perSecond),
        fieldOriented = false)
  }

  override fun isFinished(): Boolean {
    //    val diff = VisionConstants.TARGET_RANGE - rangeToTarget
    return (diff <= VisionConstants.RANGE_THRESHOLD && diff > 0.0.meters) || !Vision.hasTargets!!
  }
}
