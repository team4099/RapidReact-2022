package com.team4099.robot2022.commands.pneumatics

import com.team4099.robot2022.subsystems.pneumatics.Pneumatic
import edu.wpi.first.wpilibj2.command.CommandBase
import org.littletonrobotics.junction.Logger
import java.util.function.Supplier

class PneumaticIdleCommand(val pneumatic: Pneumatic) : CommandBase() {
  init {
    addRequirements(pneumatic)
  }

  override fun initialize() {
    pneumatic.climbModeOverride = Supplier { true }
  }

  override fun execute() {
    Logger.getInstance().recordOutput("ActiveCommands/PneumaticIdleCommand", true)
  }

  override fun isFinished(): Boolean {
    return false
  }
}
