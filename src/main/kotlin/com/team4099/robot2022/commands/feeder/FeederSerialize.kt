package com.team4099.robot2022.commands.feeder

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.FeederConstants
import com.team4099.robot2022.subsystems.feeder.Feeder
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.CommandBase

// not final
class FeederSerialize(val feeder: Feeder) : CommandBase() {
  var currentTime = 0.0
  var lastBrokenTime = 0.0
  var lastUnbrokenTime = 0.0

  init {
    addRequirements(feeder)
  }

  override fun initialize() {
    Logger.addEvent(FeederConstants.TAB, "Beam break started")
  }

  override fun execute() {
    currentTime = Timer.getFPGATimestamp()
    if (feeder.bottomBeamBroken) {
      lastBrokenTime = currentTime
    } else {
      lastUnbrokenTime = currentTime
    }
    feeder.feederState =
        when {
          (lastUnbrokenTime - lastBrokenTime < FeederConstants.BEAM_BREAK_BACKWARDS_TIME) ->
              FeederConstants.FeederState.BACKWARD_VERTICAL
          feeder.topBeamBroken -> FeederConstants.FeederState.NEUTRAL
          (currentTime - lastBrokenTime < FeederConstants.BEAM_BREAK_BROKEN_TIME) ->
              FeederConstants.FeederState.FORWARD_ALL
          else -> FeederConstants.FeederState.FORWARD_FLOOR
        }
  }
}
