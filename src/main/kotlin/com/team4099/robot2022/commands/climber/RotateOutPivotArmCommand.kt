package com.team4099.robot2022.commands.climber

import com.team4099.lib.logging.Logger
import com.team4099.lib.units.derived.inDegrees
import com.team4099.robot2022.config.constants.PivotClimberConstants
import com.team4099.robot2022.subsystems.climber.PivotClimber
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.CommandBase

class RotateOutPivotArmCommand(val pivotClimber: PivotClimber) : CommandBase() {
  var goal: TrapezoidProfile.State = TrapezoidProfile.State(0.0, 0.0)
  lateinit var leftProfile: TrapezoidProfile
  lateinit var rightProfile: TrapezoidProfile

  init {
    addRequirements(pivotClimber)
    goal = TrapezoidProfile.State(PivotClimberConstants.PivotArmPosition.OUT.angle.inDegrees, 0.0)
  }

  override fun initialize() {
    Logger.addEvent("Pivot Climber", "Pivot Climber moved outwards")
  }

  override fun execute() {
    leftProfile = TrapezoidProfile(pivotClimber.constraints, goal, pivotClimber.leftSetpoint)
    rightProfile = TrapezoidProfile(pivotClimber.constraints, goal, pivotClimber.rightSetpoint)
    pivotClimber.setAngle(leftProfile, rightProfile, false)
  }
}
