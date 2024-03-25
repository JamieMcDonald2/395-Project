/**
 * Add employee Screen v1.09
 *
 *  1.09
 *  - added"onValueChange" parameter for username settings and other values (to pass to rest of app)
 *
 *  1.08
 *  - changed several non-text fields to toggles since they only have boolean values
 *
 *  1.07
 *  - removed toast, added snack bar, for better UX
 *
 *  1.06
 *  - moved add button
 *  - added toggle switches for 'Is active', and 'trained' fields
 *  - error checking for NULL employee fields (won't allow now)
 *  - 'Toast' messages for confirmation/error handling
 *      - https://developer.android.com/guide/topics/ui/notifiers/toasts
 *      - https://developer.android.com/reference/android/widget/Toast
 *
 *  1.05
 *  - Added all UI, added scrolling (loop to create text fields)
 *      - selected/focused/error states (error state WIP) for text fields
 *      - https://developer.android.com/jetpack/compose/text#textfield
 *      - https://en.cppreference.com/w/cpp/utility/functional/reference_wrapper
 *      - https://formly.dev/docs/guide/custom-formly-wrapper/
 *
 *  1.02
 *  - new fields to match database
 *
 *  1.01
 *  - added name field UI
 *  - https://developer.android.com/jetpack/compose/state
 *  - https://developer.android.com/reference/kotlin/androidx/compose/runtime/MutableState
 *  - https://stackoverflow.com/questions/66169601/what-is-the-difference-between-remember-and-mutablestate-in-android-jetpack
 *  - https://stackoverflow.com/questions/69932411/correct-way-to-handle-mutable-state-of-list-of-data-in-jetpack-compose
 *
 */

package com.example.cmpt395aurora.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.sp
import com.example.cmpt395aurora.ComponentFunctions.CustomSnackbar
import com.example.cmpt395aurora.ComponentFunctions.GenericTextField
import com.example.cmpt395aurora.database.employees.AddEmployeeTesting
import com.example.cmpt395aurora.database.employees.EmployeeViewModel
import kotlinx.coroutines.launch

// This was actually really hard to implement! please don't modify unless it works!
// needs more clean up not as nice as our other designed pages yet
@Composable
fun AddEmployeeScreen(viewModel: EmployeeViewModel) {  // we can move the formwrapper back inside after testing
    val addEmployeeTesting = AddEmployeeTesting()

    //remove this later
//    addEmployeeTesting.populateTestData(viewModel)

    FormWrapper(viewModel)

    //remove or hide this later
    ExtendedFloatingActionButton(
        onClick = { addEmployeeTesting.populateTestData(viewModel) },
        modifier = Modifier
            .padding(4.dp)
            .size(20.dp)
    ) {
        Text(
            "Populate Test Data",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 1.sp)
        )
    }
}

// this will move back inside AddEmployeeScreen later
@Composable
fun FormWrapper(viewModel: EmployeeViewModel) {
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val fields = listOf(
        "First Name",
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
                "First Name", "Last Name", "Nick Name", "Email", "Phone Number" -> {
                    val text = when (field) {
                        "First Name" -> viewModel.fname
                        "Last Name" -> viewModel.lname
                        "Nick Name" -> viewModel.nname
                        "Email" -> viewModel.email
                        "Phone Number" -> viewModel.pnumber
                        else -> remember { mutableStateOf("") }
                    }
                    val isError = remember { mutableStateOf(false) }
                    // had to add "onValueChange" parameter for username settings and other values
                    GenericTextField(
                        text = text,
                        isError = isError,
                        label = field,
                        placeholder = "Enter $field",
                        onFocusChange = { },
                        onValueChange = { newValue -> /* handle value change */ }
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