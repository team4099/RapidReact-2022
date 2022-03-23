package com.team4099.robot2022.subsystems.feeder

import com.team4099.lib.units.derived.volts
import com.team4099.robot2022.config.constants.FeederConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class Feeder(val io: FeederIO) : SubsystemBase() {

  val inputs = FeederIO.FeederIOInputs()

  var state = FeederConstants.FeederState.NEUTRAL
    set(state) {
      field = state
      io.setPower(state.floorMotorPower, state.verticalMotorPower)
    }

  var ballCount: Int = 1
  private var bottomPrevStage: Boolean = inputs.bottomBeamBroken
  private var topPrevStage: Boolean = inputs.bottomBeamBroken

  override fun periodic() {
    io.updateInputs(inputs)

    if ((inputs.bottomBeamBroken != bottomPrevStage) && !inputs.bottomBeamBroken) {
      // in floor if correct ball is intaken
      if (inputs.floorAppliedVoltage > 0.volts) {
        ballCount++
        // in floor if incorrect ball is outtaken
      } else if (inputs.floorAppliedVoltage < 0.volts) {
        ballCount--
      }
    }
    if (state == FeederConstants.FeederState.SHOOT) {
      if ((inputs.topBeamBroken != topPrevStage) && !inputs.topBeamBroken) {
        ballCount--
      }
    }

    bottomPrevStage = inputs.bottomBeamBroken
    topPrevStage = inputs.topBeamBroken

    Logger.getInstance().processInputs("Feeder", inputs)

    Logger.getInstance().recordOutput("Feeder/ballCount", ballCount)
    Logger.getInstance().recordOutput("Feeder/bottomPrevStage", bottomPrevStage)
    Logger.getInstance().recordOutput("Feeder/topPrevStage", topPrevStage)
    Logger.getInstance().recordOutput("Feeder/state", state.name)
  }

  init {
    state = state
  }
}
