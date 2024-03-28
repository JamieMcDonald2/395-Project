/**
 * Edit Employee Info Screen v2.0
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
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cmpt395aurora.ComponentFunctions.CustomSnackbar
import com.example.cmpt395aurora.ComponentFunctions.GenericTextField
import com.example.cmpt395aurora.database.employees.Employee
import com.example.cmpt395aurora.database.employees.EmployeeViewModel
import com.example.cmpt395aurora.database.TopBarViewModel
import kotlinx.coroutines.launch

/**
 *  navController might go?
 */
@Composable
fun EditEmployeeInfoScreen(navController: NavController, viewModel: EmployeeViewModel, topBarViewModel: TopBarViewModel, employeeID: String) {
    val id = employeeID.toInt()
    val employee: Employee? = viewModel.getEmployeeByID(id)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val VeryLightGray = Color(0xFFF5F5F5)   // might have to remove or change
    val focusManager = LocalFocusManager.current

    if (employee != null) {
        viewModel.id.value = employee.id
        viewModel.fname.value = employee.fname
        viewModel.lname.value = employee.lname
        viewModel.nname.value = employee.nname
        viewModel.email.value = employee.email
        viewModel.pnumber.value = employee.pnumber
        viewModel.isActive.value = employee.isActive
        viewModel.opening.value = employee.opening
        viewModel.closing.value = employee.closing

        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
        ) {
            Box(modifier = Modifier.weight(1f)) {
                DataFields(viewModel, employee) { updatedEmployee ->
                    viewModel.updateEmployee(updatedEmployee)
                }
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
                        Log.d("OnClick", "fname: ${viewModel.fname.value}, lname: ${viewModel.lname.value}") // testing
                        if (viewModel.validateFields() && viewModel.isValidEmail(viewModel.email.value) && viewModel.isValidPhoneNumber(
                                viewModel.pnumber.value
                            )
                        ) {
                            viewModel.updateEmployee(employee)
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Employee edited successfully.",
                                    actionLabel = "Dismiss",
                                    duration = androidx.compose.material3.SnackbarDuration.Short
                                )
                            }
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Please complete all the required the fields.",
                                    actionLabel = "Dismiss",
                                    duration = androidx.compose.material3.SnackbarDuration.Short
                                )
                            }
                        }
                    }
                ) {
                    Text("Edit Employee")
                }
                CustomSnackbar(snackbarHostState = snackbarHostState)
                // prevent null pointer exception for id string if id is empty
                employee?.let {
                    Log.d(
                        "EmployeeInfoScreen",
                        "Calling updateTopBarText with: ${employee.id}"
                    )  // testing
                    topBarViewModel.updateTopBarText("Employee ID " + it.id.toString())
                }
            }
        }
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
                viewModel.setId(newId)
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
            viewModel.fname.value = newFname
            Log.d("OnValueChange", "fname: ${viewModel.fname.value}")  // testing
            onEmployeeChange(employee.copy(fname = newFname))
        },

        Field(
            "lname",
            FieldValue.StringField(employee.lname),
            "Last Name",
            "Enter last name"
        ) { newValue ->
            val newLname = (newValue as FieldValue.StringField).value
            viewModel.lname.value = newLname
            onEmployeeChange(employee.copy(fname = newLname))
        },
        Field(
            "nname",
            FieldValue.StringField(employee.nname),
            "Nick Name",
            "Enter nick name"
        ) { newValue ->
            onEmployeeChange(employee.copy(nname = (newValue as FieldValue.StringField).value))
        },
        Field(
            "email",
            FieldValue.StringField(employee.email),
            "Email",
            "Enter email"
        ) { newValue ->
            onEmployeeChange(employee.copy(email = (newValue as FieldValue.StringField).value))
        },
        Field(
            "pnumber",
            FieldValue.StringField(employee.pnumber),
            "Phone Number",
            "Enter Phone Number"
        ) { newValue ->
            onEmployeeChange(employee.copy(pnumber = (newValue as FieldValue.StringField).value))
        },
        Field(
            "isActive",
            FieldValue.BooleanField(employee.isActive),
            "Is Active?",
            ""
        ) { newValue ->
            onEmployeeChange(employee.copy(isActive = (newValue as FieldValue.BooleanField).value))
        },
        Field(
            "isTrainedForOpening",
            FieldValue.BooleanField(employee.opening),
            "Trained for Opening?",
            "",
        ) { newValue ->
            onEmployeeChange(employee.copy(opening = (newValue as FieldValue.BooleanField).value))
        },
        Field(
            "isTrainedForClosing",
            FieldValue.BooleanField(employee.closing),
            "Trained for Closing?",
            "",
        ) { newValue ->
            onEmployeeChange(employee.copy(closing = (newValue as FieldValue.BooleanField).value))
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
                                    field.onValueChange(FieldValue.BooleanField(newValue))
                                }
                            )
                        }
                    }
                }
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





