package com.team4099.robot2022.commands.feeder

import com.team4099.lib.logging.Logger
import com.team4099.robot2022.config.constants.FeederConstants
import com.team4099.robot2022.subsystems.Feeder
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.CommandBase

// not final
class FeederSerialize : CommandBase() {
  var currentTime = 0.0
  var lastBrokenTime = 0.0
  var lastUnbrokenTime = 0.0

  init {
    addRequirements(Feeder)
  }

  override fun initialize() {
    Logger.addEvent(FeederConstants.TAB, "Beam break started")
  }

  override fun execute() {
    currentTime = Timer.getFPGATimestamp()
    if (Feeder.bottomBeamBroken) {
      lastBrokenTime = currentTime
    } else {
      lastUnbrokenTime = currentTime
    }
    Feeder.feederState =
        when {
          Feeder.topBeamBroken -> FeederConstants.FeederState.NEUTRAL
          (currentTime - lastBrokenTime < FeederConstants.BEAM_BREAK_BROKEN_TIME) ->
              FeederConstants.FeederState.FORWARD_ALL
          (lastUnbrokenTime - lastBrokenTime < FeederConstants.BEAM_BREAK_BACKWARDS_TIME) ->
              FeederConstants.FeederState.BACKWARD_VERTICAL
          else -> FeederConstants.FeederState.FORWARD_FLOOR
        }
  }
}