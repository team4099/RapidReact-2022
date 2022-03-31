package com.team4099.robot2022.subsystems.pneumatics

import com.team4099.lib.units.base.amps
import com.team4099.robot2022.config.constants.PneumaticConstants
import edu.wpi.first.wpilibj.PneumaticHub

object PneumaticsIOReal : PneumaticIO {
  private val pneumatics: PneumaticHub = PneumaticHub(PneumaticConstants.REV_MODULE_ID)

  init {
    useLowClosedLoopThresholds(false)
  }

  override fun updateInputs(inputs: PneumaticIO.PneumaticsIOInputs) {
    inputs.pressurePsi = pneumatics.getPressure(0)
    inputs.compressorActive = pneumatics.compressor
    inputs.compressorCurrentAmps = pneumatics.compressorCurrent.amps
  }

  override fun useLowClosedLoopThresholds(useLow: Boolean) {
    if (!useLow) {
      pneumatics.enableCompressorAnalog(80.0, 120.0)
    } else {}
  }
}
