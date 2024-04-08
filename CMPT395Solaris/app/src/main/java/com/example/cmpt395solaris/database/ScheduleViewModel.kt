package com.example.cmpt395solaris.database

import android.app.Application
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmpt395solaris.database.dayschedule.DaySchedule
import com.example.cmpt395solaris.database.employees.Employee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ScheduleViewModel(application: Application): AndroidViewModel(application) {

    private val dbHelper = DatabaseHelper(application)
    val hasChanges = mutableStateOf(false)

    val dsdate = mutableStateOf("")
    val employeeAM1 = mutableIntStateOf(-1)
    val employeeAM2 = mutableIntStateOf(-1)
    val employeeAM3 = mutableIntStateOf(-1)
    val employeePM1 = mutableIntStateOf(-1)
    val employeePM2 = mutableIntStateOf(-1)
    val employeePM3 = mutableIntStateOf(-1)

    var selectedEmployee1 = mutableStateOf<Employee?>(null)
    var selectedEmployee2 = mutableStateOf<Employee?>(null)
    var selectedEmployee3 = mutableStateOf<Employee?>(null)
    var selectedEmployee4 = mutableStateOf<Employee?>(null)
    var selectedEmployee5 = mutableStateOf<Employee?>(null)
    var selectedEmployee6 = mutableStateOf<Employee?>(null)

    var morningEmployees = mutableStateOf(listOf<Employee>())
    var eveningEmployees = mutableStateOf(listOf<Employee>())
    var fullDayEmployees = mutableStateOf(listOf<Employee>())
    var morningTrainedEmployees = mutableStateOf(listOf<Employee>())
    var eveningTrainedEmployees = mutableStateOf(listOf<Employee>())
    var bothTrainedEmployees = mutableStateOf(listOf<Employee>())

    var options1 = mutableStateOf(listOf<String>())
    var options2 = mutableStateOf(listOf<String>())
    var options3 = mutableStateOf(listOf<String>())
    var options4 = mutableStateOf(listOf<String>())

    val originalSchedule = mutableStateOf<DaySchedule?>(null)

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

    fun doesDsDateExist(dsdate: String): Boolean{
        return dbHelper.doesDsDateExist(dsdate)
    }

}
