package com.example.cmpt395aurora.database.dayschedule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cmpt395aurora.database.DatabaseHelper

class employeescheduleviewmodel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)

    fun addShift(eadate: String, employeeid: String, amAvailability: String, pmAvailability: String, adAvailability: String) {
        dbHelper.addShift(eadate, employeeid, amAvailability, pmAvailability, adAvailability)
    }

    fun deleteShift(eadate: String, employeeid: String) {
        dbHelper.deleteAvailability(eadate, employeeid)
    }
}