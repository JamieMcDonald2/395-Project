package com.example.cmpt395solaris.database.availability

import android.app.Application
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.cmpt395solaris.database.DatabaseHelper
import com.example.cmpt395solaris.database.employees.Employee

class EmpAvailabilityViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)
    val hasChanges = mutableStateOf(false)

    val id = mutableIntStateOf(0) // Int
    val mondayAM = mutableStateOf(false)
    val mondayPM = mutableStateOf(false)
    val tuesdayAM = mutableStateOf(false)
    val tuesdayPM = mutableStateOf(false)
    val wednesdayAM = mutableStateOf(false)
    val wednesdayPM = mutableStateOf(false)
    val thursdayAM = mutableStateOf(false)
    val thursdayPM = mutableStateOf(false)
    val fridayAM = mutableStateOf(false)
    val fridayPM = mutableStateOf(false)
    val saturday = mutableStateOf(false)
    val sunday = mutableStateOf(false)

    val originalAvailability = mutableStateOf<EmpAvail?>(null)

    fun addAvailability(avail: EmpAvail) {
        dbHelper.updateAvailability(avail)
    }

    fun getAvailability(id: Int): EmpAvail?{
        return dbHelper.getAvailabilityById(id)
    }

//    fun deleteAvailability(eadate: String) {
//        dbHelper.deleteAvailability(eadate)
//    }
}