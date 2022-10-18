package com.team4099.robot2022.subsystems.vision

import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.base.inMilliseconds
import com.team4099.lib.units.base.meters
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
    var bestX = 0.meters
    var bestY = 0.meters
    var bestZ = 0.0.meters

    override fun toLog(table: LogTable?) {
      table?.put("hasTargets", hasTargets)
      table?.put("latencyMS", latency.inMilliseconds)
      table?.put("bestX", bestX.inMeters)
      table?.put("bestY", bestY.inMeters)
      table?.put("bestZ", bestZ.inMeters)
    }

    override fun fromLog(table: LogTable?) {
      table?.getBoolean("hasTargets", hasTargets)?.let { hasTargets = it }
      table?.getDouble("latencyMS", latency.inMilliseconds)?.let { latency = it.milli.seconds }
      table?.getDouble("bestX", bestX.inMeters)?.let { bestX = it.meters }
      table?.getDouble("bestY", bestY.inMeters)?.let { bestY = it.meters }
      table?.getDouble("bestZ", bestZ.inMeters)?.let { bestZ = it.meters }
    }
  }

  fun updateInputs(inputs: VisionIO.VisionIOInputs) {}
}
