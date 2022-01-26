package com.team4099.robot2022.commands.drivetrain

import edu.wpi.first.wpilibj2.command.CommandBase
import java.util.function.BiConsumer
import java.util.function.Supplier
import com.team4099.robot2022.commands.drivetrain.SysIdCommand.DriveTrainSysIdData
import com.team4099.robot2022.commands.drivetrain.SysIdCommand.MechanismSysIdData
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.Subsystem
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import java.util.function.Consumer

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
class SysIdCommand : CommandBase {
    private val isDriveTrain: Boolean
    private var driveTrainSetter: BiConsumer<Double, Double>? = null
    private var mechanismSetter: Consumer<Double>? = null
    private var driveTrainGetter: Supplier<DriveTrainSysIdData>? = null
    private var mechanismGetter: Supplier<MechanismSysIdData>? = null
    private var startTime = 0.0
    private var data: String? = null

    /** Creates a new SysIdCommand for a drive train.  */
    constructor(
        subsystem: Subsystem?,
        driveTrainSetter: BiConsumer<Double, Double>?,
        driveTrainGetter: Supplier<DriveTrainSysIdData>?
    ) {
        addRequirements(subsystem)
        isDriveTrain = true
        this.driveTrainSetter = driveTrainSetter
        this.driveTrainGetter = driveTrainGetter
    }

    /** Creates a new SysIdCommand for a generic mechanism.  */
    constructor(
        subsystem: Subsystem?, mechanismSetter: Consumer<Double>?,
        mechanismGetter: Supplier<MechanismSysIdData>?
    ) {
        addRequirements(subsystem)
        isDriveTrain = false
        this.mechanismSetter = mechanismSetter
        this.mechanismGetter = mechanismGetter
    }

    // Called when the command is initially scheduled.
    override fun initialize() {
        SmartDashboard.putBoolean("SysIdOverflow", false)
        SmartDashboard.putString("SysIdTelemetry", "")
        startTime = Timer.getFPGATimestamp()
        data = ""
    }

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() {
        val timestamp = Timer.getFPGATimestamp()

        // Check if running the correct test
        val test = SmartDashboard.getString("SysIdTest", "")
        val correctTest: Boolean
        correctTest = if (isDriveTrain) {
            test == "Drivetrain" || test == "Drivetrain (Angular)"
        } else {
            test == "Arm" || test == "Elevator" || test == "Simple"
        }
        SmartDashboard.putBoolean("SysIdWrongMech", !correctTest)

        // Wrong test, prevent movement
        if (!correctTest) {
            if (isDriveTrain) {
                driveTrainSetter!!.accept(0.0, 0.0)
            } else {
                mechanismSetter!!.accept(0.0)
            }
            return
        }

        // Calculate voltage
        val testType = SmartDashboard.getString("SysIdTestType", "")
        val voltageCommand = SmartDashboard.getNumber("SysIdVoltageCommand", 0.0)
        val rotate = SmartDashboard.getBoolean("SysIdRotate", false)
        val baseVoltage: Double
        baseVoltage = when (testType) {
            "Quasistatic" -> voltageCommand * (timestamp - startTime)
            "Dynamic" -> voltageCommand
            else -> 0.0
        }
        val primaryVoltage = baseVoltage * if (rotate) -1 else 1

        // Set output and get new data
        if (isDriveTrain) {
            driveTrainSetter!!.accept(primaryVoltage, baseVoltage)
            val subsystemData = driveTrainGetter!!.get()
            data += java.lang.Double.toString(timestamp) + ","
            data += java.lang.Double.toString(primaryVoltage) + ","
            data += java.lang.Double.toString(baseVoltage) + ","
            data += java.lang.Double.toString(subsystemData.leftPosRad / (2 * Math.PI)) + ","
            data += java.lang.Double.toString(subsystemData.rightPosRad / (2 * Math.PI)) + ","
            data += java.lang.Double.toString(subsystemData.leftVelRadPerSec / (2 * Math.PI)) + ","
            data += (java.lang.Double.toString(subsystemData.rightVelRadPerSec / (2 * Math.PI))
                    + ",")
            data += java.lang.Double.toString(subsystemData.gyroPosRad) + ","
            data += java.lang.Double.toString(subsystemData.gyroVelRadPerSec) + ","
        } else {
            mechanismSetter!!.accept(primaryVoltage)
            val subsystemData = mechanismGetter!!.get()
            data += java.lang.Double.toString(timestamp) + ","
            data += java.lang.Double.toString(primaryVoltage) + ","
            data += java.lang.Double.toString(subsystemData.posRad / (2 * Math.PI)) + ","
            data += java.lang.Double.toString(subsystemData.velRadPerSec / (2 * Math.PI)) + ","
        }
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) {
        if (data!!.length > 0) {
            SmartDashboard.putString(
                "SysIdTelemetry",
                data!!.substring(0, data!!.length - 1)
            )
            println(
                "Saved "
                        + java.lang.Long.toString(Math.round(data!!.length / 1024.0)) + " KB of data."
            )
        } else {
            println("No data to save. Something's gone wrong here...")
        }
        if (isDriveTrain) {
            driveTrainSetter!!.accept(0.0, 0.0)
        } else {
            mechanismSetter!!.accept(0.0)
        }
    }

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
        return false
    }

    /** SysId data for a drivetrain, returned by the subsystem.  */
    class DriveTrainSysIdData
    /**
     * Creates a new DriveTrainSysIdData.
     *
     * @param leftPosRad Left position (radians)
     * @param rightPosRad Right position (radians)
     * @param leftVelRadPerSec Left velocity (radians per second)
     * @param rightVelRadPerSec Right velocity (radians per second)
     * @param gyroPosRad Gyro position (radians)
     * @param gyroVelRadPerSec Gyro position (radians per second)
     */(
        val leftPosRad: Double, val rightPosRad: Double,
        val leftVelRadPerSec: Double, val rightVelRadPerSec: Double, val gyroPosRad: Double,
        val gyroVelRadPerSec: Double
    )

    /** SysId data for a generic mechanism, returned by the subsystem.  */
    class MechanismSysIdData
    /**
     * Creates a new MechanismSysIdData.
     *
     * @param posRad Position (radians)
     * @param velRadPerSec Velocity (radians per second)
     */(val posRad: Double, val velRadPerSec: Double)
}