package com.example.cmpt395solaris.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmpt395solaris.database.employees.Employee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

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
}
