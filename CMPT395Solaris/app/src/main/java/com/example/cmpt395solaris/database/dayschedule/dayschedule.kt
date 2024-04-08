/**
 * DaySchedule Data Class
 */

package com.example.cmpt395solaris.database.dayschedule

data class DaySchedule(
    val dsdate: String,
    var employeeAM1: Int,
    var employeeAM2: Int,
    var employeeAM3: Int,
    var employeePM1: Int,
    var employeePM2: Int,
    var employeePM3: Int
)