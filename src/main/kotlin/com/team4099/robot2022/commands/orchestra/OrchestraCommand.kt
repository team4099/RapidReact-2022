package com.team4099.robot2022.commands.orchestra

import com.team4099.robot2022.config.constants.OrchestraConstants
import com.team4099.robot2022.subsystems.orchestra.Orchestra
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger

class OrchestraCommand(val orchestra: Orchestra) : CommandBase() {
  init {
    addRequirements(orchestra)
  }

  override fun initialize() {
    orchestra.state = OrchestraConstants.OrchestraState.TEST
    orchestra.isPlaying = true
  }

  override fun execute() {
    Logger.getInstance().recordOutput("ActiveCommands/OrchestraCommand", true)
  }

  override fun isFinished(): Boolean {
    return false
  }
}
