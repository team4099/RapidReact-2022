package com.team4099.robot2022.subsystems.feeder

import com.team4099.lib.hal.Clock
import com.team4099.lib.units.base.Time
import com.team4099.lib.units.base.seconds
import com.team4099.lib.units.derived.volts
import com.team4099.robot2022.config.constants.FeederConstants
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
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

  var oneBallCheck: Boolean = false
  var timerBottomBreak: Boolean = false
  private var bottomPrevStage: Boolean = timerBottomBreak
  private var topPrevStage: Boolean = timerBottomBreak
  var timer: Time = Clock.fpgaTime


  //  private var bottomBeamBreakList = mutableListOf<Boolean>()
  //  private var topBeamBreakList = mutableListOf<Boolean>()

  override fun periodic() {
    io.updateInputs(inputs)
    timerBottomBreak = false

    if (bottomPrevStage != timerBottomBreak &&
      state == FeederConstants.FeederState.FORWARD_ALL
    ) {
      oneBallCheck = true
    }

    if(timerBottomBreak) {
      if(timer == 0.seconds) {
        timer = Clock.fpgaTime
      }
      else if (Clock.fpgaTime - timer >= FeederConstants.BEAM_BREAK_THRESHOLD) {
        timerBottomBreak = true
      }
    }
    else {
      timer = 0.seconds
    }

    if (!inputs.topBeamBroken && !timerBottomBreak && !oneBallCheck) {
      ballCount = 0
    } else if (inputs.topBeamBroken && timerBottomBreak) {
      ballCount = 2
      oneBallCheck = false
    } else if ((inputs.topBeamBroken && !timerBottomBreak) || oneBallCheck) {
      ballCount = 1
    }

    if ((timerBottomBreak != bottomPrevStage) && timerBottomBreak) {
      // in floor if correct ball is intaken
      if (inputs.topBeamBroken && timerBottomBreak) {
        ballCount = 2
      } else if (inputs.floorAppliedVoltage >= 0.volts) {
        ballCount++
      }
      // in floor if incorrect ball is outtaken
      //      } else if (inputs.floorAppliedVoltage < 0.volts) {
      //        ballCount--
      //      }
    }
    if (state == FeederConstants.FeederState.SHOOT) {
      if ((inputs.topBeamBroken != topPrevStage) && !inputs.topBeamBroken) {
        ballCount--
      }
    }

    //    if (bottomBeamBreakList.size > FeederConstants.BEAM_BREAK_FILTER_SIZE) {
    //      bottomBeamBreakList.removeAt(bottomBeamBreakList.size - 1)
    //    }
    //    bottomBeamBreakList.add(timerBottomBreak)
    //
    //    if (topBeamBreakList.size > FeederConstants.BEAM_BREAK_FILTER_SIZE) {
    //      topBeamBreakList.removeAt(topBeamBreakList.size - 1)
    //    }
    //    topBeamBreakList.add(inputs.topBeamBroken)

    bottomPrevStage = inputs.bottomBeamBroken
    topPrevStage = inputs.topBeamBroken

    Logger.getInstance().processInputs("Feeder", inputs)

    Logger.getInstance().recordOutput("Feeder/ballCount", ballCount)
    Logger.getInstance().recordOutput("Feeder/bottomPrevStage", bottomPrevStage)
    Logger.getInstance().recordOutput("Feeder/topPrevStage", topPrevStage)
    Logger.getInstance().recordOutput("Feeder/state", state.name)
    Logger.getInstance().recordOutput("Feeder/oneBallCheck", oneBallCheck)

    SmartDashboard.putBoolean("Feeder/oneCargo", ballCount >= 1)
    SmartDashboard.putBoolean("Feeder/twoCargo", ballCount >= 2)
  }

  init {
    state = state
  }
}
