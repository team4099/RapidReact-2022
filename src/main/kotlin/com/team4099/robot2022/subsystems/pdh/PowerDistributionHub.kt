package com.team4099.robot2022.subsystems.pdh

import com.team4099.lib.units.base.inAmperes
import com.team4099.lib.units.derived.inVolts
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class PowerDistributionHub(val io: PowerDistributionHubIO) : SubsystemBase() {

  val inputs = PowerDistributionHubIO.PowerDistributionHubIOInputs()
  var resistance = 0.0

  override fun periodic() {
    io.updateInputs(inputs)
    Logger.getInstance().processInputs("PDH", inputs)

    resistance = inputs.voltage.inVolts / inputs.current.inAmperes

    Logger.getInstance().recordOutput("PDH/resistance", resistance)
  }
}
