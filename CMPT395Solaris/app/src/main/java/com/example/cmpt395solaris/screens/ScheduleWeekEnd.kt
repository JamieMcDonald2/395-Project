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
import com.example.cmpt395solaris.database.ScheduleViewModel
import com.example.cmpt395solaris.database.dayschedule.DaySchedule
import com.example.cmpt395solaris.database.employees.Employee
import com.example.cmpt395solaris.database.employees.EmployeeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ScheduleWeekEnd(
    date: String,
    viewModel: EmployeeViewModel,
    navController: NavController,
    scheduleViewModel: ScheduleViewModel
) {
    // Convert date string to a formatted date
    val dateString = formatDate2(parseDate(date))
    val parts = dateString.split(", ")
    val day = parts.first().lowercase()

    var toggleState by remember { mutableStateOf(false) }

    scheduleViewModel.fullDayEmployees =
        remember { mutableStateOf(viewModel.getAvailableEmployees(day)) }
    scheduleViewModel.morningTrainedEmployees =
        remember { mutableStateOf(viewModel.getOpenTrainedEmployees(day)) }
    scheduleViewModel.eveningTrainedEmployees =
        remember { mutableStateOf(viewModel.getCloseTrainedEmployees(day)) }
    scheduleViewModel.bothTrainedEmployees =
        remember {  mutableStateOf(viewModel.getBothTrainedEmployees(day)) }

    var schedule = DaySchedule(
        date,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1)

    if (!scheduleViewModel.doesDsDateExist(date)){
        scheduleViewModel.addDaySchedule(schedule)
    }
    else{
        schedule = scheduleViewModel.getDaySchedule(date)!!
    }

    // State variables for selected employees
//    scheduleViewModel.selectedEmployee1 = remember { mutableStateOf<Employee?>(null) }
//    scheduleViewModel.selectedEmployee2 = remember { mutableStateOf<Employee?>(null) }
//    scheduleViewModel.selectedEmployee3 = remember { mutableStateOf<Employee?>(null) }

    // Fetching selected employees from the view model
//    val selectedEmployeesMap by scheduleViewModel.selectedEmployeesFlow.collectAsState()
//
//    // Set selected employees if already stored in the view model
//    val selectedEmployees = selectedEmployeesMap[dateString] ?: emptyList()
//    if (selectedEmployees.size >= 3) {
//        selectedEmployee1 = selectedEmployees[0]
//        selectedEmployee2 = selectedEmployees[1]
//        selectedEmployee3 = selectedEmployees[2]
//    }


    if(schedule.employeeAM1 > 0){
        scheduleViewModel.selectedEmployee1 =
            remember { mutableStateOf(viewModel.getEmployeeByID(schedule.employeeAM1)) }
    }
    if(schedule.employeeAM2 > 0){
        scheduleViewModel.selectedEmployee2 =
            remember { mutableStateOf(viewModel.getEmployeeByID(schedule.employeeAM2)) }
    }
    if(schedule.employeeAM3 > 0){
        scheduleViewModel.selectedEmployee3 =
            remember { mutableStateOf(viewModel.getEmployeeByID(schedule.employeeAM3)) }

        toggleState = true
    }

    // State variables to manage expansion of dropdown menus
    var isExpanded1 by remember { mutableStateOf(false) }
    var isExpanded2 by remember { mutableStateOf(false) }
    var isExpanded3 by remember { mutableStateOf(false) }

    // Options for the dropdown menus

    val employeeList = scheduleViewModel.fullDayEmployees


    scheduleViewModel.options1 = remember {
        mutableStateOf(scheduleViewModel.fullDayEmployees.value.map { "${it.fname} ${it.lname}" })
    }
    scheduleViewModel.options2 = remember {
        mutableStateOf(scheduleViewModel.morningTrainedEmployees.value.map { "${it.fname} ${it.lname}" })
    }
    scheduleViewModel.options3 = remember {
        mutableStateOf(scheduleViewModel.eveningTrainedEmployees.value.map { "${it.fname} ${it.lname}" })
    }
    scheduleViewModel.options4 = remember {
        mutableStateOf(scheduleViewModel.bothTrainedEmployees.value.map { "${it.fname} ${it.lname}" })
    }




    // Function to handle confirm button click
    fun onConfirmClicked() {
        // Update selected employees in the view model
//        val selectedEmployees = listOf(selectedEmployee1, selectedEmployee2, selectedEmployee3)
//        scheduleViewModel.setSelectedEmployeesForDate(dateString, selectedEmployees)

        schedule.employeeAM1 = scheduleViewModel.selectedEmployee1.value?.id ?: -1
        schedule.employeeAM2 = scheduleViewModel.selectedEmployee2.value?.id ?: -1
        schedule.employeeAM3 = scheduleViewModel.selectedEmployee3.value?.id ?: -1

        scheduleViewModel.updateDaySchedule(schedule)
    }

    // State to track the toggle state



    // State to manage visibility of additional DropdownMenus
    var showAdditionalDropdowns by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = dateString,
                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(40.dp))

            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.align(Alignment.BottomCenter))
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Text(
                text = "Full Day Shift",
                modifier = Modifier.padding(start = 15.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier.padding(horizontal = 15.dp),
                horizontalAlignment = Alignment.Start
            ) {
                DropdownMenu3(
                    scheduleViewModel.selectedEmployee1.value,
                    scheduleViewModel.selectedEmployee2.value,
                    scheduleViewModel.selectedEmployee3.value,
                    isExpanded1,
                    scheduleViewModel.fullDayEmployees.value,
                    scheduleViewModel.morningTrainedEmployees.value,
                    scheduleViewModel.eveningTrainedEmployees.value,
                    scheduleViewModel.bothTrainedEmployees.value,
                    {
                        if (it != null) {
                            scheduleViewModel.selectedEmployee1.value = it
                        }
                    }
                ) { isExpanded1 = !isExpanded1 }
                Spacer(modifier = Modifier.height(10.dp))
                DropdownMenu3(
                    scheduleViewModel.selectedEmployee2.value,
                    scheduleViewModel.selectedEmployee1.value,
                    scheduleViewModel.selectedEmployee3.value,
                    isExpanded2,
                    scheduleViewModel.fullDayEmployees.value,
                    scheduleViewModel.morningTrainedEmployees.value,
                    scheduleViewModel.eveningTrainedEmployees.value,
                    scheduleViewModel.bothTrainedEmployees.value,
                    {
                        if (it != null) {
                            scheduleViewModel.selectedEmployee2.value = it
                        }
                    }
                ) { isExpanded2 = !isExpanded2 }
                Spacer(modifier = Modifier.height(10.dp))
                if (toggleState) {
                    DropdownMenu3(
                        scheduleViewModel.selectedEmployee3.value,
                        scheduleViewModel.selectedEmployee1.value,
                        scheduleViewModel.selectedEmployee2.value,
                        isExpanded3,
                        scheduleViewModel.fullDayEmployees.value,
                        scheduleViewModel.morningTrainedEmployees.value,
                        scheduleViewModel.eveningTrainedEmployees.value,
                        scheduleViewModel.bothTrainedEmployees.value,
                        {
                            if (it != null) {
                                scheduleViewModel.selectedEmployee3.value = it
                            }
                        }
                    ) { isExpanded3 = !isExpanded3 }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Busy Day?",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                )
                Switch(
                    checked = toggleState,
                    onCheckedChange = { toggleState = it },
                    modifier = Modifier.padding(horizontal = 240.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        onConfirmClicked()
                        navController.navigate("schedule1")}

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
    otherSelectedEmp1: Employee?,
    otherSelectedEmp2: Employee?,
    isExpanded: Boolean,
    employeeOptions: List<Employee>,
    openOptions: List<Employee>,
    closeOptions: List<Employee>,
    bothOptions: List<Employee>,
    onEmployeeSelected: (Employee?) -> Unit,
    onDropdownClicked: () -> Unit
) {
        val borderColor = Color.LightGray // Light gray border color
        val backgroundColor = Color.White
        val textColor = Color.Black

        val updatedEmployee = remember {
            selectedEmployee?.let { mutableStateOf(it.copy()) }
        }

        var options: List<String>? = null
        var opening = false
        var closing = false

        if(otherSelectedEmp1 != null){
            if(otherSelectedEmp1.opening){
                opening = true
            }
            if(otherSelectedEmp1.closing){
                closing = true
            }
        }
        if(otherSelectedEmp2 != null){
            if(otherSelectedEmp2.opening){
                opening = true
            }
            if(otherSelectedEmp2.closing){
                closing = true
            }
        }
        options = if(opening && closing){
            employeeOptions.map { "${it.fname} ${it.lname}" }
        } else if(opening){
            closeOptions.map { "${it.fname} ${it.lname}" }
        } else if(closing){
            openOptions.map { "${it.fname} ${it.lname}" }
        } else{
            bothOptions.map { "${it.fname} ${it.lname}" }
        }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onDropdownClicked() }
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(backgroundColor)
                .border(BorderStroke(1.dp, borderColor), shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = selectedEmployee?.let { "${it.fname} ${it.lname}" } ?: "Select Employee",
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
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

        if (isExpanded) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                val filteredOptions = options.toList()
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
                                val newEmployee = employeeOptions.find { employee ->
                                    "${employee.fname} ${employee.lname}" == option
                                }
                                onEmployeeSelected(newEmployee)
                                onDropdownClicked()
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
fun DropdownMenu3(
    selectedEmployee: Employee?,
    otherSelectedEmp1: Employee?,
    otherSelectedEmp2: Employee?,
    isExpanded: Boolean,
    employeeOptions: List<Employee>,
    openOptions: List<Employee>,
    closeOptions: List<Employee>,
    bothOptions: List<Employee>,
    onEmployeeSelected: (Employee?) -> Unit,
    onDropdownClicked: () -> Unit
) {
    val borderColor = Color.LightGray // Light gray border color
    val backgroundColor = Color.White
    val textColor = Color.Black

//    // Create a copy of the options list to prevent ConcurrentModificationException
//    var options = remember { calculateOptions(
//        selectedEmployee,
//        otherSelectedEmp1,
//        otherSelectedEmp2,
//        employeeOptions,
//        openOptions,
//        closeOptions,
//        bothOptions
//    ) }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onDropdownClicked() }
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(backgroundColor)
                .border(BorderStroke(1.dp, borderColor), shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = selectedEmployee?.let { "${it.fname} ${it.lname}" } ?: "Select Employee",
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
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

        if (isExpanded) {
            val options = remember { calculateOptions2(
                selectedEmployee,
                otherSelectedEmp1,
                otherSelectedEmp2,
                employeeOptions,
                openOptions,
                closeOptions,
                bothOptions
            ) }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Iterate over the copy of options list
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
                                val newEmployee = employeeOptions.find { employee ->
                                    "${employee.fname} ${employee.lname}" == option
                                }
                                onEmployeeSelected(newEmployee)
                                onDropdownClicked()
                            }
                    ) {
                        Text(
                            text = option.toString(),
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


fun calculateOptions(
    selectedEmployee: Employee?,
    otherSelectedEmp1: Employee?,
    otherSelectedEmp2: Employee?,
    employeeOptions: List<Employee>,
    openOptions: List<Employee>,
    closeOptions: List<Employee>,
    bothOptions: List<Employee>
): List<Any> {
    val opening = otherSelectedEmp1?.opening == true || otherSelectedEmp2?.opening == true
    val closing = otherSelectedEmp1?.closing == true || otherSelectedEmp2?.closing == true

    val options = if (opening && closing) {
        employeeOptions.map { "${it.fname} ${it.lname}" }
    } else if (opening) {
        closeOptions.map { "${it.fname} ${it.lname}" }
    } else if (closing) {
        openOptions.map { "${it.fname} ${it.lname}" }
    } else {
        bothOptions.map { "${it.fname} ${it.lname}" }
    }

    // Filter out the selected employee from the options list
    return options.filter { it != "${selectedEmployee?.fname} ${selectedEmployee?.lname}" }
}

fun calculateOptions2(
    selectedEmployee: Employee?,
    otherSelectedEmp1: Employee?,
    otherSelectedEmp2: Employee?,
    employeeOptions: List<Employee>,
    openOptions: List<Employee>,
    closeOptions: List<Employee>,
    bothOptions: List<Employee>
): List<String> {
    val opening = otherSelectedEmp1?.opening == true || otherSelectedEmp2?.opening == true
    val closing = otherSelectedEmp1?.closing == true || otherSelectedEmp2?.closing == true

    // Determine the appropriate options list based on opening and closing statuses
    val options = if (opening && closing) {
        employeeOptions
    } else if (opening) {
        closeOptions
    } else if (closing) {
        openOptions
    } else {
        bothOptions
    }

    // Map options to strings
    val optionStrings = options.map { "${it.fname} ${it.lname}" }

    // Filter out selected employee, otherSelectedEmp1, and otherSelectedEmp2
    return optionStrings.filter {
        it != "${selectedEmployee?.fname} ${selectedEmployee?.lname}" &&
                it != "${otherSelectedEmp1?.fname} ${otherSelectedEmp1?.lname}" &&
                it != "${otherSelectedEmp2?.fname} ${otherSelectedEmp2?.lname}"
    }
}
