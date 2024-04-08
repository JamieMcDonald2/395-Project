/**
 * DaySchedule Data Class
 */

package com.example.cmpt395solaris.database.dayschedule

data class DaySchedule(
    val dsdate: String,
    val employeeAM1: Int,
    val employeeAM2: Int,
    val employeeAM3: Int,
    val employeePM1: Int,
    val employeePM2: Int,
    val employeePM3: Int
)