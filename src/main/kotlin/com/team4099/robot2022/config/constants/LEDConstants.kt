package com.team4099.robot2022.config.constants

import com.team4099.lib.drivers.BlinkinLedDriver
import com.team4099.lib.units.base.amps

object LEDConstants {
  val INTAKE_CURRENT_THRESHOLD = 15.amps
  val OUTAKE_CURRENT_THRESHOLD = 20.amps

  enum class LEDState(val red: Int, val green: Int, val blue: Int) {
    // Solid Orange (never used)
    IDLE(0, 0, 0),

    // Blinking Gold
    AUTO(0, 0, 0),

    // Solid Gold
    STANDING_ZERO(0, 0, 0),

    // Solid Purple
    STANDING_ONE(0, 0, 0),

    // Solid Green
    STANDING_TWO(0, 0, 0),

    // Solid Blue
    INTAKING(0, 0, 0),

    // Solid Red
    OUTTAKING(0, 0, 0),

    // Changing Green
    CLIMBER_READY(0, 0, 0),

    // Changing Mix
    // CLIMBING(BlinkinLedDriver.BlinkinLedMode.FIXED_BEATS_PARTY),

    // Flashing Red
    // DISALLOW_CLIMB(BlinkinLedDriver.BlinkinLedMode.FIXED_HEARTBEAT_RED),

    // Changing Red
    // CLIMB_FINISHED_RED_ALLIANCE(BlinkinLedDriver.BlinkinLedMode.FIXED_BEATS_LAVA),

    // Changing Blue
    // CLIMB_FINISHED_BLUE_ALLIANCE(BlinkinLedDriver.BlinkinLedMode.FIXED_BEATS_OCEAN),

    // Speeding Blue
    // BLUE_SHOOT(BlinkinLedDriver.BlinkinLedMode.FIXED_CHASE_BLUE),

    // Speeding Red
    // RED_SHOOT(BlinkinLedDriver.BlinkinLedMode.FIXED_CHASE_RED)
  }

  enum class BlinkinLEDState(val blinkinMode: BlinkinLedDriver.BlinkinLedMode) {
    // Solid Orange (never used)
    IDLE(BlinkinLedDriver.BlinkinLedMode.SOLID_ORANGE),

    // Blinking Gold
    AUTO(BlinkinLedDriver.BlinkinLedMode.FIXED_STROBE_GOLD),

    // Solid Gold
    STANDING_ZERO(BlinkinLedDriver.BlinkinLedMode.SOLID_GOLD),

    // Solid Purple
    STANDING_ONE(BlinkinLedDriver.BlinkinLedMode.SOLID_VIOLET),

    // Solid Green
    STANDING_TWO(BlinkinLedDriver.BlinkinLedMode.SOLID_GREEN),

    // Solid Blue
    INTAKING(BlinkinLedDriver.BlinkinLedMode.SOLID_BLUE),

    // Solid Red
    OUTTAKING(BlinkinLedDriver.BlinkinLedMode.SOLID_DARK_RED),

    // Changing Green
    CLIMBER_READY(BlinkinLedDriver.BlinkinLedMode.FIXED_BEATS_FOREST),

    // Changing Mix
    CLIMBING(BlinkinLedDriver.BlinkinLedMode.FIXED_BEATS_PARTY),

    // Flashing Red
    DISALLOW_CLIMB(BlinkinLedDriver.BlinkinLedMode.FIXED_HEARTBEAT_RED),

    // Changing Red
    CLIMB_FINISHED_RED_ALLIANCE(BlinkinLedDriver.BlinkinLedMode.FIXED_BEATS_LAVA),

    // Changing Blue
    CLIMB_FINISHED_BLUE_ALLIANCE(BlinkinLedDriver.BlinkinLedMode.FIXED_BEATS_OCEAN),

    // Speeding Blue
    BLUE_SHOOT(BlinkinLedDriver.BlinkinLedMode.FIXED_CHASE_BLUE),

    // Speeding Red
    RED_SHOOT(BlinkinLedDriver.BlinkinLedMode.FIXED_CHASE_RED)
  }
}
