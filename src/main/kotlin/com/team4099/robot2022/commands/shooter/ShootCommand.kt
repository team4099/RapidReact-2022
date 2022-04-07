package com.team4099.robot2022.commands.shooter

import com.team4099.robot2022.config.constants.FeederConstants
import com.team4099.robot2022.config.constants.ShooterConstants
import com.team4099.robot2022.subsystems.feeder.Feeder
import com.team4099.robot2022.subsystems.shooter.Shooter
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class ShootCommand(val shooter: Shooter, val feeder: Feeder) : CommandBase() {
  init {
    addRequirements(feeder, shooter)
  }
  //  var ballCount: Int = 0
  override fun initialize() {}

  override fun isFinished(): Boolean {
    return false
  }

  override fun execute() {
    //    ballCount = feeder.ballCount
    if (shooter.isOnTarget) {
      feeder.state = FeederConstants.FeederState.SHOOT
    } else {
      if (feeder.inputs.topBeamBroken) {
        feeder.state = FeederConstants.FeederState.NEUTRAL
      } else {
        feeder.state = FeederConstants.FeederState.SHOOT
      }
    }
    Logger.getInstance().recordOutput("ActiveCommands/ShootCommand", true)
  }

  override fun end(interrupted: Boolean) {
    shooter.state = ShooterConstants.ShooterState.IDLE
    feeder.state = FeederConstants.FeederState.NEUTRAL
    //    if (ballCount == 1){
    //      feeder.oneBallCheck = false
    //    }
  }
}
