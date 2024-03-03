/**
 * Add employee Screen v1.00
 *
 *  - just basic for now needs UI and organization
 */

package com.example.cmpt395luloo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.cmpt395luloo.database.employees.EmployeeViewModel

@Composable
fun AddEmployeeScreen(viewModel: EmployeeViewModel) {
    var name: String by remember { mutableStateOf("") }
    var position: String by remember { mutableStateOf("") }

    Column {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        TextField(
            value = position,
            onValueChange = { position = it },
            label = { Text("Position") }
        )

        Button(onClick = { viewModel.addEmployee(name, position) }) {
            Text("Add Employee")
        }
    }
}