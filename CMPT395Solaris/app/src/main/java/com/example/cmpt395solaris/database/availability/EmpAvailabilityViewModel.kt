package com.example.cmpt395solaris.database.availability

import android.app.Application
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.cmpt395solaris.database.DatabaseHelper

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

    fun updateAvailability(updatedAvailability: EmpAvail) {
        dbHelper.updateAvailability(updatedAvailability)
    }


    fun updateAvailabilityInfo() {
        // Create a new Employee instance from the ViewModel fields
        val currentAvailability = EmpAvail(
            id = id.intValue,
            mondayAM = mondayAM.value,
            mondayPM = mondayPM.value,
            tuesdayAM = tuesdayAM.value,
            tuesdayPM = tuesdayPM.value,
            wednesdayAM = wednesdayAM.value,
            wednesdayPM = wednesdayPM.value,
            thursdayAM = thursdayAM.value,
            thursdayPM = thursdayPM.value,
            fridayAM = fridayAM.value,
            fridayPM = fridayPM.value,
            saturday = saturday.value,
            sunday = sunday.value,
        )

        // Check if the original employee is different from the current state of the ViewModel fields
        if (originalAvailability.value != currentAvailability) {
            // If they are different, update the employee info in the ViewModel and the database
            updateAvailability(currentAvailability)
            originalAvailability.value = currentAvailability.copy()
        }
    }


    fun loadViewModel(avail: EmpAvail){
        id.intValue = avail.id
        mondayAM.value = avail.mondayAM
        mondayPM.value = avail.mondayPM
        tuesdayAM.value = avail.tuesdayAM
        tuesdayPM.value = avail.tuesdayPM
        wednesdayAM.value = avail.wednesdayAM
        wednesdayPM.value = avail.wednesdayPM
        thursdayAM.value = avail.thursdayAM
        thursdayPM.value = avail.thursdayPM
        fridayAM.value = avail.fridayAM
        fridayPM.value = avail.fridayPM
        saturday.value = avail.saturday
        sunday.value = avail.sunday
    }



    //functions related to editing employees
    fun loadAvailability(id: Int) {
        val avail = getAvailability(id)
        originalAvailability.value = avail?.copy() // Save a copy of the initial state

        // Update the ViewModel fields
        this.id.intValue = avail?.id ?: 0
        this.mondayAM.value = avail?.mondayAM ?: false
        this.mondayPM.value = avail?.mondayPM ?: false
        this.tuesdayAM.value = avail?.tuesdayAM ?: false
        this.tuesdayPM.value = avail?.tuesdayPM ?: false
        this.wednesdayAM.value = avail?.wednesdayAM ?: false
        this.wednesdayPM.value = avail?.wednesdayPM ?: false
        this.thursdayAM.value = avail?.thursdayAM ?: false
        this.thursdayPM.value = avail?.thursdayPM ?: false
        this.fridayAM.value = avail?.fridayAM ?: false
        this.fridayPM.value = avail?.fridayPM ?: false
        this.saturday.value = avail?.saturday ?: false
        this.sunday.value = avail?.sunday ?: false
    }

//    fun deleteAvailability(eadate: String) {
//        dbHelper.deleteAvailability(eadate)
//    }
}