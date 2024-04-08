package com.example.cmpt395solaris.database.dayschedule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cmpt395solaris.database.DatabaseHelper

class employeescheduleviewmodel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)

//    fun addShift(eadate: String, amAvailability: String, pmAvailability: String, adAvailability: String) {
////        dbHelper.addShift(eadate, amAvailability, pmAvailability, adAvailability)
//    }

//    fun deleteShift(eadate: String) {
//        dbHelper.deleteAvailability(eadate)
//    }
}