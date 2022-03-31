package com.team4099.robot2022.config.constants

object PneumaticConstants {
  const val REV_MODULE_ID = 1

  const val NORMAL_AVERAGING_TAPS = 25
  const val COMPRESSOR_AVERAGING_TAPS = 50
  const val COMPRESSOR_RATE_PSI_PER_SECOND = 1.5 // add psi stuff to units library

  const val CLIMB_PSI_REQ = 80.0

  enum class AllowClimb(val canClimb: Boolean) {
    CLIMB(true),
    NO_CLIMB(false)
  }
}
