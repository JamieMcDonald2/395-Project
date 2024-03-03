/**
 *   Employee Main page
 *   v1.01
 *
 *   1.01:
 *   - added info from db test
 *   - https://developer.android.com/training/data-storage/sqlite
 */

package com.example.cmpt395luloo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cmpt395luloo.database.employees.EmployeeViewModel

@Composable
fun EmployeeMain(navController: NavHostController, viewModel: EmployeeViewModel) {
    // Use the ViewModel to get the data
    val employees = viewModel.getAllEmployees()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp), // Add padding here
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // placeholder for search bar - remove this note as well!
        Text(text = "Employee Main Screen")

        Spacer(modifier = Modifier.height(32.dp)) // Add space between each employee name

        // Display our employees here will need to add UI components
        employees.forEach { employee ->
            Text(text = employee.name)
        }
    }
}
