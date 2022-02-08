package com.team4099.robot2022.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.DemandType
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration
import com.team4099.lib.logging.Logger
import com.team4099.lib.units.AngularMechanismSensor
import com.team4099.lib.units.LinearAcceleration
import com.team4099.lib.units.LinearVelocity
import com.team4099.lib.units.Timescale
import com.team4099.lib.units.base.Length
import com.team4099.lib.units.base.feet
import com.team4099.lib.units.base.inFeet
import com.team4099.lib.units.base.inSeconds
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
import com.team4099.lib.units.inRotationsPerSecond
import com.team4099.lib.units.inRotationsPerSecondPerSecond
import com.team4099.lib.units.perSecond
import com.team4099.robot2022.config.constants.DrivetrainConstants
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
          DrivetrainConstants.STEERING_SENSOR_CPR,
          DrivetrainConstants.STEERING_SENSOR_GEAR_RATIO)
  private val driveSensor =
      ctreLinearMechanismSensor(
          driveFalcon,
          DrivetrainConstants.DRIVE_SENSOR_CPR,
          DrivetrainConstants.DRIVE_SENSOR_GEAR_RATIO,
          DrivetrainConstants.WHEEL_DIAMETER)

  private val steeringAbsolute =
      AngularMechanismSensor(
          DrivetrainConstants.ABSOLUTE_GEAR_RATIO, Timescale.CTRE, { 0.0 }, { potentiometer.get() })

  private val filter = MedianFilter(10)

  // motor params
  private val steeringConfiguration: TalonFXConfiguration = TalonFXConfiguration()
  private val driveConfiguration: TalonFXConfiguration = TalonFXConfiguration()

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
      //      if (filter.calculate((steeringSensor.position).inRadians)
      //          .around(value.inRadians, (DrivetrainConstants.ALLOWED_ANGLE_ERROR).inRadians)) {
      //        steeringFalcon.set(ControlMode.PercentOutput, 0.0)
      //      } else {
      steeringFalcon.set(ControlMode.MotionMagic, steeringSensor.positionToRawUnits(value))
      //      }

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

    Logger.addSource("$label Drivetrain", "Steering Position Raw") { potentiometer.get() }

    Logger.addSource(
        "Drivetrain Tuning",
        "$label Azimuth kP",
        { DrivetrainConstants.PID.STEERING_KP },
        { newP -> steeringFalcon.config_kP(0, newP) },
        false)

    steeringConfiguration.slot0.kP = DrivetrainConstants.PID.STEERING_KP
    steeringConfiguration.slot0.kI = DrivetrainConstants.PID.STEERING_KI
    steeringConfiguration.slot0.kD = DrivetrainConstants.PID.STEERING_KD
    steeringConfiguration.slot0.kF = DrivetrainConstants.PID.STEERING_KFF
    //    steeringConfiguration.motionCruiseVelocity =
    //        steeringSensor.velocityToRawUnits(DrivetrainConstants.STEERING_VEL_MAX)
    //    steeringConfiguration.motionAcceleration =
    //        steeringSensor.accelerationToRawUnits(DrivetrainConstants.STEERING_ACCEL_MAX)
    steeringConfiguration.motionCruiseVelocity = DrivetrainConstants.STEERING_VEL_NATIVE_MAX.inRotationsPerSecond * Timescale.CTRE.velocity.inSeconds / (DrivetrainConstants.STEERING_SENSOR_GEAR_RATIO / DrivetrainConstants.STEERING_SENSOR_CPR)
    steeringConfiguration.motionAcceleration = DrivetrainConstants.STEERING_ACCEL_NATIVE_MAX.inRotationsPerSecondPerSecond * Timescale.CTRE.velocity.inSeconds * Timescale.CTRE.acceleration.inSeconds / (DrivetrainConstants.STEERING_SENSOR_GEAR_RATIO / DrivetrainConstants.STEERING_SENSOR_CPR)
    steeringConfiguration.peakOutputForward = 1.0
    steeringConfiguration.peakOutputReverse = -1.0
    steeringConfiguration.supplyCurrLimit.currentLimit =
        DrivetrainConstants.STEERING_SUPPLY_CURRENT_LIMIT
    steeringConfiguration.supplyCurrLimit.enable = true

    steeringFalcon.setNeutralMode(NeutralMode.Coast)
    steeringFalcon.inverted = false
    steeringFalcon.configAllSettings(steeringConfiguration)
    steeringFalcon.configAllowableClosedloopError(
        0, steeringSensor.positionToRawUnits(DrivetrainConstants.ALLOWED_ANGLE_ERROR))

    driveConfiguration.slot0.kP = DrivetrainConstants.PID.DRIVE_KP
    driveConfiguration.slot0.kI = DrivetrainConstants.PID.DRIVE_KI
    driveConfiguration.slot0.kD = DrivetrainConstants.PID.DRIVE_KD
    driveConfiguration.slot0.kF = DrivetrainConstants.PID.DRIVE_KFF
    driveConfiguration.supplyCurrLimit.currentLimit = DrivetrainConstants.DRIVE_SUPPLY_CURRENT_LIMIT

    driveFalcon.configAllSettings(driveConfiguration)
    driveFalcon.setNeutralMode(NeutralMode.Coast)
  }

  /**
   * Sets the swerve module to the specified angular and X & Y velocities using feed forward.
   *
   * @param steering The angular position desired for the swerve module to be set to
   * @param speed The speed desired for the swerve module to reach
   * @param acceleration The linear acceleration used to calculate how to reach the desired speed
   */
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
    //    driveFalcon.set(speedSetPoint / DrivetrainConstants.DRIVE_SETPOINT_MAX)

    driveFalcon.set(
        ControlMode.Velocity,
        driveSensor.velocityToRawUnits(speedSetPoint),
        DemandType.ArbitraryFeedForward,
        (DrivetrainConstants.PID.DRIVE_KS * sign(speedSetPoint.value) +
                speedSetPoint * DrivetrainConstants.PID.DRIVE_KV +
                acceleration * DrivetrainConstants.PID.DRIVE_KA).inVolts)
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

  /** Creates event of the current potentiometer value as needs to be manually readjusted. */
  fun resetModuleZero() {
    Logger.addEvent("Drivetrain", "Absolute Potentiometer Value $label (${potentiometer.get()}")
  }

  /** Zeros the steering motor */
  fun zeroSteering() {
    steeringFalcon.selectedSensorPosition =
        steeringSensor.positionToRawUnits(
            -(potentiometer.get().radians) + zeroOffset.inRadians.radians)
    Logger.addEvent(
        "Drivetrain",
        "Loading Zero for Module $label (${steeringSensor.positionToRawUnits(
          -(potentiometer.get().radians) + zeroOffset.inRadians.radians)})")
  }

  /** Zeros the drive motor */
  fun zeroDrive() {
    driveFalcon.selectedSensorPosition = 0.0
  }
}
