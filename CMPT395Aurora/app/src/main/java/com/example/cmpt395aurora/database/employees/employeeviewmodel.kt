/**
 * Employee View Model v1.02
 *
 *      - Database view model
 *
 * v1.02
 *  - corrected variable types, added new validate function for text fields - to be updated later?
 */

package com.example.cmpt395aurora.database.employees

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.cmpt395aurora.database.DatabaseHelper

class EmployeeViewModel(application: Application) : AndroidViewModel(application) {
    val fname = mutableStateOf("")
    val lname = mutableStateOf("")
    val nname = mutableStateOf("")
    val email = mutableStateOf("")
    val position = mutableStateOf("")
    val isActive = mutableStateOf(false)
    val opening = mutableStateOf(false)
    val closing = mutableStateOf(false)

    private val dbHelper = DatabaseHelper(application)

    fun addEmployee(fname: String, lname: String, nname: String, email: String, position: String, isActive: Boolean, opening: Boolean, closing: Boolean) {
        dbHelper.addEmployee(fname, lname, nname, email, position, isActive, opening, closing)
    }

    fun getAllEmployees(): List<Employee> {
        return dbHelper.getAllEmployees()
    }

    fun deleteEmployee(id: Int) {
        dbHelper.deleteEmployee(id)
    }

    /*fun modifyEmployee(id: Int, newName: String, newPosition: String) {
        dbHelper.modifyEmployee(id, newName, newPosition)
    }*/

    fun validateFields(): Boolean {
        // Add your validation logic here. For example:
        return fname.value.isNotEmpty() &&
            lname.value.isNotEmpty() &&
            nname.value.isNotEmpty() &&
            email.value.isNotEmpty() &&
            position.value.isNotEmpty()
    }
}