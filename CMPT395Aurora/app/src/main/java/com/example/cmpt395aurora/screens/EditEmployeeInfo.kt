/**
 * Edit Employee Info Screen v2.2
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
 *   using show dialog and snackbar
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

package com.example.cmpt395aurora.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cmpt395aurora.ComponentFunctions.CustomSnackbar
import com.example.cmpt395aurora.ComponentFunctions.GenericTextField
import com.example.cmpt395aurora.database.employees.Employee
import com.example.cmpt395aurora.database.employees.EmployeeViewModel
import com.example.cmpt395aurora.database.TopBarViewModel
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
    val employee: Employee? = viewModel.getEmployeeByID(id)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val showDialog = remember { mutableStateOf(false) } // Declare showDialog variable

    val VeryLightGray = Color(0xFFF5F5F5) // might have to remove or change
    val focusManager = LocalFocusManager.current

    Log.d("EditEmployeeInfoScreen", "Employee ID: $id, Employee: $employee") // Log employee details

    if (employee != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
        ) {
            Box(modifier = Modifier.weight(1f)) {
                DataFields(
                    viewModel = viewModel,
                    employee = employee,
                    onEmployeeChange = { updatedEmployee ->
                        viewModel.updateEmployee(updatedEmployee)
                    }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(VeryLightGray), // Change to your desired color
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        val email: String = viewModel.email.value ?: ""
                        val phoneNumber: String = viewModel.pnumber.value ?: ""

                        val areFieldsValid = viewModel.validateFields()
                        val isEmailValid: Boolean = viewModel.isValidEmail(email)
                        val isPhoneNumberValid: Boolean = viewModel.isValidPhoneNumber(phoneNumber)

                        if (areFieldsValid && isEmailValid && isPhoneNumberValid) {
                            if (viewModel.hasChanges(employee)) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Employee edited successfully.",
                                        actionLabel = "Dismiss",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                                navController.popBackStack()
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Please complete all fields.",
                                        actionLabel = "Dismiss",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        } else {
                            showDialog.value = true
                        }
                    }
                ) {
                    Text("Edit Employee")
                }

                if (showDialog.value) { // Access showDialog value
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
                CustomSnackbar(snackbarHostState = snackbarHostState)

                // prevent null pointer exception for id string if id is empty
                employee?.let {
                    topBarViewModel.updateTopBarText("Employee ID " + it.id.toString())
                }
            }
        }
    } else {
        Log.e("EditEmployeeInfoScreen", "Employee not found")
    }
}

@Composable
fun DataFields(viewModel: EmployeeViewModel, employee: Employee, onEmployeeChange: (Employee) -> Unit) {

//    var showDialog by remember { mutableStateOf(false) }

    val fields = listOf(
        Field(
            "id",
            FieldValue.StringField(employee.id.toString()),
            "ID",
            "Enter ID"
        ) { newValue ->
            val newId = (newValue as FieldValue.StringField).value.toIntOrNull()
            if (newId == null) {
                // Show an error message or throw an exception
                throw NumberFormatException("Invalid ID: ${newValue.value}")
            } else {
                // Pass the newId directly to the onEmployeeChange lambda function
                onEmployeeChange(employee.copy(id = newId))
            }
        },
        Field(
            "fname",
            FieldValue.StringField(employee.fname),
            "First Name",
            "Enter first name"
        ) { newValue ->
            val newFname = (newValue as FieldValue.StringField).value
            onEmployeeChange(employee.copy(fname = newFname))
        },
        Field(
            "lname",
            FieldValue.StringField(employee.lname),
            "Last Name",
            "Enter last name"
        ) { newValue ->
            val newLname = (newValue as FieldValue.StringField).value
            onEmployeeChange(employee.copy(lname = newLname))
        },
        Field(
            "nname",
            FieldValue.StringField(employee.nname),
            "Nick Name",
            "Enter nick name"
        ) { newValue ->
            val newNname = (newValue as FieldValue.StringField).value
            onEmployeeChange(employee.copy(nname = newNname))
        },
        Field(
            "email",
            FieldValue.StringField(employee.email),
            "Email",
            "Enter email"
        ) { newValue ->
            val Email = (newValue as FieldValue.StringField).value
            onEmployeeChange(employee.copy(email = Email))
        },
        Field(
            "pnumber",
            FieldValue.StringField(employee.pnumber),
            "Phone Number",
            "Enter Phone Number"
        ) { newValue ->
            val pNumber = (newValue as FieldValue.StringField).value
            onEmployeeChange(employee.copy(pnumber = pNumber))
        },
        Field(
            "isActive",
            FieldValue.BooleanField(employee.isActive),
            "Is Active?",
            ""
        ) { newValue ->
            val isActive = (newValue as FieldValue.BooleanField).value
            onEmployeeChange(employee.copy(isActive = isActive))
        },
        Field(
            "isTrainedForOpening",
            FieldValue.BooleanField(employee.opening),
            "Trained for Opening?",
            "",
        ) { newValue ->
            val opening = (newValue as FieldValue.BooleanField).value
            onEmployeeChange(employee.copy(opening = opening))
        },
        Field(
            "isTrainedForClosing",
            FieldValue.BooleanField(employee.closing),
            "Trained for Closing?",
            "",
        ) { newValue ->
            val closing = (newValue as FieldValue.BooleanField).value
            onEmployeeChange(employee.copy(closing = closing))
        }
        // Add other fields here ?
    )

    LazyColumn {
        item {
            Box(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) { // Add padding to the top
                Button(
                    onClick = { /*showDialog = true*/ },
                    modifier = Modifier.align(Alignment.Center) // Center the button
                ) {
                    Text("Edit Availability")
                }
            }

            Spacer(modifier = Modifier.height(8.dp)) // space
        }

        items(fields.size) { index ->
            val field = fields[index]
            when (field.initialValue) {
                is FieldValue.StringField -> {
                    val text =
                        remember { mutableStateOf((field.initialValue).value) }
                    val isError = remember { mutableStateOf(false) }

                    GenericTextField(
                        text = text,
                        isError = isError,
                        label = field.label,
                        placeholder = field.placeholder,
                        onFocusChange = { focusState ->
                            // Handle focus change
                        }
                    ) { newValue ->
                        text.value = newValue
                        field.onValueChange(FieldValue.StringField(newValue))
                    }
                }

                is FieldValue.BooleanField -> {
                    val isChecked =
                        remember { mutableStateOf((field.initialValue).value) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp), // Add horizontal padding
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically // Vertically center the items
                    ) {
                        Text(field.label)
                        Box(
                            modifier = Modifier.padding(4.dp),
                            contentAlignment = Alignment.Center // Vertically center the Switch
                        ) {
                            Switch(
                                checked = isChecked.value,
                                onCheckedChange = { newValue ->
                                    isChecked.value = newValue
                                    Log.d("Switch", "New Value: $newValue")  // testing
                                    field.onValueChange(FieldValue.BooleanField(newValue))
                                }
                            )
                        }
                    }
                }
                // force when to be exhaustive ? it works lol
                else -> {}
            }
        }
//        if (showDialog) {
//            MyOverlay(onDismiss = { showDialog = false }) // Replace with your actual overlay composable
    }
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





