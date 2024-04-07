/**
 *   Schedule Employee page
 *   v2.03
 *
 */

package com.example.cmpt395solaris.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.navigation.NavController
import com.example.cmpt395solaris.database.SharedViewModel
import com.example.cmpt395solaris.database.employees.Employee
import com.example.cmpt395solaris.database.employees.EmployeeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val selectedEmployeesState: MutableState<MutableMap<String, List<Employee?>>> = mutableStateOf(mutableMapOf())



@Composable
fun ScheduleWeekEnd(date: String?, viewModel: EmployeeViewModel, navController: NavController, sharedViewModel: SharedViewModel) {
    // Format the provided date string
    val dateString = formatDate3(parseDate2(date))

    // Fetch employees from the ViewModel and sort them
    val employees = viewModel.getAllEmployees().sortedWith(compareBy({ !it.isActive }, { it.fname }))

    // Options for the dropdown menus for each shift
    val options1 = employees.map { "${it.fname} ${it.lname}" }
    val options2 = employees.map { "${it.fname} ${it.lname}" }
    val options3 = employees.map { "${it.fname} ${it.lname}" }

    // State to track the toggle state for additional dropdown menus
    var toggleState by remember { mutableStateOf(false) }
    val isDropdownExpanded = remember { mutableStateOf(false) }

    // Column for displaying the selected date
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 0.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = dateString,
                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                // No need to specify font here to use the default font
            )
            Spacer(modifier = Modifier.height(40.dp))

            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.align(Alignment.BottomCenter))
        }
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
            Spacer(modifier = Modifier.height(10.dp))

            // Column for dropdown menus
            Column(
                modifier = Modifier.padding(horizontal = 15.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Dropdown 1
                val dropdownMenu1State = remember { mutableStateOf(false) }

                DropdownMenu2(
                    selectedEmployee = selectedEmployeesState.value[dateString]?.getOrNull(0),
                    isExpanded = dropdownMenu1State,
                    options = options1,
                    employees = employees,
                    onEmployeeSelected = { employee ->
                        // Update selected employees for the current day
                        val updatedEmployees = selectedEmployeesState.value.toMutableMap()
                        updatedEmployees[dateString] = mutableListOf(employee)
                        selectedEmployeesState.value = updatedEmployees
                    },
                    onDropdownClicked = {
                        dropdownMenu1State.value = true
                    },
                    onOptionSelected = {
                        dropdownMenu1State.value = false
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

// Dropdown 2
                val dropdownMenu2State = remember { mutableStateOf(false) }

                DropdownMenu2(
                    selectedEmployee = selectedEmployeesState.value[dateString]?.getOrNull(1),
                    isExpanded = dropdownMenu2State,
                    options = options2,
                    employees = employees,
                    onEmployeeSelected = { employee ->
                        // Update selected employees for the current day
                        val updatedEmployees = selectedEmployeesState.value.toMutableMap()
                        updatedEmployees[dateString] = mutableListOf(
                            selectedEmployeesState.value[dateString]?.getOrNull(0),
                            employee
                        )
                        selectedEmployeesState.value = updatedEmployees
                    },
                    onDropdownClicked = {
                        dropdownMenu2State.value = true
                    },
                    onOptionSelected = {
                        dropdownMenu2State.value = false
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

// Dropdown 3
                if (toggleState) {
                    val dropdownMenu3State = remember { mutableStateOf(false) }

                    DropdownMenu2(
                        selectedEmployee = selectedEmployeesState.value[dateString]?.getOrNull(2),
                        isExpanded = dropdownMenu3State,
                        options = options3,
                        employees = employees,
                        onEmployeeSelected = { employee ->
                            // Update selected employees for the current day
                            val updatedEmployees = selectedEmployeesState.value.toMutableMap()
                            updatedEmployees[dateString] = mutableListOf(
                                selectedEmployeesState.value[dateString]?.getOrNull(0),
                                selectedEmployeesState.value[dateString]?.getOrNull(1),
                                employee
                            )
                            selectedEmployeesState.value = updatedEmployees
                        },
                        onDropdownClicked = {
                            dropdownMenu3State.value = true
                        },
                        onOptionSelected = {
                            dropdownMenu3State.value = false
                        }
                    )
                }

            }
            // Adding space at the bottom
            Spacer(modifier = Modifier.height(10.dp))
        }

        // Item for toggling additional dropdown menus
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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

            // Button for confirming the selections
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        // Update the selected employees before confirming
                        navController.navigate("schedule1")
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
    isExpanded: MutableState<Boolean>,
    options: List<String>,
    employees: List<Employee>,
    onEmployeeSelected: (Employee?) -> Unit,
    onDropdownClicked: () -> Unit,
    onOptionSelected: () -> Unit
) {
    val borderColor = Color.LightGray // Light gray border color
    val backgroundColor = Color.White
    val textColor = Color.Black

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    // Toggle expansion state and invoke the click callback
                    isExpanded.value = !isExpanded.value
                    onDropdownClicked()
                }
                .fillMaxWidth()
                .height(50.dp)
                .background(backgroundColor)
                .border(BorderStroke(1.dp, borderColor), shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = selectedEmployee?.let { "${it.fname} ${it.lname}" } ?: "Select Employee",
                modifier = Modifier.weight(1f).padding(start = 16.dp),
                color = if (selectedEmployee == null) Color.Gray else Color.Black,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                )
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Icon",
                tint = Color.Black,
                modifier = Modifier.padding(16.dp)
            )
        }

        if (isExpanded.value) { // Check if the dropdown is expanded
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEach { option ->
                    Box(
                        modifier = Modifier
                            .background(backgroundColor)
                            .border(
                                BorderStroke(1.dp, borderColor),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable {
                                val selectedEmployee = employees.find { employee ->
                                    "${employee.fname} ${employee.lname}" == option
                                }
                                onEmployeeSelected(selectedEmployee)
                                onDropdownClicked()
                                onOptionSelected() // Call the callback when an option is selected
                            }
                    ) {
                        Text(
                            text = option,
                            modifier = Modifier.padding(8.dp),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                            )
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
    if (date == null) return "" // Return empty string if date is null

    // Format day of the week (e.g., Monday)
    val dayOfWeekFormat = SimpleDateFormat("EEEE, ", Locale.getDefault())
    val dayOfWeek = dayOfWeekFormat.format(date)

    // Format month (e.g., March)
    val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
    val month = monthFormat.format(date)

    // Format day of the month with ordinal indicator (e.g., 13th)
    val dayOfMonth = SimpleDateFormat("dd", Locale.getDefault()).format(date)
    val dayOfMonthOrdinal = getDayOfMonthWithOrdinal(dayOfMonth.toInt())

    // Format year (e.g., 2024)
    val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
    val year = yearFormat.format(date)
    return "$dayOfWeek $month $dayOfMonthOrdinal $year"
}

// Function to get ordinal indicator for day of month
private fun getDayOfMonthWithOrdinal(day: Int): String {
    return when (day) {
        in 11..13 -> "${day}th"
        else -> when (day % 10) {
            1 -> "${day}st"
            2 -> "${day}nd"
            3 -> "${day}rd"
            else -> "${day}th"
        }
    }
}

@Composable
fun DropdownMenuState(initiallyExpanded: Boolean = false): MutableState<Boolean> {
    return remember { mutableStateOf(initiallyExpanded) }
}


