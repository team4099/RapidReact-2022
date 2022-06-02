package com.team4099.robot2022.commands

import com.team4099.robot2022.config.constants.VisionConstants
import com.team4099.robot2022.subsystems.drivetrain.Drivetrain
import com.team4099.robot2022.subsystems.vision.Vision

class getVisionTarget(val vision: Vision, val drive : Drivetrain) {
  init {
      addRequirement(vision)
  }

  override fun initialize(){
    vision.newTarget()
    
  }

}
