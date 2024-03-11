/**
 * Add employee Screen v1.00
 *
 *  - just basic for now needs UI and organization
 */

package com.example.cmpt395aurora.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.cmpt395aurora.database.employees.EmployeeViewModel

@Composable
fun AddEmployeeScreen(viewModel: EmployeeViewModel) {
    var fname: String by remember { mutableStateOf("") }
    var lname: String by remember { mutableStateOf("") }
    var nname: String by remember { mutableStateOf("") }
    var email: String by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf("") }
    var opening by remember { mutableStateOf("") }
    var closing by remember { mutableStateOf("") }
    var position: String by remember { mutableStateOf("") }

    Column {
        TextField(
            value = fname,
            onValueChange = { fname = it },
            label = { Text("First Name") }
        )

        TextField(
            value = lname,
            onValueChange = { lname = it },
            label = { Text("Last Name") }
        )

        TextField(
            value = nname,
            onValueChange = { nname = it },
            label = { Text("Nick Name") }
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        TextField(
            value = position,
            onValueChange = { position = it },
            label = { Text("Position") }
        )

        TextField(
            value = isActive,
            onValueChange = { isActive = it },
            label = { Text("Is Active?") }
        )

        TextField(
            value = opening,
            onValueChange = { opening = it },
            label = { Text("Trained for Opening?") }
        )

        TextField(
            value = closing,
            onValueChange = { closing = it },
            label = { Text("Trained for Closing?") }
        )

        Button(onClick = { viewModel.addEmployee(fname, lname, nname, email, position, isActive, opening, closing) }) {
            Text("Add Employee")
        }


    }
}