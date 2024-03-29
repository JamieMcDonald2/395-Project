
/**
 *   Schedule Employee page
 *   v1.00
 *
 */

package com.example.cmpt395aurora.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cmpt395aurora.database.employees.Employee
import com.example.cmpt395aurora.database.employees.EmployeeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ScheduleWeekEnd(date: String?, viewModel: EmployeeViewModel) {
    // Format the provided date string
    val dateString = formatDate3(parseDate2(date))

    // State variables for selected employees for each shift
    var selectedEmployee1 by remember { mutableStateOf<Employee?>(null) }
    var selectedEmployee2 by remember { mutableStateOf<Employee?>(null) }
    var selectedEmployee3 by remember { mutableStateOf<Employee?>(null) }

    // State variables to manage expansion of dropdown menus for each shift
    var isExpanded1 by remember { mutableStateOf(false) }
    var isExpanded2 by remember { mutableStateOf(false) }
    var isExpanded3 by remember { mutableStateOf(false) }

    // Fetch employees from the ViewModel and sort them
    val employees = viewModel.getAllEmployees().sortedWith(compareBy({ !it.isActive }, { it.fname }))

    // Options for the dropdown menus for each shift
    val options1 = employees.map { "${it.fname} ${it.lname}" }
    val options2 = employees.map { "${it.fname} ${it.lname}" }
    val options3 = employees.map { "${it.fname} ${it.lname}" }

    // State to track the toggle state for additional dropdown menus
    var toggleState by remember { mutableStateOf(false) }

    // Column for displaying the selected date
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dateString,
            style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
        )
    }

    // Adding space below the date
    Spacer(modifier = Modifier.height(20.dp))

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp), // Added padding to move LazyColumn down for now
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Item representing the "Full Day Shift"
        item {
            Text(
                text = "Full Day Shift",
                modifier = Modifier.padding(start = 15.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )

            // Adding space between shift text and dropdown menus
            Spacer(modifier = Modifier.height(20.dp))

            // Column for dropdown menus
            Column(
                modifier = Modifier.padding(horizontal = 15.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Dropdown 1
                DropdownMenu2(
                    selectedEmployee1,
                    isExpanded1,
                    options1,
                    employees,
                    { selectedEmployee1 = it },
                    { isExpanded1 = !isExpanded1 }
                )

                // Dropdown 2
                DropdownMenu2(
                    selectedEmployee2,
                    isExpanded2,
                    options2,
                    employees,
                    { selectedEmployee2 = it },
                    { isExpanded2 = !isExpanded2 }
                )

                // Dropdown 3 if toggleState is true
                if (toggleState) {
                    DropdownMenu2(
                        selectedEmployee3,
                        isExpanded3,
                        options3,
                        employees,
                        { selectedEmployee3 = it },
                        { isExpanded3 = !isExpanded3 }
                    )
                }
            }
            // Adding space at the bottom
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Item for toggling additional dropdown menus
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = toggleState,
                    onCheckedChange = {
                        toggleState = it
                    },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = "Busy Day?",
                    modifier = Modifier.align(Alignment.CenterVertically).padding(start = 8.dp)
                )
            }

            // Adding space between switch and confirm button
            Spacer(modifier = Modifier.height(20.dp))

            // Button for confirming the selections
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        // Haven't implemented logic for button yet
                    },
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}

/**
 * Composable function to display a dropdown menu.
 *
 * @param selectedEmployee The currently selected employee in the dropdown.
 * @param isExpanded Whether the dropdown is expanded or not.
 * @param options The list of options for the dropdown. (This will later be populated with
 * employees from the database)
 * @param onEmployeeSelected Callback function to handle selection of an employee.
 * @param onDropdownClicked Callback function to handle dropdown click.
 */
@Composable
fun DropdownMenu2(
    selectedEmployee: Employee?,
    isExpanded: Boolean,
    options: List<String>,
    employeeOptions: List<Employee>, // List of Employee objects
    onEmployeeSelected: (Employee?) -> Unit,
    onDropdownClicked: () -> Unit
) {
    // State variable to manage border size for the icon (optional)
    var iconBorder by remember { mutableStateOf(0.dp) }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onDropdownClicked() }
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.White)
                .border(1.dp, Color.Gray)
        ) {
            Text(
                text = selectedEmployee?.let { "${it.fname} ${it.lname}" } ?: "Select Employee",
                modifier = Modifier.weight(1f).padding(start = 16.dp),
                color = if (selectedEmployee == null) Color.LightGray else Color.Black
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Icon",
                modifier = Modifier.padding(16.dp)
                //.border(iconBorder, Color.Black) This is optional, I don't know if I like it
            )
        }

        // If dropdown is clicked, display the list of options
        if (isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                options.forEach { option ->
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                            .border(1.dp, Color.Gray)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable {
                                // Find the corresponding Employee object based on the selected name
                                val selectedEmployee = employeeOptions.find { employee ->
                                    "${employee.fname} ${employee.lname}" == option
                                }
                                onEmployeeSelected(selectedEmployee)
                                onDropdownClicked()
                            }
                    ) {
                        Text(
                            text = option,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}




fun parseDate2(dateString: String?): Date? {
    // Check if the input string is null
    if (dateString == null) return null
    // Create a date format with the pattern "yyyy-MM-dd" and the default locale, can change this
    // later if date needs to be in different format
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    // Parse the input date string and return the Date object
    return format.parse(dateString)
}

fun formatDate3(date: Date?): String {
    // Check if the input date is null
    if (date == null) return "" // Return empty string if date is null
    // Create a date format with the pattern "yyyy-MM-dd" and the default locale, can change this
    // later if date needs to be in different format
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    // Format the input date and return the formatted date string
    return format.format(date)
}




