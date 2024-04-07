package com.example.cmpt395solaris.database.availability



data class EmpAvail(
    val id: Int,
    val mondayAM: Boolean,
    val mondayPM: Boolean,
    val tuesdayAM: Boolean,
    val tuesdayPM: Boolean,
    val wednesdayAM: Boolean,
    val wednesdayPM: Boolean,
    val thursdayAM: Boolean,
    val thursdayPM: Boolean,
    val fridayAM: Boolean,
    val fridayPM: Boolean,
    val saturday: Boolean,
    val sunday: Boolean,
)