import com.team4099.lib.logging.Logger
import com.team4099.lib.units.base.inInches
import com.team4099.robot2022.config.Constants
import com.team4099.robot2022.subsystems.Climber
import edu.wpi.first.wpilibj2.command.CommandBase

class OpenLoopClimbCommand(private val power: () -> Double) : CommandBase() {

  init {
    //addRequirements(Climber)
  }

  override fun initialize() {
    Logger.addEvent("Climber", "Climber power at ${power()}")
  }

  override fun isFinished(): Boolean {
    return false
  }
  //******SECTIONS COMMENTED OUT TO AVOID ERRORS TEMPORARILY*****
  //override fun execute() {
  //  val powerDifference =
  //    (Climber.climberTArm.position - Climber.climberPArm.position).inInches *
  //      Constants.Climber.POSITION_P

  //  if (!Climber.brakeApplied) {
  //    Climber.setOpenLoopPower(power() - powerDifference, power() + powerDifference)
  //  }
  //}

  //override fun end(interrupted: Boolean) {
  //  Climber.setOpenLoopPower(0.0, 0.0)
  }
}
