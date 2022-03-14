package com.team4099.robot2022.subsystems.drivetrain

import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.kauailabs.navx.frc.AHRS
import com.team4099.lib.units.AngularVelocity
import com.team4099.lib.units.derived.Angle
import com.team4099.lib.units.derived.degrees
import com.team4099.lib.units.derived.inDegrees
import com.team4099.lib.units.perSecond
import com.team4099.robot2022.config.constants.Constants
import com.team4099.robot2022.config.constants.Constants.Universal.CANIVORE_NAME
import com.team4099.robot2022.config.constants.DrivetrainConstants
import edu.wpi.first.wpilibj.AnalogPotentiometer
import edu.wpi.first.wpilibj.SerialPort
import java.lang.Math.PI
import kotlin.math.IEEErem

object DrivetrainIOReal : DrivetrainIO {
  override fun getSwerveModules(): List<SwerveModule> {
    return listOf(
        SwerveModule(
            SwerveModuleIOReal(
                TalonFX(Constants.Drivetrain.FRONT_LEFT_STEERING_ID, CANIVORE_NAME),
                TalonFX(Constants.Drivetrain.FRONT_LEFT_DRIVE_ID, CANIVORE_NAME),
                AnalogPotentiometer(
                    Constants.Drivetrain.FRONT_LEFT_ANALOG_POTENTIOMETER, 2 * PI, 0.0),
                DrivetrainConstants.FRONT_LEFT_MODULE_ZERO,
                "Front Left Wheel")),
        SwerveModule(
            SwerveModuleIOReal(
                TalonFX(Constants.Drivetrain.FRONT_RIGHT_STEERING_ID, CANIVORE_NAME),
                TalonFX(Constants.Drivetrain.FRONT_RIGHT_DRIVE_ID, CANIVORE_NAME),
                AnalogPotentiometer(
                    Constants.Drivetrain.FRONT_RIGHT_ANALOG_POTENTIOMETER, 2 * PI, 0.0),
                DrivetrainConstants.FRONT_RIGHT_MODULE_ZERO,
                "Front Right Wheel")),
        SwerveModule(
            SwerveModuleIOReal(
                TalonFX(Constants.Drivetrain.BACK_LEFT_STEERING_ID, CANIVORE_NAME),
                TalonFX(Constants.Drivetrain.BACK_LEFT_DRIVE_ID, CANIVORE_NAME),
                AnalogPotentiometer(
                    Constants.Drivetrain.BACK_LEFT_ANALOG_POTENTIOMETER, 2 * PI, 0.0),
                DrivetrainConstants.BACK_LEFT_MODULE_ZERO,
                "Back Left Wheel")),
        SwerveModule(
            SwerveModuleIOReal(
                TalonFX(Constants.Drivetrain.BACK_RIGHT_STEERING_ID, CANIVORE_NAME),
                TalonFX(Constants.Drivetrain.BACK_RIGHT_DRIVE_ID, CANIVORE_NAME),
                AnalogPotentiometer(
                    Constants.Drivetrain.BACK_RIGHT_ANALOG_POTENTIOMETER, 2 * PI, 0.0),
                DrivetrainConstants.BACK_RIGHT_MODULE_ZERO,
                "Back Right Wheel")))
  }

  private val gyro = AHRS(SerialPort.Port.kMXP)

  init {
    gyro.calibrate()
  }

  var gyroOffset: Angle = 0.0.degrees

  /** The current angle of the drivetrain. */
  val gyroAngle: Angle
    get() {
      if (gyro.isConnected) {
        var rawAngle = gyro.angle + gyroOffset.inDegrees
        rawAngle += DrivetrainConstants.GYRO_RATE_COEFFICIENT * gyro.rate
        return rawAngle.IEEErem(360.0).degrees
      } else {
        return -1.337.degrees
      }
    }

  val gyroRate: AngularVelocity
    get() {
      if (gyro.isConnected) {
        return gyro.rate.degrees.perSecond
      } else {
        return -1.337.degrees.perSecond
      }
    }

  override fun updateInputs(inputs: DrivetrainIO.DrivetrainIOInputs) {
    inputs.gyroAngle = gyroAngle
    inputs.gyroVelocity = gyroRate

    inputs.gyroConnected = gyro.isConnected
  }

  override fun zeroGyro(toAngle: Angle) {
    gyroOffset = (toAngle.inDegrees - gyro.angle).degrees
  }
}
