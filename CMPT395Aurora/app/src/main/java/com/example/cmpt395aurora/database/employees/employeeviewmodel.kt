/**
 * Employee View Model v1.05
 *
 *      - Employee database view model
 *
 *  v1.05
 *  - add id field
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
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.cmpt395aurora.database.DatabaseHelper

class EmployeeViewModel(application: Application) : AndroidViewModel(application) {
    // Define allEmployees as MutableLiveData
    val allEmployees: MutableLiveData<List<Employee>> = MutableLiveData()

    val id = mutableStateOf(0) // Int
    val idString = mutableStateOf("") // String
    val fname = mutableStateOf("")
    val lname = mutableStateOf("")
    val nname = mutableStateOf("")
    val email = mutableStateOf("")
    val pnumber = mutableStateOf("")
    val isActive = mutableStateOf(false)
    val opening = mutableStateOf(false)
    val closing = mutableStateOf(false)

    private val dbHelper = DatabaseHelper(application)

    fun setId(value: Int) {
        id.value = value
        idString.value = value.toString()
    }

    fun addEmployee(
        id: Int,
        fname: String,
        lname: String,
        nname: String,
        email: String,
        pnumber: String,
        isActive: Boolean,
        opening: Boolean,
        closing: Boolean
    ) {
        dbHelper.addEmployee(id, fname, lname, nname, email, pnumber, isActive, opening, closing)
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

    fun refreshEmployees() {
        allEmployees.value = dbHelper.getAllEmployees()
    }

    fun validateFields(): Boolean {
        Log.d("validateFields", "id: ${id.value}, fname: ${fname.value}, lname: ${lname.value}, nname: ${nname.value}, email: ${email.value}, pnumber: ${pnumber.value}") // testing
        val areFieldsValid = id.value != 0 &&
            fname.value.isNotEmpty() &&
            lname.value.isNotEmpty() &&
            nname.value.isNotEmpty() &&
            email.value.isNotEmpty() &&
            pnumber.value.isNotEmpty()

        Log.d("validateFields", "areFieldsValid: $areFieldsValid")  // testing
        return areFieldsValid
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        val isEmailValid = email.matches(emailRegex)

        Log.d("isValidEmail", "isEmailValid: $isEmailValid for email: $email")  // testing
        return isEmailValid
    }

    fun isValidPhoneNumber(pNumber: String): Boolean {
        val phoneRegex = "^[+]?[0-9]{10,15}$".toRegex()
        val cleanedNumber = pNumber.replace("[^\\d.]".toRegex(), "")
        val isPhoneNumberValid = cleanedNumber.matches(phoneRegex)

        Log.d(
            "isValidPhoneNumber",
            "isPhoneNumberValid: $isPhoneNumberValid for number: $cleanedNumber"
        )  // testing
        return isPhoneNumberValid
    }
    // clear fields on add employee screen
    fun clearEmployeeFields() {
        id.value = 0 // Int
        fname.value = ""
        lname.value = ""
        nname.value = ""
        email.value = ""
        pnumber.value = ""
        isActive.value = false
        opening.value = false
        closing.value = false
    }
}