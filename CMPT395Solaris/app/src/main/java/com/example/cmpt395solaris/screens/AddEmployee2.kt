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
 *  Do not alter without consulting Ben!
 */

@Composable
fun AddEmployeeScreen2(
    navController: NavController,
    viewModel: EmployeeViewModel,
    topBarViewModel: TopBarViewModel
) {
    val id = viewModel.lastID.intValue + 1

    LaunchedEffect(Unit) {
        viewModel.clearEmployeeFields()
    }

    val employee = Employee(
        id = viewModel.lastID.intValue + 1,
        fname = "",
        lname = "",
        nname = "",
        email = "",
        pnumber = "",
        isActive = false,
        opening = false,
        closing = false
    )
    viewModel.id.intValue = viewModel.lastID.intValue + 1

    val editEmployee = remember { mutableStateOf(employee) }
//    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
//    val showSnackbar = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    val veryLightGray = Color(0xFFF5F5F5)
    val focusManager = LocalFocusManager.current

    Log.d("AddEmployeeInfoScreen", "Employee ID: $id, Employee: $employee")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
    ) {
        Box(modifier = Modifier.weight(1f)) {
            AddEmployeeDataFields(
                navController = navController,
                viewModel = viewModel,
                topBarViewModel = topBarViewModel,
                employee = employee,
                focusManager = focusManager,
                snackbarHostState = snackbarHostState,
                showDialog = showDialog
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

//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(60.dp)
//                    .background(veryLightGray),
//                contentAlignment = Alignment.Center
//            ) {
//                if (showDialog.value) {
//                    AlertDialog(
//                        onDismissRequest = { showDialog.value = false },
//                        title = { Text("No changes were made.") },
//                        text = { Text("Do you want to leave anyway?") },
//                        confirmButton = {
//                            Button(onClick = {
//                                showDialog.value = false
//                                navController.popBackStack()
//                            }) {
//                                Text("Leave Anyway")
//                            }
//                        },
//                        dismissButton = {
//                            Button(onClick = { showDialog.value = false }) {
//                                Text("Stay")
//                            }
//                        }
//                    )
//                }
//                employee.let {
//                    topBarViewModel.updateTopBarText("Employee ID " + it.id.toString())
//                }
//            }
        }
        CustomSnackbar(snackbarHostState = snackbarHostState)
    }
}

@Composable
fun AddEmployeeDataFields(
    navController: NavController,
    viewModel: EmployeeViewModel,
    topBarViewModel: TopBarViewModel,
    employee: Employee,
    focusManager: FocusManager,
    snackbarHostState: SnackbarHostState,
    showDialog: MutableState<Boolean>,
    onEmployeeChange: (Employee) -> Unit
) {
    // Create a mutable copy of the employee
    val updatedEmployee = remember { mutableStateOf(employee.copy()) }

    val showSnackbar = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()


    val fields = listOf(
        Field(
            "fname",
            FieldValue.StringField(updatedEmployee.value.fname),
            "First Name",
            "Enter first name"
        ) { newValue ->
            val newFname = (newValue as FieldValue.StringField).value
            viewModel.fname.value = newFname
            updatedEmployee.value = updatedEmployee.value.copy(fname = newFname)
            viewModel.hasChanges.value = true
        },
        Field(
            "lname",
            FieldValue.StringField(updatedEmployee.value.lname),
            "Last Name",
            "Enter last name"
        ) { newValue ->
            val newLname = (newValue as FieldValue.StringField).value
            viewModel.lname.value = newLname
            updatedEmployee.value = updatedEmployee.value.copy(lname = newLname)
            viewModel.hasChanges.value = true
        },
        Field(
            "nname",
            FieldValue.StringField(updatedEmployee.value.nname),
            "Nick Name",
            "Enter nick name"
        ) { newValue ->
            val newNname = (newValue as FieldValue.StringField).value
            viewModel.nname.value = newNname
            updatedEmployee.value = updatedEmployee.value.copy(nname = newNname)
            viewModel.hasChanges.value = true
        },
        Field(
            "email",
            FieldValue.StringField(updatedEmployee.value.email),
            "Email",
            "Enter email"
        ) { newValue ->
            val newEmail = (newValue as FieldValue.StringField).value
            viewModel.email.value = newEmail
            updatedEmployee.value = updatedEmployee.value.copy(email = newEmail)
            viewModel.hasChanges.value = true
        },
        Field(
            "pnumber",
            FieldValue.StringField(updatedEmployee.value.pnumber),
            "Phone Number",
            "Enter phone number"
        ) { newValue ->
            val newPNumber = (newValue as FieldValue.StringField).value
            viewModel.pnumber.value = newPNumber
            updatedEmployee.value = updatedEmployee.value.copy(pnumber = newPNumber)
            viewModel.hasChanges.value = true
        },
        Field(
            "isActive",
            FieldValue.BooleanField(updatedEmployee.value.isActive),
            "Is Active?",
            ""
        ) { newValue ->
            val newIsActive = (newValue as FieldValue.BooleanField).value
            viewModel.isActive.value = newIsActive
            updatedEmployee.value = updatedEmployee.value.copy(isActive = newIsActive)
            viewModel.hasChanges.value = true
        },
        Field(
            "isTrainedForOpening",
            FieldValue.BooleanField(updatedEmployee.value.opening),
            "Trained for Opening?",
            ""
        ) { newValue ->
            val newOpening = (newValue as FieldValue.BooleanField).value
            viewModel.opening.value = newOpening
            updatedEmployee.value = updatedEmployee.value.copy(opening = newOpening)
            viewModel.hasChanges.value = true
        },
        Field(
            "isTrainedForClosing",
            FieldValue.BooleanField(updatedEmployee.value.closing),
            "Trained for Closing?",
            ""
        ) { newValue ->
            val newClosing = (newValue as FieldValue.BooleanField).value
            viewModel.closing.value = newClosing
            updatedEmployee.value = updatedEmployee.value.copy(closing = newClosing)
            viewModel.hasChanges.value = true
        }
    )

    LaunchedEffect(updatedEmployee.value) {
        onEmployeeChange(updatedEmployee.value)
    }
    LazyColumn {



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

        item { AddEmployeeButton(
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
//        item { EditAvailabilityButton(availabilityViewModel =) } // edit availability button
    }
    // Only update the ViewModel when the "Edit Employee" button is clicked
    onEmployeeChange(updatedEmployee.value)
}


@Composable
fun AddEmployeeButton(
    focusManager: FocusManager,
    viewModel: EmployeeViewModel,
    topBarViewModel: TopBarViewModel,
    navController: NavController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    showDialog: MutableState<Boolean>,
    showSnackbar: MutableState<Boolean>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp), // Add padding to the top
        contentAlignment = Alignment.Center // Center the content
    ){
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

                        //
//
                if (viewModel.validateFields() &&  viewModel.isValidPhoneNumber(viewModel.pnumber.value) && viewModel.isValidEmail(viewModel.email.value)) {
                    // If the fields are valid, check if any changes were made
                    if (viewModel.originalEmployee.value != currentEmployee) {
                        // If they are different, update the employee info in the ViewModel and the database
                        viewModel.addEmployee(
                            viewModel.id.intValue,
                            viewModel.fname.value,
                            viewModel.lname.value,
                            viewModel.nname.value,
                            viewModel.email.value,
                            viewModel.pnumber.value,
                            viewModel.isActive.value,
                            viewModel.opening.value,
                            viewModel.closing.value
                        )
                        //update the latest id added
                        viewModel.lastID.intValue = viewModel.id.intValue
//                        viewModel.updateEmployeeInfo()
                        viewModel.originalEmployee.value = currentEmployee.copy()

                        // Show a snackbar message and navigate back
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Employee added successfully.",
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
            Text("Add Employee")
        }
    }
}





