package com.team4099.robot2022.commands.pivotClimber

import com.team4099.lib.logging.Logger
import com.team4099.lib.units.derived.inDegrees
import com.team4099.robot2022.config.Constants
import com.team4099.robot2022.subsystems.PivotClimber
import com.team4099.robot2022.subsystems.TelescopingClimber
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.CommandBase

class RotateOutPivotArmCommand : CommandBase() {
  var goal: TrapezoidProfile.State = TrapezoidProfile.State(0.0, 0.0)
  lateinit var leftProfile: TrapezoidProfile
  lateinit var rightProfile: TrapezoidProfile

  init {
    addRequirements(PivotClimber)
    goal = TrapezoidProfile.State(Constants.PivotClimber.PivotArmPosition.OUT.angle.inDegrees, 0.0)
  }

  override fun initialize() {
    Logger.addEvent("Pivot Climber", "Pivot Climber moved outwards")
  }

  override fun execute() {
    leftProfile = TrapezoidProfile(PivotClimber.constraints, goal, PivotClimber.leftSetpoint)
    rightProfile = TrapezoidProfile(PivotClimber.constraints, goal, PivotClimber.rightSetpoint)
    TelescopingClimber.setPosition(leftProfile, rightProfile, false)
  }
}
