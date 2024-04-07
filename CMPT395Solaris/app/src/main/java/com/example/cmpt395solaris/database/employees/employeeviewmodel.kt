/**
 * Employee View Model v1.4
 *
 *      - Employee database view model
 *
 *  v1.4
 *  - rewrote logic for edit employee screen:
 *      - added verification logic
 *      - removed 'updateEmployee' in favor of a new system that logs changes using flags and
 *        a comparison to the original data (originalEmployee)
 *
 *  v1.3
 *  - improved email and phone number verification as it was often failing when it shouldn't
 *
 *  v1.2
 *  - added query to see if any fields have been changed for confirmation dialogs
 *
 *  v1.1
 *  - new refresh query
 *  - moved sorting to new refresh query for employee list
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

package com.example.cmpt395solaris.database.employees

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.cmpt395solaris.database.DatabaseHelper

class EmployeeViewModel(application: Application) : AndroidViewModel(application) {
    // Define allEmployees as MutableLiveData
    var employees = mutableStateOf(listOf<Employee>())
    val hasChanges = mutableStateOf(false)

    val id = mutableIntStateOf(0) // Int
    val idString = mutableStateOf("") // String
    val fname = mutableStateOf("")
    val lname = mutableStateOf("")
    val nname = mutableStateOf("")
    val email = mutableStateOf("")
    val pnumber = mutableStateOf("")
    val isActive = mutableStateOf(false)
    val opening = mutableStateOf(false)
    val closing = mutableStateOf(false)

    val lastID = mutableIntStateOf(0)

    val originalEmployee = mutableStateOf<Employee?>(null)

    private val dbHelper = DatabaseHelper(application)

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
//        Log.d("addEmployee", "employee email: ${email}")  // testing
//        Log.d("addEmployee", "employee pnumber: ${pnumber}")  // testing
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
        refreshEmployees() // refresh employees after update
    }

    fun refreshEmployees() {
        val allEmployees = dbHelper.getAllEmployees()
        val activeEmployees = allEmployees.filter { it.isActive }.sortedBy { it.fname }
        val inactiveEmployees = allEmployees.filter { !it.isActive }.sortedBy { it.fname }
        employees.value = activeEmployees + inactiveEmployees
    }

    //functions related to editing employees
    fun loadEmployee(id: Int) {
        val employee = getEmployeeByID(id)
        originalEmployee.value = employee?.copy() // Save a copy of the initial state

        // Update the ViewModel fields
        this.id.intValue = employee?.id ?: 0
        this.fname.value = employee?.fname ?: ""
        this.lname.value = employee?.lname ?: ""
        this.nname.value = employee?.nname ?: ""
        this.email.value = employee?.email ?: ""
        this.pnumber.value = employee?.pnumber ?: ""
        this.isActive.value = employee?.isActive ?: false
        this.opening.value = employee?.opening ?: false
        this.closing.value = employee?.closing ?: false
    }

    fun updateEmployeeInfo() {
        // Create a new Employee instance from the ViewModel fields
        val currentEmployee = Employee(
            id = id.intValue,
            fname = fname.value,
            lname = lname.value,
            nname = nname.value,
            email = email.value,
            pnumber = pnumber.value,
            isActive = isActive.value,
            opening = opening.value,
            closing = closing.value
        )

        // Check if the original employee is different from the current state of the ViewModel fields
        if (originalEmployee.value != currentEmployee) {
            // If they are different, update the employee info in the ViewModel and the database
            updateEmployee(currentEmployee)
            originalEmployee.value = currentEmployee.copy()
        }
    }

    fun validateFields(): Boolean {
        // Check if the fields have been initialized
        if (id.intValue == 0 && fname.value.isEmpty() && lname.value.isEmpty() && nname.value.isEmpty() && email.value.isEmpty() && pnumber.value.isEmpty()) {
            Log.d("validateFields", "Fields not initialized. id: ${id.intValue}, fname: ${fname.value}, lname: ${lname.value}, nname: ${nname.value}, email: ${email.value}, pnumber: ${pnumber.value}")
            return false
        }

        // Then proceed with the rest of the validation
        val isValid = id.intValue != 0 &&
            fname.value.isNotEmpty() &&
            lname.value.isNotEmpty() &&
            nname.value.isNotEmpty() &&
            email.value.isNotEmpty() &&
            pnumber.value.isNotEmpty()

        if (!isValid) {
            Log.d("validateFields", "Validation failed. id: ${id.intValue}, fname: ${fname.value}, lname: ${lname.value}, nname: ${nname.value}, email: ${email.value}, pnumber: ${pnumber.value}")
        } else {
            Log.d("validateFields", "Validation passed. id: ${id.intValue}, fname: ${fname.value}, lname: ${lname.value}, nname: ${nname.value}, email: ${email.value}, pnumber: ${pnumber.value}")
        }

        return isValid
    }

    fun isValidEmail(email: String?): Boolean {
        if (email.isNullOrEmpty()) {
            Log.d("isValidEmail1", "Email is null or empty")
            return false
        }

        // made international friendly
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex()
        val isEmailValid = email.matches(emailRegex)

//        Log.d("isValidEmail2", "isEmailValid: $isEmailValid for email: $email") // testing
        return isEmailValid
    }

    fun isValidPhoneNumber(pNumber: String?): Boolean {
        if (pNumber.isNullOrEmpty()) {
            Log.d("isValidPhoneNumber1", "Phone number is null or empty")
            return false
        }

        val phoneRegex = "^[+]?[0-9]{9,15}$".toRegex()
        val cleanedNumber = pNumber.replace("[^\\d.]".toRegex(), "")
        Log.d("isValidPhoneNumber1", "Cleaned phone number: $cleanedNumber")
        val isPhoneNumberValid = cleanedNumber.matches(phoneRegex)

//        Log.d("isValidPhoneNumber2", "isPhoneNumberValid: $isPhoneNumberValid for number: $cleanedNumber") // testing
        return isPhoneNumberValid
    }

    // clear fields on add employee screen
    fun clearEmployeeFields() {
        id.intValue = 0 // Int
        idString.value = ""
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