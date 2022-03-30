package com.team4099.robot2022.subsystems.pneumatics

import com.team4099.lib.units.base.inSeconds
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.PneumaticConstants
import com.team4099.robot2022.util.Alert
import com.team4099.robot2022.util.Alert.AlertType
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger
import java.util.function.Supplier

class Pneumatic(val io: PneumaticIO) : SubsystemBase() {

  val inputs = PneumaticIO.PneumaticsIOInputs()

  private val normalAveragingTaps = PneumaticConstants.NORMAL_AVERAGING_TAPS
  private val compressorAveragingTaps = PneumaticConstants.COMPRESSOR_AVERAGING_TAPS
  private val compressorRatePsiPerSec = PneumaticConstants.COMPRESSOR_RATE_PSI_PER_SECOND

  private val noPressureTimer: Timer = Timer()
  private val compressorEnabledTimer: Timer = Timer()
  private val dumpValveAlert =
    Alert("Can't build pressure. Check the dump valve!", AlertType.WARNING)

  private val filterData: MutableList<Double> = ArrayList()
  private var pressureSmoothedPsi = 0.0

  private var lastPressurePsi = 0.0
  private var lastPressureIncreasing = false
  private var compressorMaxPoint = 0.0
  private var compressorMinPoint = 0.0

  var climbModeOverride = Supplier { false }

  init {
    noPressureTimer.start()
    compressorEnabledTimer.start()
  }

  fun getPressure(): Double {
    return pressureSmoothedPsi
  }

  fun setSupplier(setClimbModeOverride: Supplier<Boolean>) {
    climbModeOverride = setClimbModeOverride
  }

  override fun periodic() {
    io.updateInputs(inputs)
    Logger.getInstance().processInputs("Pneumatics", inputs)

    io.useLowClosedLoopThresholds(climbModeOverride.get())

    val limitedPressure = if (inputs.pressurePsi < 0.0) 0.0 else inputs.pressurePsi
    var processedPressure = 0.0
    if (inputs.compressorActive) {
      if (limitedPressure != lastPressurePsi) {
        val increasing = limitedPressure > lastPressurePsi
        if (increasing != lastPressureIncreasing) {
          if (increasing) {
            compressorMinPoint = lastPressurePsi
          } else {
            compressorMaxPoint = lastPressurePsi
          }
        }
        lastPressurePsi = limitedPressure
        lastPressureIncreasing = increasing
      }
      processedPressure = (compressorMinPoint + compressorMaxPoint) / 2.0
      processedPressure +=
        (
          compressorAveragingTaps / 2.0 *
            Constants.Universal.LOOP_PERIOD_TIME.inSeconds *
            compressorRatePsiPerSec
          )
    } else {
      lastPressurePsi = limitedPressure
      lastPressureIncreasing = false
      compressorMaxPoint = limitedPressure
      compressorMinPoint = limitedPressure
      processedPressure = limitedPressure
    }

    filterData.add(processedPressure)
    while (filterData.size >
      if (inputs.compressorActive) compressorAveragingTaps else normalAveragingTaps
    ) {
      filterData.removeAt(0)
    }
    pressureSmoothedPsi =
      filterData.stream().mapToDouble { a: Double? -> a!! }.summaryStatistics().average

    Logger.getInstance().recordOutput("Pneumatics/PressurePsi", pressureSmoothedPsi)
//    Shuffleboard.getTab("Camera Feed").add(pressureSmoothedPsi.toString())

    if (inputs.pressurePsi > 3) {
      noPressureTimer.reset()
    }
    if (!inputs.compressorActive) {
      compressorEnabledTimer.reset()
    }
    dumpValveAlert.set(noPressureTimer.hasElapsed(5.0) && compressorEnabledTimer.hasElapsed(5.0))
  }
}
