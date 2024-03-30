/**
 * Employee View Model v1.4
 *
 *      - Employee database view model
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

package com.example.cmpt395aurora.database.employees

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.cmpt395aurora.database.DatabaseHelper

class EmployeeViewModel(application: Application) : AndroidViewModel(application) {
    // Define allEmployees as MutableLiveData
    var employees = mutableStateOf(listOf<Employee>())

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

//    // testing - get rid of later
//    init {
//        Log.d("ViewModel", "ViewModel initialized")
//        Log.d("ViewModel", "Initial values: id=${id.value}, fname=${fname.value}, lname=${lname.value}, ...")
//    }

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
//        Log.d("addEmployee", "employee email: ${email}")  // testing
//        Log.d("addEmployee", "employee pnumber: ${pnumber}")  // testing
        dbHelper.addEmployee(id, fname, lname, nname, email, pnumber, isActive, opening, closing)
    }

    // obsolete
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
        Log.d("ViewModel updateEmployee", "Updating employee: $updatedEmployee") // testing
        dbHelper.updateEmployee(updatedEmployee)
        refreshEmployees() // refresh employees after update

        // Update ViewModel's state variables
        id.value = updatedEmployee.id
        fname.value = updatedEmployee.fname
        lname.value = updatedEmployee.lname
        nname.value = updatedEmployee.nname
        email.value = updatedEmployee.email
        pnumber.value = updatedEmployee.pnumber
        isActive.value = updatedEmployee.isActive
        opening.value = updatedEmployee.opening
        closing.value = updatedEmployee.closing

        // testing
//        Log.d("ViewModel updateEmployee", "Values after update: id=${id.value}, fname=${fname.value}, lname=${lname.value}, nname=${nname.value}, email=${email.value}, pnumber=${pnumber.value}")
    }

    fun refreshEmployees() {
        // sort the lists here now - new as of 2.8
        val allEmployees = dbHelper.getAllEmployees()
        val activeEmployees = allEmployees.filter { it.isActive }.sortedBy { it.fname }
        val inactiveEmployees = allEmployees.filter { !it.isActive }.sortedBy { it.fname }
        employees.value = activeEmployees + inactiveEmployees
    }

    fun validateFields(): Boolean {
        val isValid = id.value != 0 &&
            fname.value.isNotEmpty() && fname.value != "default" &&
            lname.value.isNotEmpty() && lname.value != "default" &&
            nname.value.isNotEmpty() && nname.value != "default" &&
            email.value.isNotEmpty() && email.value != "default" &&
            pnumber.value.isNotEmpty() && pnumber.value != "default"

//        Log.d("validateFields", "isValid: $isValid") // testing
        return isValid
    }

    fun hasChanges(updatedEmployee: Employee): Boolean {
        val originalEmployee = getEmployeeByID(updatedEmployee.id) ?: return false // Assuming id is the primary key
        return originalEmployee != updatedEmployee
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

        val phoneRegex = "^[+]?[0-9]{10,15}$".toRegex()
        val cleanedNumber = pNumber.replace("[^\\d.]".toRegex(), "")
        val isPhoneNumberValid = cleanedNumber.matches(phoneRegex)

//        Log.d("isValidPhoneNumber2", "isPhoneNumberValid: $isPhoneNumberValid for number: $cleanedNumber") // testing
        return isPhoneNumberValid
    }

    // clear fields on add employee screen
    fun clearEmployeeFields() {
        id.value = 0 // Int
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