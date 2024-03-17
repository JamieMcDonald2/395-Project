/**
 * Employee Info Screen
 *
 *
 */

package com.example.cmpt395aurora.screens


import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.cmpt395aurora.ComponentFunctions.CustomSnackbar
import com.example.cmpt395aurora.ComponentFunctions.GenericTextField
import com.example.cmpt395aurora.database.employees.Employee
import com.example.cmpt395aurora.database.employees.EmployeeViewModel
import kotlinx.coroutines.launch

// This was actually really hard to implement! please don't modify unless it works!
// needs more clean up not as nice as our other designed pages yet
@Composable
fun EmployeeInfoScreen(viewModel: EmployeeViewModel) {  // we can move the formwrapper back inside after testing
//    val addEmployeeTesting = AddEmployeeTesting()
//
    //remove this later
//    addEmployeeTesting.populateTestData(viewModel)

    DataFields(viewModel)

}

// this will move back inside AddEmployeeScreen later
@Composable
fun DataFields(viewModel: EmployeeViewModel) {
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val fields = listOf(
        "This is an employee info page",
        "Last Name",
        "Nick Name",
        "Email",
        "Phone Number",
        "Is Active?",
        "Trained for Opening?",
        "Trained for Closing?"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            // cool!
            .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
    ) {
        /* this loop might not work for error checking :(
           need to add error checking
        */
        items(fields) { field ->
            when (field) {
                "This is an employee info page", "Last Name", "Nick Name", "Email", "Phone Number" -> {
                    val text = when (field) {
                        "This is an employee info page" -> viewModel.fname
                        "Last Name" -> viewModel.lname
                        "Nick Name" -> viewModel.nname
                        "Email" -> viewModel.email
                        "Phone Number" -> viewModel.pnumber
                        else -> remember { mutableStateOf("") }
                    }
                    val isError = remember { mutableStateOf(false) }

                    GenericTextField(
                        text = text,
                        isError = isError,
                        label = field,
                        placeholder = "Enter $field",
                        onFocusChange = { }
                    )
                }

                "Is Active?", "Trained for Opening?", "Trained for Closing?" -> {
                    val isChecked = when (field) {
                        "Is Active?" -> viewModel.isActive
                        "Trained for Opening?" -> viewModel.opening
                        "Trained for Closing?" -> viewModel.closing

                        else -> remember { mutableStateOf(false) }
                    }

                    Text(field)
                    Switch(
                        checked = isChecked.value,
                        onCheckedChange = { isChecked.value = it },
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
        // super hard part to figure out!
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        if (viewModel.validateFields()) {
                            viewModel.addEmployee(
                                fname = viewModel.fname.value,
                                lname = viewModel.lname.value,
                                nname = viewModel.nname.value,
                                email = viewModel.email.value,
                                pnumber = viewModel.pnumber.value,
                                isActive = viewModel.isActive.value,
                                opening = viewModel.opening.value,
                                closing = viewModel.closing.value
                            )
                            // Clear the fields
                            viewModel.fname.value = ""
                            viewModel.lname.value = ""
                            viewModel.nname.value = ""
                            viewModel.email.value = ""
                            viewModel.pnumber.value = ""
                            viewModel.isActive.value = false
                            viewModel.opening.value = false
                            viewModel.closing.value = false

                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Employee added successfully.",
                                    actionLabel = "Dismiss",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Please complete all the required the fields.",
                                    actionLabel = "Dismiss",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    },
                ) {
                    Text("Add Employee")
                }
            }
        }
    }
    CustomSnackbar(snackbarHostState = snackbarHostState)
}