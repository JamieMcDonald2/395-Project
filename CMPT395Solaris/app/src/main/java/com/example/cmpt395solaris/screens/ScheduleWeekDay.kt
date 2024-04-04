
/**
 *   Schedule Employee page
 *   v1.00
 *
 */

package com.example.cmpt395solaris.screens

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
import com.example.cmpt395solaris.database.employees.Employee
import com.example.cmpt395solaris.database.employees.EmployeeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ScheduleWeekDay(date: String?, viewModel: EmployeeViewModel) {
    // Convert date string to a formatted date
    val dateString = formatDate2(parseDate(date))

    // State variables for selected employees
    var selectedEmployee1 by remember { mutableStateOf<Employee?>(null) }
    var selectedEmployee2 by remember { mutableStateOf<Employee?>(null) }
    var selectedEmployee3 by remember { mutableStateOf<Employee?>(null) }
    var selectedEmployee4 by remember { mutableStateOf<Employee?>(null) }
    var selectedEmployee5 by remember { mutableStateOf<Employee?>(null) }
    var selectedEmployee6 by remember { mutableStateOf<Employee?>(null) }

    // State variables to manage expansion of dropdown menus
    var isExpanded1 by remember { mutableStateOf(false) }
    var isExpanded2 by remember { mutableStateOf(false) }
    var isExpanded3 by remember { mutableStateOf(false) }
    var isExpanded4 by remember { mutableStateOf(false) }
    var isExpanded5 by remember { mutableStateOf(false) }
    var isExpanded6 by remember { mutableStateOf(false) }

    // Fetch employees from the ViewModel and sort them by activity status and first name
    val employees = viewModel.getAllEmployees().sortedWith(compareBy({ !it.isActive }, { it.fname }))

    // Options for the dropdown menus
    val options1 = employees.map { "${it.fname} ${it.lname}" }
    val options2 = employees.map { "${it.fname} ${it.lname}" }
    val options3 = employees.map { "${it.fname} ${it.lname}" }
    val options4 = employees.map { "${it.fname} ${it.lname}" }
    val options5 = employees.map { "${it.fname} ${it.lname}" }
    val options6 = employees.map { "${it.fname} ${it.lname}" }

    // State to track the toggle state
    var toggleState by remember { mutableStateOf(false) }

    // State to manage visibility of additional DropdownMenus
    var showAdditionalDropdowns by remember { mutableStateOf(false) }

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
        item {
            // Display "Morning Shift" section
            Text(
                text = "Morning Shift",
                modifier = Modifier.padding(start = 15.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )

            // Adding padding between "Morning Shift" text and dropdowns
            Spacer(modifier = Modifier.height(20.dp))

            // Column for dropdowns related to "Morning Shift"
            Column(
                modifier = Modifier.padding(horizontal = 15.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Dropdown 1 for "Morning Shift"
                DropdownMenu(
                    selectedEmployee1,
                    isExpanded1,
                    options1,
                    employees,
                    { selectedEmployee1 = it },
                    { isExpanded1 = !isExpanded1 }
                )

                // Dropdown 2 for "Morning Shift"
                DropdownMenu(
                    selectedEmployee2,
                    isExpanded2,
                    options2,
                    employees,
                    { selectedEmployee2 = it },
                    { isExpanded2 = !isExpanded2 }
                )

                // Additional dropdowns for "Morning Shift" if toggleState is true
                if (toggleState) {
                    // Dropdown 3 for "Morning Shift"
                    DropdownMenu(
                        selectedEmployee3,
                        isExpanded3,
                        options3,
                        employees,
                        { selectedEmployee3 = it },
                        { isExpanded3 = !isExpanded3 }
                    )
                }
            }

            // Adding space between "Morning Shift" and "Afternoon Shift"
            Spacer(modifier = Modifier.height(20.dp))

            // Display "Afternoon Shift" section
            Text(
                text = "Afternoon Shift",
                modifier = Modifier.padding(start = 15.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )

            // Adding space between "Morning Shift" and "Afternoon Shift"
            Spacer(modifier = Modifier.height(20.dp))

            // Column for dropdowns related to "Afternoon Shift"
            Column(
                modifier = Modifier.padding(horizontal = 15.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Dropdown 4 for "Afternoon Shift"
                DropdownMenu(
                    selectedEmployee4,
                    isExpanded4,
                    options4,
                    employees,
                    { selectedEmployee4 = it },
                    { isExpanded4 = !isExpanded4 }
                )

                // Dropdown 5 for "Afternoon Shift"
                DropdownMenu(
                    selectedEmployee5,
                    isExpanded5,
                    options5,
                    employees,
                    { selectedEmployee5 = it },
                    { isExpanded5 = !isExpanded5 }
                )

                // Additional dropdowns for "Afternoon Shift" if toggleState is true
                if (toggleState) {
                    // Dropdown 6 for "Afternoon Shift"
                    DropdownMenu2(
                        selectedEmployee6,
                        isExpanded6,
                        options6,
                        employees,
                        { selectedEmployee6 = it },
                        { isExpanded6 = !isExpanded6 }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Confirmation section
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Switch for toggling additional dropdowns
                Text(
                    text = "Busy Day?",
                    modifier = Modifier.align(Alignment.CenterVertically).padding(start = 8.dp)
                )
                Switch(
                    checked = toggleState,
                    onCheckedChange = {
                        toggleState = it
                    },
                    modifier = Modifier.padding(horizontal = 240.dp)
                )
            }

            // Adding space between switch and confirm button
            Spacer(modifier = Modifier.height(20.dp))

            // Button for confirming selections
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
 * Composable function to display a dropdown menu for selecting an employee.
 *
 * @param selectedEmployee The currently selected employee in the dropdown.
 * @param isExpanded Whether the dropdown is expanded or not.
 * @param options The list of options for the dropdown. (This will later be populated with
 * employees from the database)
 * @param employeeOptions The list of all employees to populate the dropdown options.
 * @param onEmployeeSelected Callback function to handle selection of an employee.
 * @param onDropdownClicked Callback function to handle dropdown click.
 */
@Composable
fun DropdownMenu(
    selectedEmployee: Employee?,
    isExpanded: Boolean,
    options: List<String>,
    employeeOptions: List<Employee>,
    onEmployeeSelected: (Employee?) -> Unit,
    onDropdownClicked: () -> Unit
) {
    // State variable to manage border size for the icon (optional)
    var iconBorder by remember { mutableStateOf(0.dp) } // ?

    Column {
        // Row representing the dropdown menu
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
            // Text displaying the selected employee or "Select Employee" if none selected
            Text(
                text = selectedEmployee?.let { "${it.fname} ${it.lname}" } ?: "Select Employee",
                modifier = Modifier.weight(1f).padding(start = 16.dp),
                color = if (selectedEmployee == null) Color.LightGray else Color.Black
            )
            // Dropdown icon
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Icon",
                modifier = Modifier.padding(16.dp)
                //.border(iconBorder, Color.Black) This is optional, I don't know if I like it
            )
        }

        // If dropdown is expanded, display the list of options
        if (isExpanded) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEach { option ->
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                            .border(1.dp, Color.Gray)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable {
                                // Find the corresponding Employee based on the selected name
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


fun parseDate(dateString: String?): Date? {
    // Check if the input string is null
    if (dateString == null) return null
    // Create a date format with the pattern "yyyy-MM-dd" and the default locale, can change this
    // later if date needs to be in different format
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    // Parse the input date string and return the Date object
    return format.parse(dateString)
}

fun formatDate2(date: Date?): String {
    // Check if the input date is null
    if (date == null) return "" // Return empty string if date is null
    // Create a date format with the pattern "yyyy-MM-dd" and the default locale, can change this
    // later if date needs to be in different format
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    // Format the input date and return the formatted date string
    return format.format(date)
}



