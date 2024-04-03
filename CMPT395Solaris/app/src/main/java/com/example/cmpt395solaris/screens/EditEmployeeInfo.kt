/**
 * Edit Employee Info Screen v2.7
 *
 * v2.7
 * - disabled 'ID' field - maybe remove later altogether
 *
 * v2.6
 * - new logic for back buttons to confirm discard
 *
 * v2.5
 * - completed verification for data fields
 *
 * v2.4
 * - reversed changes to empty data field logic, still isn't working
 * - attempted to correct all verification/error checking logic, including empty data fields
 *
 * v2.3
 * - changed data field logic to store values until 'Edit Employee' is clicked as only the last
 *   field changed was updating in previous version
 * - moved empty field confirmation logic inside data fields, wasn't working with 'no changes made'
 *   dialog
 * - removed error throwing from ID field, replaced with error snack bar for better UX
 *
 * v2.2
 * - Removed the ViewModel assignment for each field since the ViewModel already holds the employee data
 * - Simplified the function by removing unnecessary ViewModel interactions and validations as they are already handled in the ViewModel
 * - Kept the validation checks and database update logic intact, relying on ViewModel to perform these operations
 *
 * - rewrote logic for confirmations and dialog - weren't working properly
 *
 * v2.1
 * - confirmation for:
 *      - incomplete fields (fixed)
 *      - unchanged information
 *   using show dialog and snack bar
 *
 * v2.0
 * - added availability button
 * - started overlay for availability
 *
 * v1.9
 * - cannot use lazy container inside column of parent, reworked to use columns and boxes
 * - new persistent button (instead of being at bottom)
 *
 * v1.8
 * - make column containers for button, button to confirm actions
 * - debugging value changes to DB
 * - scope / coroutine updates (Again)
 *
 * v1.7
 * - new logic for boolean fields
 * - new methods for preparing UI
 *  -https://www.baeldung.com/kotlin/backing-fields
 *  -https://blog.logrocket.com/guide-using-sealed-classes-kotlin/
 *  -https://typealias.com/start/kotlin-lambdas/
 *
 * v1.6
 * - new logic for new top bar functionality, old way wasn't working properly
 *
 * v1.5
 * - LazyColumn container added, list items changed to loop due to number
 *
 * v1.4
 * - pass ID to top bar
 *
 * v1.3
 * - add id field and logic
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

package com.example.cmpt395solaris.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cmpt395solaris.ComponentFunctions.CustomSnackbar
import com.example.cmpt395solaris.ComponentFunctions.GenericTextField
import com.example.cmpt395solaris.database.employees.Employee
import com.example.cmpt395solaris.database.employees.EmployeeViewModel
import com.example.cmpt395solaris.database.TopBarViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 *  Do not alter without consulting Jamie!
 */
@Composable
fun EditEmployeeInfoScreen(
    navController: NavController,
    viewModel: EmployeeViewModel,
    topBarViewModel: TopBarViewModel,
    employeeID: String
) {
    val id = employeeID.toInt()

    // Load the employee data into the ViewModel
    viewModel.loadEmployee(id)

    val employee: Employee? = viewModel.getEmployeeByID(id)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val showSnackbar = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    val veryLightGray = Color(0xFFF5F5F5)
    val focusManager = LocalFocusManager.current

    val originalEmployee = remember { mutableStateOf(viewModel.getEmployeeByID(id)) }
    val editEmployee =
        remember { mutableStateOf(originalEmployee.value?.copy()) } // Create a copy for editing

    Log.d("EditEmployeeInfoScreen", "Employee ID: $id, Employee: $employee")

    if (employee != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
        ) {
            Box(modifier = Modifier.weight(1f)) {
                DataFields(
                    viewModel = viewModel,
                    employee = employee
                ) { updatedEmployee ->
                    editEmployee.value = updatedEmployee // Update the copy for editing

                    // Create a new Employee instance from the ViewModel fields
                    val currentEmployee = Employee(
                        id = viewModel.id.intValue,
                        fname = viewModel.fname.value,
                        lname = viewModel.lname.value,
                        nname = viewModel.nname.value,
                        email = viewModel.email.value,
                        pnumber = viewModel.pnumber.value,
                        isActive = viewModel.isActive.value,
                        opening = viewModel.opening.value,
                        closing = viewModel.closing.value
                    )

                    // Check if the original employee is different from the current state of the ViewModel fields
                    if (viewModel.originalEmployee.value != currentEmployee) {
                        // If they are different, set the hasChanges flag to true
                        topBarViewModel.setHasChanges(true)
                    } else {
                        // If they are the same, set the hasChanges flag to false
                        topBarViewModel.setHasChanges(false)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(veryLightGray),
                contentAlignment = Alignment.Center
            ) {
                if (showDialog.value) {
                    AlertDialog(
                        onDismissRequest = { showDialog.value = false },
                        title = { Text("No changes were made.") },
                        text = { Text("Do you want to leave anyway?") },
                        confirmButton = {
                            Button(onClick = {
                                showDialog.value = false
                                navController.popBackStack()
                            }) {
                                Text("Leave Anyway")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showDialog.value = false }) {
                                Text("Stay")
                            }
                        }
                    )
                }
                employee.let {
                    topBarViewModel.updateTopBarText("Employee ID " + it.id.toString())
                }

                EditEmployeeButton(
                    focusManager = focusManager,
                    viewModel = viewModel,
                    topBarViewModel = topBarViewModel,
                    navController = navController,
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    showDialog = showDialog,
                    showSnackbar = showSnackbar
                )
            }
        }
    } else {
        Log.e("EditEmployeeInfoScreen", "Employee not found")
    }
    CustomSnackbar(snackbarHostState = snackbarHostState)
}

@Composable
fun DataFields(
    viewModel: EmployeeViewModel,
    employee: Employee,
    onEmployeeChange: (Employee) -> Unit
) {
    // Create a mutable copy of the employee
    val updatedEmployee = remember { mutableStateOf(employee.copy()) }

    val fields = listOf(
        // probably remove any ability to edit ID field
//        Field(
//            "id",
//            FieldValue.StringField(employee.id.toString()),
//            "ID",
//            "Enter ID"
//        ) { newValue ->
//            val newId = (newValue as FieldValue.StringField).value.toIntOrNull()
//            if (newId != null) {
//                viewModel.id.intValue = newId
//                viewModel.hasChanges.value = true
//            }
//        },
        Field(
            "fname",
            FieldValue.StringField(employee.fname),
            "First Name",
            "Enter first name"
        ) { newValue ->
            val newFname = (newValue as FieldValue.StringField).value
            viewModel.fname.value = newFname
            viewModel.hasChanges.value = true
        },
        Field(
            "lname",
            FieldValue.StringField(employee.lname),
            "Last Name",
            "Enter last name"
        ) { newValue ->
            val newLname = (newValue as FieldValue.StringField).value
            viewModel.lname.value = newLname
            viewModel.hasChanges.value = true
        },
        Field(
            "nname",
            FieldValue.StringField(employee.nname),
            "Nick Name",
            "Enter nick name"
        ) { newValue ->
            val newNname = (newValue as FieldValue.StringField).value
            viewModel.nname.value = newNname
            viewModel.hasChanges.value = true
        },
        Field(
            "email",
            FieldValue.StringField(employee.email),
            "Email",
            "Enter email"
        ) { newValue ->
            val newEmail = (newValue as FieldValue.StringField).value
            viewModel.email.value = newEmail
            viewModel.hasChanges.value = true
        },
        Field(
            "pnumber",
            FieldValue.StringField(employee.pnumber),
            "Phone Number",
            "Enter phone number"
        ) { newValue ->
            val newPNumber = (newValue as FieldValue.StringField).value
            viewModel.pnumber.value = newPNumber
            viewModel.hasChanges.value = true
        },
        Field(
            "isActive",
            FieldValue.BooleanField(employee.isActive),
            "Is Active?",
            ""
        ) { newValue ->
            val newIsActive = (newValue as FieldValue.BooleanField).value
            viewModel.isActive.value = newIsActive
            viewModel.hasChanges.value = true
        },
        Field(
            "isTrainedForOpening",
            FieldValue.BooleanField(employee.opening),
            "Trained for Opening?",
            ""
        ) { newValue ->
            val newOpening = (newValue as FieldValue.BooleanField).value
            viewModel.opening.value = newOpening
            viewModel.hasChanges.value = true
        },
        Field(
            "isTrainedForClosing",
            FieldValue.BooleanField(employee.closing),
            "Trained for Closing?",
            ""
        ) { newValue ->
            val newClosing = (newValue as FieldValue.BooleanField).value
            viewModel.closing.value = newClosing
            viewModel.hasChanges.value = true
        }
    )

    LaunchedEffect(updatedEmployee.value) {
        onEmployeeChange(updatedEmployee.value)
    }
    LazyColumn {

        item { EditAvailabilityButton() } // edit availability button

        items(fields.size) { index ->
            val field = fields[index]
            when (field.initialValue) {
                is FieldValue.StringField -> {
                    val text = remember { mutableStateOf((field.initialValue).value) }

                    GenericTextField(
                        text = text,
                        isError = remember { mutableStateOf(false) },  // No error state
                        label = field.label,
                        placeholder = field.placeholder,
                        onFocusChange = { focusState ->
                            // Handle focus change
                        },
                        onValueChange = { newValue ->
                            text.value = newValue
                            field.onValueChange(FieldValue.StringField(newValue))
                            viewModel.hasChanges.value = true
                        }
                    )
                }

                is FieldValue.BooleanField -> {
                    val isChecked = remember { mutableStateOf((field.initialValue).value) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(field.label)
                        Box(
                            modifier = Modifier.padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Switch(
                                checked = isChecked.value,
                                onCheckedChange = { newValue ->
                                    isChecked.value = newValue
                                    field.onValueChange(FieldValue.BooleanField(newValue))
                                    viewModel.hasChanges.value = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }
    // Only update the ViewModel when the "Edit Employee" button is clicked
    onEmployeeChange(updatedEmployee.value)
}

sealed class FieldValue {
    data class StringField(val value: String) : FieldValue()
    data class BooleanField(val value: Boolean) : FieldValue()
}

data class Field(
    val name: String,
    val initialValue: FieldValue,
    val label: String,
    val placeholder: String,
    val onValueChange: (FieldValue) -> Unit
)

@Composable
fun EditEmployeeButton(
    focusManager: FocusManager,
    viewModel: EmployeeViewModel,
    topBarViewModel: TopBarViewModel,
    navController: NavController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    showDialog: MutableState<Boolean>,
    showSnackbar: MutableState<Boolean>
) {
    Button(
        onClick = {
            focusManager.clearFocus()
            // Create a new Employee instance from the ViewModel fields
            val currentEmployee = Employee(
                id = viewModel.id.intValue,
                fname = viewModel.fname.value,
                lname = viewModel.lname.value,
                nname = viewModel.nname.value,
                email = viewModel.email.value,
                pnumber = viewModel.pnumber.value,
                isActive = viewModel.isActive.value,
                opening = viewModel.opening.value,
                closing = viewModel.closing.value
            )

            if (viewModel.validateFields() && viewModel.isValidEmail(viewModel.email.value) && viewModel.isValidPhoneNumber(viewModel.pnumber.value)) {
                // If the fields are valid, check if any changes were made
                if (viewModel.originalEmployee.value != currentEmployee) {
                    // If they are different, update the employee info in the ViewModel and the database
                    viewModel.updateEmployee(currentEmployee)
                    viewModel.updateEmployeeInfo()
                    viewModel.originalEmployee.value = currentEmployee.copy()

                    // Show a snackbar message and navigate back
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Employee edited successfully.",
                            actionLabel = "Dismiss",
                            duration = SnackbarDuration.Short
                        )
                    }
                    navController.popBackStack()
                    topBarViewModel.setHasChanges(false) // back button logic
                } else {
                    // If no changes were made, show a dialog
                    showDialog.value = true
                    showSnackbar.value = true
                }
            } else {
                // If the fields are not valid, show a snackbar message
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Please complete all fields.",
                        actionLabel = "Dismiss",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    ) {
        Text("Edit Employee")
    }
}

@Composable
fun EditAvailabilityButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp), // Add padding to the top
        contentAlignment = Alignment.Center // Center the content
    ) {
        Button(
            onClick = {
                // Handle edit availability click here
            }
        ) {
            Text("Edit Availability")
        }
    }
}






