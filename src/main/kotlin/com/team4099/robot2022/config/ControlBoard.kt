package com.team4099.robot2022.config

import com.team4099.lib.joystick.XboxOneGamepad
import com.team4099.robot2022.config.constants.Constants
import edu.wpi.first.wpilibj2.command.button.Trigger

/**
 * Maps buttons on the driver and operator controllers to specific actions with meaningful variable
 * names.
 */
object ControlBoard {
  private val driver = XboxOneGamepad(Constants.Joysticks.DRIVER_PORT)
  private val operator = XboxOneGamepad(Constants.Joysticks.SHOTGUN_PORT)
  private val technician = XboxOneGamepad(Constants.Joysticks.TECHNICIAN_PORT)

  val strafe: Double
    get() = -driver.leftXAxis

  val forward: Double
    get() = driver.leftYAxis

  val turn: Double
    get() = driver.rightXAxis

  val resetGyro = Trigger { driver.startButton && driver.selectButton }

  // buttons not final
  val runFeederIn = Trigger { operator.dPadUp }
  val runFeederOut = Trigger { operator.dPadDown }

  val runIntake = Trigger { operator.aButton }
  val prepareClimb = Trigger { operator.startButton }

  // Shooter triggers
  val startShooter = Trigger { operator.xButton }
  val startShooterFar = Trigger { operator.yButton }
  val outTake = Trigger { operator.bButton }
  //  val alignRobot = Trigger { operator.yButton }

  val extendTelescoping = Trigger { operator.rightShoulderButton }
  val retractTelescoping = Trigger { operator.leftShoulderButton }
  val rotateOutPivot: Double
    get() = operator.rightTriggerAxis
  val rotateInPivot: Double
    get() = operator.leftTriggerAxis

  val advanceAndClimb = Trigger { operator.startButton }
  val climbWithoutAdvance = Trigger { operator.selectButton }

  val leftSpoolDown = Trigger { technician.leftShoulderButton }
  val rightSpoolDown = Trigger { technician.rightShoulderButton }

  val leftSpoolUp = Trigger { technician.leftTriggerAxis > 0.5 }
  val rightSpoolUp = Trigger { technician.rightTriggerAxis > 0.5 }
}
