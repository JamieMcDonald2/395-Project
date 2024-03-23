
/**
 * Employee Info Screen
 *
 *
 */

package com.example.cmpt395aurora.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.cmpt395aurora.database.employees.Employee
import com.example.cmpt395aurora.database.employees.EmployeeViewModel


// This was actually really hard to implement! please don't modify unless it works!
// needs more clean up not as nice as our other designed pages yet
@Composable
fun EmployeeInfoScreen(navController: NavController, viewModel: EmployeeViewModel, employeeID: String) {

    val employee: Employee? = viewModel.getEmployeeByID(employeeID.toInt())

    if (employee != null) {
        DataFields(employee)
    }

}


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
fun DataFields(employee: Employee) {
    val info = displayUserInfo(employee)
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = info,
            modifier = Modifier.align(Alignment.TopStart)
        )
    }

}

