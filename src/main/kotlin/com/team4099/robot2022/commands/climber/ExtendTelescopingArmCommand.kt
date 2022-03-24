package com.team4099.robot2022.commands.climber

import com.team4099.lib.hal.Clock
import com.team4099.lib.units.base.Time
import com.team4099.lib.units.base.inMeters
import com.team4099.lib.units.base.inSeconds
import com.team4099.lib.units.inMetersPerSecond
import com.team4099.robot2022.config.constants.TelescopingClimberConstants
import com.team4099.robot2022.subsystems.climber.TelescopingClimber
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class ExtendTelescopingArmCommand(val telescopingClimber: TelescopingClimber) : CommandBase() {

  lateinit var leftTelescopingProfile: TrapezoidProfile
  lateinit var rightTelescopingProfile: TrapezoidProfile

  var startTime: Time

  init {
    addRequirements(telescopingClimber)
    telescopingClimber.desiredState =
        TelescopingClimberConstants.DesiredTelescopeStates.MAX_EXTENSION
  }



  override fun initialize() {
    leftTelescopingProfile =
      TrapezoidProfile(
        telescopingClimber.constraints,
        TrapezoidProfile.State(telescopingClimber.desiredState.position.inMeters, 0.0),
        TrapezoidProfile.State(
          telescopingClimber.inputs.leftPosition.inMeters,
          telescopingClimber.inputs.leftVelocity.inMetersPerSecond))


    rightTelescopingProfile =
      TrapezoidProfile(
        telescopingClimber.constraints,
        TrapezoidProfile.State(telescopingClimber.desiredState.position.inMeters, 0.0),
        TrapezoidProfile.State(
          telescopingClimber.inputs.rightPosition.inMeters,
          telescopingClimber.inputs.rightVelocity.inMetersPerSecond))

    startTime = Clock.fpgaTime

  }

  override fun execute() {
    telescopingClimber.setPosition(
      leftTelescopingProfile.calculate((Clock.fpgaTime - startTime).inSeconds), rightTelescopingProfile.calculate((Clock.fpgaTime - startTime).inSeconds), isUnderLoad = false)

    Logger.getInstance().recordOutput("ActiveCommands/ExtendTelescopingArmCommand", true)
  }

  override fun isFinished(): Boolean {
//    return telescopingClimber.currentState.correspondingDesiredState ==
//        telescopingClimber.desiredState
    leftTelescopingProfile.isFinished()
  }
}
