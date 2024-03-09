/**
 * Employee View Model v1.00
 *
 *      - Database view model
 */

package com.example.cmpt395aurora.database.employees

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cmpt395aurora.database.DatabaseHelper


class EmployeeViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)

    fun addEmployee(name: String, position: String) {
        dbHelper.addEmployee(name, position)
    }

    fun getAllEmployees(): List<Employee> {
        return dbHelper.getAllEmployees()
    }

    fun deleteEmployee(id: Int) {
        dbHelper.deleteEmployee(id)
    }
}