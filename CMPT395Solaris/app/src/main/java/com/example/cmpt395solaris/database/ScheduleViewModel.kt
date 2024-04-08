package com.example.cmpt395solaris.database

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.cmpt395solaris.database.DatabaseHelper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmpt395solaris.database.dayschedule.DaySchedule
import com.example.cmpt395solaris.database.employees.Employee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedViewModel(application: Application)  : ViewModel() {

    private val dbHelper = DatabaseHelper(application)

    // Define StateFlow properties for selected employees for each day
    private val selectedEmployeesMap = mutableMapOf<String, List<Employee?>>()
    private val _selectedEmployeesFlow = MutableStateFlow<Map<String, List<Employee?>>>(selectedEmployeesMap)
    val selectedEmployeesFlow: StateFlow<Map<String, List<Employee?>>> = _selectedEmployeesFlow

    // Function to update selected employees for a specific date
    fun setSelectedEmployeesForDate(date: String, selectedEmployees: List<Employee?>) {
        viewModelScope.launch {
            // Update selected employees for the specified date
            selectedEmployeesMap[date] = selectedEmployees
            // Notify observers of the change
            _selectedEmployeesFlow.value = selectedEmployeesMap.toMap()
        }
    }

    fun addDaySchedule(schedule: DaySchedule): Boolean {
        return dbHelper.addDaySchedule(schedule)
    }

    fun getDaySchedule(dsdate: String): DaySchedule?{
        return dbHelper.getDaySchedule(dsdate)
    }

    fun updateDaySchedule(schedule: DaySchedule): Int {
        return dbHelper.updateDaySchedule(schedule)
    }

}
