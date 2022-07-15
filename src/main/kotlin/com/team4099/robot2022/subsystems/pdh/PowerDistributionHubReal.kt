package com.team4099.robot2022.subsystems.pdh

import com.team4099.lib.units.base.amps
import com.team4099.lib.units.derived.volts
import edu.wpi.first.wpilibj.PowerDistribution

object PowerDistributionHubReal : PowerDistributionHubIO {
  private val pdh = PowerDistribution(1, PowerDistribution.ModuleType.kRev)

  override fun updateInputs(inputs: PowerDistributionHubIO.PowerDistributionHubIOInputs) {
    inputs.voltage = pdh.voltage.volts
    inputs.current = pdh.totalCurrent.amps
  }
}
