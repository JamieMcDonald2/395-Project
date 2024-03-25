/**
 * Employee View Model v1.04
 *
 *      - Database view model
 *
 *  v1.04
 *  - add update employee call to function from dbhelper
 *
 *  v1.03
 *  - changed position field to phone number
 *
 *  v1.02
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
    val pnumber = mutableStateOf("")
    val isActive = mutableStateOf(false)
    val opening = mutableStateOf(false)
    val closing = mutableStateOf(false)

    private val dbHelper = DatabaseHelper(application)

    fun addEmployee(fname: String, lname: String, nname: String, email: String, pnumber: String, isActive: Boolean, opening: Boolean, closing: Boolean) {
        dbHelper.addEmployee(fname, lname, nname, email, pnumber, isActive, opening, closing)
    }

    fun getAllEmployees(): List<Employee> {
        return dbHelper.getAllEmployees()
    }

    fun getEmployeeByID(id: Int): Employee? {
        return dbHelper.getEmployeeById(id)
    }

    fun deleteEmployee(id: Int) {
        dbHelper.deleteEmployee(id)
    }

    fun updateEmployee(updatedEmployee: Employee) {
        dbHelper.updateEmployee(updatedEmployee)
    }

    fun validateFields(): Boolean {
        // Add your validation logic here. For example:
        return fname.value.isNotEmpty() &&
            lname.value.isNotEmpty() &&
            nname.value.isNotEmpty() &&
            email.value.isNotEmpty() &&
            pnumber.value.isNotEmpty()
    }
}