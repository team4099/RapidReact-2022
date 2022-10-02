package com.team4099.robot2022.subsystems.vision

import edu.wpi.first.wpilibj2.command.SubsystemBase

class Vision(val io: VisionIO) : SubsystemBase() {
  val inputs = VisionIO.VisionIOInputs()
}
