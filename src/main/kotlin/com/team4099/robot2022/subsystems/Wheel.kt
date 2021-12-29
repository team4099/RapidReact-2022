package com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.DemandType
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.ctre.phoenix.sensors.CANCoder
import com.team4099.lib.around
import com.team4099.lib.logging.Logger
import com.team4099.lib.units.AngularMechanismSensor
import com.team4099.lib.units.LinearAcceleration
import com.team4099.lib.units.LinearVelocity
import com.team4099.lib.units.Timescale
import com.team4099.lib.units.base.Length
import com.team4099.lib.units.base.feet
import com.team4099.lib.units.base.inFeet
import com.team4099.lib.units.base.inches
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
import kotlin.math.IEEErem
import kotlin.math.sign
import kotlin.math.withSign

class Wheel(
  private val directionFalcon: TalonFX,
  private val driveFalcon: TalonFX,
  private val encoder: CANCoder,
  private val zeroOffset: Angle,
  val label: String
) {

  private val directionSensor =
      ctreAngularMechanismSensor(
          directionFalcon,
          Constants.Drivetrain.DIRECTION_SENSOR_CPR,
          Constants.Drivetrain.DIRECTION_SENSOR_GEAR_RATIO)
  private val driveSensor =
      ctreLinearMechanismSensor(
          driveFalcon,
          Constants.Drivetrain.DRIVE_SENSOR_CPR,
          Constants.Drivetrain.DRIVE_SENSOR_GEAR_RATIO,
          3.inches)

  private val directionAbsolute =
      AngularMechanismSensor(
          Constants.Drivetrain.ABSOLUTE_GEAR_RATIO,
          Timescale.CTRE,
          { encoder.velocity },
          { Math.toRadians(encoder.absolutePosition) })

  private val filter = MedianFilter(10)

  // motor params
  private val driveTemp: Double
    get() = driveFalcon.temperature

  private val directionTemp: Double
    get() = directionFalcon.temperature

  private val driveOutputCurrent: Double
    get() = directionFalcon.statorCurrent

  private val directionOutputCurrent: Double
    get() = directionFalcon.statorCurrent

  private val drivePercentOutput: Double
    get() = driveFalcon.motorOutputPercent

  private val directionPercentOutput: Double
    get() = directionFalcon.motorOutputPercent

  private val driveBusVoltage: ElectricalPotential
    get() = driveFalcon.busVoltage.volts

  private val directionBusVoltage: ElectricalPotential
    get() = directionFalcon.busVoltage.volts

  val driveOutputVoltage: ElectricalPotential
    get() = driveBusVoltage * drivePercentOutput

  val driveDistance: Length
    get() = driveSensor.position

  val driveVelocity: LinearVelocity
    get() = driveSensor.velocity

  val directionPosition: Angle
    get() = directionSensor.position

  private var speedSetPoint: LinearVelocity = 0.feet.perSecond
  private var accelerationSetPoint: LinearAcceleration = 0.feet.perSecond.perSecond

  private var directionSetPoint: Angle = 0.degrees
    set(value) {
      // Logger.addEvent("Drivetrain", "label: $label, value: ${value.inDegrees}, reference raw
      // position: ${directionSensor.positionToRawUnits(value)}, current raw position:
      // ${directionSensor.getRawPosition()}")
      if (filter.calculate((directionSensor.position).inRadians)
          .around(value.inRadians, (Constants.Drivetrain.ALLOWED_ANGLE_ERROR).inRadians)) {
        directionFalcon.set(ControlMode.PercentOutput, 0.0)
      } else {
        directionFalcon.set(ControlMode.Position, directionSensor.positionToRawUnits(value))
      }

      field = value
    }

  init {
    driveFalcon.configFactoryDefault()
    directionFalcon.configFactoryDefault()

    driveFalcon.clearStickyFaults()
    directionFalcon.clearStickyFaults()

    // Logger.addSource("$label Drivetrain", "Drive Faults") { driveFalcon.getFaults() }
    // Logger.addSource("$label Drivetrain", "Direction Faults") { directionFalcon.getFaults() }

    Logger.addSource("$label Drivetrain", "Drive Output Current") { driveOutputCurrent }
    Logger.addSource("$label Drivetrain", "Direction Output Current") { directionOutputCurrent }

    Logger.addSource("$label Drivetrain", "Drive Temperature") { driveTemp }
    Logger.addSource("$label Drivetrain", "Direction Temperature") { directionTemp }

    Logger.addSource("$label Drivetrain", "Drive Percent Output") { drivePercentOutput }
    Logger.addSource("$label Drivetrain", "Direction Percent Output") { directionPercentOutput }

    Logger.addSource("$label Drivetrain", "Drive Bus Voltage") { driveBusVoltage }
    Logger.addSource("$label Drivetrain", "Direction Bus Voltage") { directionBusVoltage }

    Logger.addSource("$label Drivetrain", "Drive SetPoint") { speedSetPoint.inFeetPerSecond }
    Logger.addSource("$label Drivetrain", "Direction SetPoint") { directionSetPoint.inDegrees }

    Logger.addSource("$label Drivetrain", "Drive Position") { driveSensor.position.inFeet }
    Logger.addSource("$label Drivetrain", "Direction Position") { directionPosition.inDegrees }

    Logger.addSource(
        "Drivetrain Tuning",
        "$label Azimuth kP",
        { Constants.Drivetrain.PID.DIRECTION_KP },
        { newP -> directionFalcon.config_kP(0, newP) },
        false)

    directionFalcon.config_kP(0, Constants.Drivetrain.PID.DIRECTION_KP)
    directionFalcon.config_kI(0, Constants.Drivetrain.PID.DIRECTION_KI)
    // directionPID.iZone = 0.0
    directionFalcon.config_kD(0, Constants.Drivetrain.PID.DIRECTION_KD)
    directionFalcon.config_kF(0, Constants.Drivetrain.PID.DIRECTION_KFF)
    directionFalcon.configMotionCruiseVelocity(
        directionSensor.velocityToRawUnits(Constants.Drivetrain.DIRECTION_VEL_MAX))
    directionFalcon.configMotionAcceleration(
        directionSensor.accelerationToRawUnits(Constants.Drivetrain.DIRECTION_ACCEL_MAX))
    directionFalcon.configPeakOutputForward(1.0)
    directionFalcon.configPeakOutputReverse(-1.0)
    directionFalcon.configAllowableClosedloopError(
        0, directionSensor.positionToRawUnits(Constants.Drivetrain.ALLOWED_ANGLE_ERROR))
    // directionFalcon.outputCurrent(Constants.Drivetrain.DIRECTION_SMART_CURRENT_LIMIT)

    driveFalcon.config_kP(0, Constants.Drivetrain.PID.DRIVE_KP)
    driveFalcon.config_kI(0, Constants.Drivetrain.PID.DRIVE_KI)
    driveFalcon.config_kD(0, Constants.Drivetrain.PID.DRIVE_KD)
    driveFalcon.config_kF(0, Constants.Drivetrain.PID.DRIVE_KFF)
    // driveFalcon.setSmartCurrentLimit(Constants.Drivetrain.DRIVE_SMART_CURRENT_LIMIT)
  }

  fun set(
    direction: Angle,
    speed: LinearVelocity,
    acceleration: LinearAcceleration = 0.0.meters.perSecond.perSecond
  ) {
    if (speed == 0.feet.perSecond) {
      driveFalcon.set(ControlMode.PercentOutput, 0.0)
    }
    var directionDifference =
        (direction - directionSensor.position).inRadians.IEEErem(2 * Math.PI).radians

    val isInverted = directionDifference.absoluteValue > (Math.PI / 2).radians
    if (isInverted) {
      directionDifference -= Math.PI.withSign(directionDifference.inRadians).radians
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
    directionSetPoint = directionSensor.position + directionDifference
    //    driveFalcon.set(speedSetPoint / Constants.Drivetrain.DRIVE_SETPOINT_MAX)

    driveFalcon.set(
        ControlMode.Velocity,
        driveSensor.velocityToRawUnits(speedSetPoint),
        DemandType.ArbitraryFeedForward,
        (Constants.Drivetrain.PID.DRIVE_KS * sign(speedSetPoint.value) +
                speedSetPoint * Constants.Drivetrain.PID.DRIVE_KV +
                acceleration * Constants.Drivetrain.PID.DRIVE_KA).inVolts)
  }

  fun setOpenLoop(direction: Angle, speed: Double) {
    var directionDifference =
        (direction - directionSensor.position).inRadians.IEEErem(2 * Math.PI).radians

    val isInverted = directionDifference.absoluteValue > (Math.PI / 2).radians
    if (isInverted) {
      directionDifference -= Math.PI.withSign(directionDifference.inRadians).radians
    }

    val outputPower =
        if (isInverted) {
          speed * -1
        } else {
          speed
        }
    directionSetPoint = directionSensor.position + directionDifference
    driveFalcon.set(ControlMode.PercentOutput, outputPower)
  }

  fun resetModuleZero() {
    //    encoder.configFactoryDefault()
    //    encoder.configMagnetOffset(0.0)
    //    Logger.addEvent("Drivetrain", "Configuring Zero for Module $label")
    //    encoder.configMagnetOffset(
    //        -encoder.absolutePosition - zeroOffset.inDegrees - encoder.configGetMagnetOffset())
    //    encoder.setPositionToAbsolute()
    //
    // encoder.configSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition)
  }

  fun zeroDirection() {
    directionFalcon.selectedSensorPosition =
        directionSensor.positionToRawUnits(encoder.absolutePosition.degrees + zeroOffset)
    Logger.addEvent("Drivetrain", "Loading Zero for Module $label (${encoder.absolutePosition})")
  }

  fun zeroDrive() {
    driveFalcon.selectedSensorPosition = 0.0
  }
}
