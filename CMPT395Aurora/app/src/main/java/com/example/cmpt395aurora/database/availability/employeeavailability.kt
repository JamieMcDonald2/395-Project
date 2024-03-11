package com.example.cmpt395aurora.database.availability

data class EmpAvail(
    val eadate: String,
    val employeeid: Int,
    val amAvailability: Int,
    val pmAvailability: Int,
    val adAvailability: Int
)