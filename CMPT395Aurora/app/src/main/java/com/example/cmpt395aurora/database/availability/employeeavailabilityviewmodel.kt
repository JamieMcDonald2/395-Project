package com.example.cmpt395aurora.database.availability

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cmpt395aurora.database.DatabaseHelper

class employeeavailabilityviewmodel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)

    fun addAvailability(eadate: String, amAvailability: String, pmAvailability: String, adAvailability: String) {
        dbHelper.addAvailability(eadate, amAvailability, pmAvailability, adAvailability)
    }

    fun deleteAvailability(eadate: String) {
        dbHelper.deleteAvailability(eadate)
    }
}