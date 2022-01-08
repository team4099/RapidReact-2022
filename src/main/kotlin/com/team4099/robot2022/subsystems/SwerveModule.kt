package com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.DemandType
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.team4099.lib.around
import com.team4099.lib.logging.Logger
import com.team4099.lib.units.AngularMechanismSensor
import com.team4099.lib.units.LinearAcceleration
import com.team4099.lib.units.LinearVelocity
import com.team4099.lib.units.Timescale
import com.team4099.lib.units.base.Length
import com.team4099.lib.units.base.feet
import com.team4099.lib.units.base.inFeet
import com.team4099.lib.units.base.meters
import com.team4099.lib.units.ctreAngularMechanismSensor
import com.team4099.lib.units.ctreLinearMechanismSensor
import com.team4099.lib.units.derived.Angle
import com.team4099.lib.units.derived.ElectricalPotential
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inDegrees
import com.team4099.lib.units.derived.inRadians
import com.team4099.lib.units.derived.inVolts
import com.team4099.lib.units.derived.radians
import com.team4099.lib.units.derived.volts
import com.team4099.lib.units.inFeetPerSecond
import com.team4099.lib.units.perSecond
import com.team4099.robot2022.config.Constants
import edu.wpi.first.math.filter.MedianFilter
import edu.wpi.first.wpilibj.AnalogPotentiometer
import kotlin.math.IEEErem
import kotlin.math.sign
import kotlin.math.withSign

class SwerveModule(
  private val steeringFalcon: TalonFX,
  private val driveFalcon: TalonFX,
  private val potentiometer: AnalogPotentiometer,
  private val zeroOffset: Angle,
  val label: String
) {

  private val steeringSensor =
      ctreAngularMechanismSensor(
          steeringFalcon,
          Constants.Drivetrain.STEERING_SENSOR_CPR,
          Constants.Drivetrain.STEERING_SENSOR_GEAR_RATIO)
  private val driveSensor =
      ctreLinearMechanismSensor(
          driveFalcon,
          Constants.Drivetrain.DRIVE_SENSOR_CPR,
          Constants.Drivetrain.DRIVE_SENSOR_GEAR_RATIO,
          Constants.Drivetrain.WHEEL_DIAMETER)

  private val steeringAbsolute =
      AngularMechanismSensor(
          Constants.Drivetrain.ABSOLUTE_GEAR_RATIO,
          Timescale.CTRE,
          { 0.0 },
          { potentiometer.get() })

  private val filter = MedianFilter(10)

  // motor params
  private val driveTemp: Double
    get() = driveFalcon.temperature

  private val steeringTemp: Double
    get() = steeringFalcon.temperature

  private val driveOutputCurrent: Double
    get() = steeringFalcon.statorCurrent

  private val steeringOutputCurrent: Double
    get() = steeringFalcon.statorCurrent

  private val drivePercentOutput: Double
    get() = driveFalcon.motorOutputPercent

  private val steeringPercentOutput: Double
    get() = steeringFalcon.motorOutputPercent

  private val driveBusVoltage: ElectricalPotential
    get() = driveFalcon.busVoltage.volts

  private val steeringBusVoltage: ElectricalPotential
    get() = steeringFalcon.busVoltage.volts

  val driveOutputVoltage: ElectricalPotential
    get() = driveBusVoltage * drivePercentOutput

  val driveDistance: Length
    get() = driveSensor.position

  val driveVelocity: LinearVelocity
    get() = driveSensor.velocity

  val steeringPosition: Angle
    get() = steeringSensor.position

  private var speedSetPoint: LinearVelocity = 0.feet.perSecond
  private var accelerationSetPoint: LinearAcceleration = 0.feet.perSecond.perSecond

  private var steeringSetPoint: Angle = 0.degrees
    set(value) {
      // Logger.addEvent("Drivetrain", "label: $label, value: ${value.inDegrees}, reference raw
      // position: ${steeringSensor.positionToRawUnits(value)}, current raw position:
      // ${steeringSensor.getRawPosition()}")
      if (filter.calculate((steeringSensor.position).inRadians)
          .around(value.inRadians, (Constants.Drivetrain.ALLOWED_ANGLE_ERROR).inRadians)) {
        steeringFalcon.set(ControlMode.PercentOutput, 0.0)
      } else {
        steeringFalcon.set(ControlMode.Position, steeringSensor.positionToRawUnits(value))
      }

      field = value
    }

  init {
    driveFalcon.configFactoryDefault()
    steeringFalcon.configFactoryDefault()

    driveFalcon.clearStickyFaults()
    steeringFalcon.clearStickyFaults()

    // Logger.addSource("$label Drivetrain", "Drive Faults") { driveFalcon.getFaults() }
    // Logger.addSource("$label Drivetrain", "Steering Faults") { steeringFalcon.getFaults() }

    Logger.addSource("$label Drivetrain", "Drive Output Current") { driveOutputCurrent }
    Logger.addSource("$label Drivetrain", "Steering Output Current") { steeringOutputCurrent }

    Logger.addSource("$label Drivetrain", "Drive Temperature") { driveTemp }
    Logger.addSource("$label Drivetrain", "Steering Temperature") { steeringTemp }

    Logger.addSource("$label Drivetrain", "Drive Percent Output") { drivePercentOutput }
    Logger.addSource("$label Drivetrain", "Steering Percent Output") { steeringPercentOutput }

    Logger.addSource("$label Drivetrain", "Drive Bus Voltage") { driveBusVoltage }
    Logger.addSource("$label Drivetrain", "Steering Bus Voltage") { steeringBusVoltage }

    Logger.addSource("$label Drivetrain", "Drive SetPoint") { speedSetPoint.inFeetPerSecond }
    Logger.addSource("$label Drivetrain", "Steering SetPoint") { steeringSetPoint.inDegrees }

    Logger.addSource("$label Drivetrain", "Drive Position") { driveSensor.position.inFeet }
    Logger.addSource("$label Drivetrain", "Steering Position") { steeringPosition.inDegrees }

    Logger.addSource(
        "Drivetrain Tuning",
        "$label Azimuth kP",
        { Constants.Drivetrain.PID.STEERING_KP },
        { newP -> steeringFalcon.config_kP(0, newP) },
        false)

    steeringFalcon.config_kP(0, Constants.Drivetrain.PID.STEERING_KP)
    steeringFalcon.config_kI(0, Constants.Drivetrain.PID.STEERING_KI)
    // steeringPID.iZone = 0.0
    steeringFalcon.config_kD(0, Constants.Drivetrain.PID.STEERING_KD)
    steeringFalcon.config_kF(0, Constants.Drivetrain.PID.STEERING_KFF)
    steeringFalcon.configMotionCruiseVelocity(
        steeringSensor.velocityToRawUnits(Constants.Drivetrain.STEERING_VEL_MAX))
    steeringFalcon.configMotionAcceleration(
        steeringSensor.accelerationToRawUnits(Constants.Drivetrain.STEERING_ACCEL_MAX))
    steeringFalcon.configPeakOutputForward(1.0)
    steeringFalcon.configPeakOutputReverse(-1.0)
    steeringFalcon.configAllowableClosedloopError(
        0, steeringSensor.positionToRawUnits(Constants.Drivetrain.ALLOWED_ANGLE_ERROR))
    // steeringFalcon.outputCurrent(Constants.Drivetrain.STEERING_SMART_CURRENT_LIMIT)

    driveFalcon.config_kP(0, Constants.Drivetrain.PID.DRIVE_KP)
    driveFalcon.config_kI(0, Constants.Drivetrain.PID.DRIVE_KI)
    driveFalcon.config_kD(0, Constants.Drivetrain.PID.DRIVE_KD)
    driveFalcon.config_kF(0, Constants.Drivetrain.PID.DRIVE_KFF)
    // driveFalcon.setSmartCurrentLimit(Constants.Drivetrain.DRIVE_SMART_CURRENT_LIMIT)
  }

  fun set(
    steering: Angle,
    speed: LinearVelocity,
    acceleration: LinearAcceleration = 0.0.meters.perSecond.perSecond
  ) {
    if (speed == 0.feet.perSecond) {
      driveFalcon.set(ControlMode.PercentOutput, 0.0)
    }
    var steeringDifference =
        (steering - steeringSensor.position).inRadians.IEEErem(2 * Math.PI).radians

    val isInverted = steeringDifference.absoluteValue > (Math.PI / 2).radians
    if (isInverted) {
      steeringDifference -= Math.PI.withSign(steeringDifference.inRadians).radians
    }

    speedSetPoint =
        if (isInverted) {
          speed * -1
        } else {
          speed
        }
    accelerationSetPoint =
        if (isInverted) {
          acceleration * -1
        } else {
          acceleration
        }
    steeringSetPoint = steeringSensor.position + steeringDifference
    //    driveFalcon.set(speedSetPoint / Constants.Drivetrain.DRIVE_SETPOINT_MAX)

    driveFalcon.set(
        ControlMode.Velocity,
        driveSensor.velocityToRawUnits(speedSetPoint),
        DemandType.ArbitraryFeedForward,
        (Constants.Drivetrain.PID.DRIVE_KS * sign(speedSetPoint.value) +
                speedSetPoint * Constants.Drivetrain.PID.DRIVE_KV +
                acceleration * Constants.Drivetrain.PID.DRIVE_KA).inVolts)
  }

  fun setOpenLoop(steering: Angle, speed: Double) {
    var steeringDifference =
        (steering - steeringSensor.position).inRadians.IEEErem(2 * Math.PI).radians

    val isInverted = steeringDifference.absoluteValue > (Math.PI / 2).radians
    if (isInverted) {
      steeringDifference -= Math.PI.withSign(steeringDifference.inRadians).radians
    }

    val outputPower =
        if (isInverted) {
          speed * -1
        } else {
          speed
        }
    steeringSetPoint = steeringSensor.position + steeringDifference
    driveFalcon.set(ControlMode.PercentOutput, outputPower)
  }

  fun resetModuleZero() {
    Logger.addEvent("Drivetrain", "Absolute Potentiometer Value $label (${potentiometer.get()}")
  }

  fun zeroSteering() {
    steeringFalcon.selectedSensorPosition =
        steeringSensor.positionToRawUnits(
            potentiometer.get().radians + zeroOffset.inRadians.radians)
    Logger.addEvent(
        "Drivetrain",
        "Loading Zero for Module $label (${potentiometer.get() + zeroOffset.inRadians})")
  }

  fun zeroDrive() {
    driveFalcon.selectedSensorPosition = 0.0
  }
}
