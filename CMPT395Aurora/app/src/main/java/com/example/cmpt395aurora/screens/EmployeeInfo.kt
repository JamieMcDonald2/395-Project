/**
 * Employee Info Screen v1.2
 *
 * v1.2
 * - added UI
 *
 * v1.1
 * - cleaned up code while copying logic for settings
 *
 * v1.0
 * - Added fields, ability to display existing data
 *
 */

package com.example.cmpt395aurora.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.cmpt395aurora.ComponentFunctions.GenericTextField
import com.example.cmpt395aurora.database.employees.Employee
import com.example.cmpt395aurora.database.employees.EmployeeViewModel

@Composable
fun EmployeeInfoScreen(navController: NavController, viewModel: EmployeeViewModel, employeeID: String) {

    val employee: Employee? = viewModel.getEmployeeByID(employeeID.toInt())

    if (employee != null) {
        DataFields(employee) { updatedEmployee ->
            // This is where you handle the updated employee object.
            // For example, you might want to update the employee in your ViewModel:
            viewModel.updateEmployee(updatedEmployee)
        }
    }
}

// Probably can delete this
fun displayUserInfo(employee: Employee): String { // Changed return type to String

    val userInfo = StringBuilder()
    userInfo.append("First Name: ${employee.fname}\n")
    userInfo.append("Last Name: ${employee.lname}\n")
    userInfo.append("Nick Name: ${employee.nname}\n")
    userInfo.append("Email: ${employee.email}\n")
    userInfo.append("Phone Number: ${employee.pnumber}\n")
    userInfo.append("Is Active?: ${if (employee.isActive) "Yes" else "No"}\n")
    userInfo.append("Trained for Opening?: ${if (employee.opening) "Yes" else "No"}\n")
    userInfo.append("Trained for Closing?: ${if (employee.closing) "Yes" else "No"}")

    // Convert StringBuilder to String before returning
    return userInfo.toString()
}

// this will move back inside AddEmployeeScreen later
@Composable
fun DataFields(employee: Employee, onEmployeeChange: (Employee) -> Unit) {
    val fname = remember { mutableStateOf(employee.fname) }
    val lname = remember { mutableStateOf(employee.lname) }
//    var nname by remember { mutableStateOf(employee.nname) }
//    var email by remember { mutableStateOf(employee.email) }
//    var pnumber by remember { mutableStateOf(employee.pnumber) }
//    var isActive by remember { mutableStateOf(employee.isActive) }
//    var opening by remember { mutableStateOf(employee.opening) }
//    var closing by remember { mutableStateOf(employee.closing) }

    val fnameError = remember { mutableStateOf(false) }
    val lnameError = remember { mutableStateOf(false) }

    Column {
        GenericTextField(
            text = fname,
            isError = fnameError,
            label = "First Name",
            placeholder = "Enter first name",
            onFocusChange = { focusState ->
                // Handle focus change
            },
            onValueChange = { newValue ->
                fname.value = newValue
                onEmployeeChange(employee.copy(fname = fname.value))
            }
        )
        GenericTextField(
            text = lname,
            isError = lnameError,
            label = "Last Name",
            placeholder = "Enter last name",
            onFocusChange = { focusState ->
                // Handle focus change
            },
            onValueChange = { newValue ->
                lname.value = newValue
                onEmployeeChange(employee.copy(lname = lname.value))
            }
        )
        // Add other GenericTextField components here
    }
}

