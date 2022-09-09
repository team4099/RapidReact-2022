package com.team4099.robot2022.subsystems.vision

import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger
import kotlin.time.Duration.Companion.seconds

class Vision(val io: VisionIO) : SubsystemBase() {
  val inputs = VisionIO.VisionIOInputs()
  var lastTimestampCaptured = 0.0.seconds
  var ledOn: Boolean = true

  override fun periodic() {
    io.updateInputs(inputs)
    Logger.getInstance().processInputs("Vision", inputs)
    
  }

}
