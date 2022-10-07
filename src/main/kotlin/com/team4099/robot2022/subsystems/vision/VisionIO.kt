package com.team4099.robot2022.subsystems.vision

import com.team4099.lib.units.base.inMilliseconds
import com.team4099.lib.units.base.seconds
import com.team4099.lib.units.milli
import org.littletonrobotics.junction.LogTable
import org.littletonrobotics.junction.inputs.LoggableInputs
import org.photonvision.targeting.PhotonTrackedTarget

interface VisionIO {
  class VisionIOInputs : LoggableInputs {
    var hasTargets: Boolean = false
    val targets: List<PhotonTrackedTarget> = mutableListOf()
    var latency = 0.milli.seconds

    override fun toLog(table: LogTable?) {
      table?.put("hasTargets", hasTargets)
      table?.put("latencyMS", latency.inMilliseconds)
    }

    override fun fromLog(table: LogTable?) {
      table?.getBoolean("hasTargets", hasTargets)?.let { hasTargets = it }
      table?.getDouble("latencyMS", latency.inMilliseconds)?.let { latency = it.milli.seconds }
    }
  }

  fun updateInputs(inputs: VisionIO.VisionIOInputs) {}
}
