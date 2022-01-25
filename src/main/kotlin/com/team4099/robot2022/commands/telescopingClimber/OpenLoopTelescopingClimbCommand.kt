import com.team4099.lib.logging.Logger
import edu.wpi.first.wpilibj2.command.CommandBase

class OpenLoopTelescopingClimbCommand(private val power: () -> Double) : CommandBase() {

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
