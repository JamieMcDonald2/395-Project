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

    fun addEmployee(fname: String, lname: String, nname: String, email: String, isActive: String, opening: String, closing: String, position: String) {
        dbHelper.addEmployee(fname, lname, nname, email, isActive, opening, closing, position)
    }

    fun getAllEmployees(): List<Employee> {
        return dbHelper.getAllEmployees()
    }

    fun deleteEmployee(id: Int) {
        dbHelper.deleteEmployee(id)
    }

    fun modifyEmployee(id: Int, newName: String, newPosition: String) {
        dbHelper.modifyEmployee(id, newName, newPosition)
    }
}