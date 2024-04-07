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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.cmpt395solaris.ComponentFunctions.CustomSnackbar
import com.example.cmpt395solaris.ComponentFunctions.GenericTextField
import com.example.cmpt395solaris.database.employees.EmployeeViewModel
import com.example.cmpt395solaris.database.TopBarViewModel
import com.example.cmpt395solaris.database.availability.EmpAvail
import com.example.cmpt395solaris.database.availability.EmpAvailabilityViewModel

/**
 *  Do not alter without consulting Jamie!
 */


@Composable
fun EmployeeAvailabilityScreen(
    navController: NavController,
    viewModel: EmployeeViewModel,
    topBarViewModel: TopBarViewModel,
    employeeID: String,
    availabilityViewModel: EmpAvailabilityViewModel
) {
    val id = employeeID.toInt()

    val avial: EmpAvail? = availabilityViewModel.getAvailability(id)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val showSnackbar = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    val veryLightGray = Color(0xFFF5F5F5)
    val focusManager = LocalFocusManager.current

    val originalAvailability = remember { mutableStateOf(availabilityViewModel.getAvailability(id)) }
    val editAvailability =
        remember { mutableStateOf(originalAvailability.value?.copy()) } // Create a copy for editing

    Log.d("EditAvailabilityScreen", "Employee ID: $id, Availability: $avial")

//    avail: EmpAvail,
//    availabilityViewModel: EmpAvailabilityViewModel,
//    onAvailabilityChange: (EmpAvail) -> Unit


    if (avial != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
        ) {
            Box(modifier = Modifier.weight(1f)) {
                AvailabilityFields(
                    avial,
                    availabilityViewModel
                ) { updatedAvailability ->
                    editAvailability.value = updatedAvailability // Update the copy for editing

                    // Create a new Employee instance from the ViewModel fields
                    val currentAvailability = EmpAvail(
                        id = viewModel.id.intValue,
                        mondayAM = availabilityViewModel.mondayAM.value,
                        mondayPM = availabilityViewModel.mondayPM.value,
                        tuesdayAM = availabilityViewModel.mondayAM.value,
                        tuesdayPM = availabilityViewModel.mondayPM.value,
                        wednesdayAM = availabilityViewModel.mondayAM.value,
                        wednesdayPM = availabilityViewModel.mondayPM.value,
                        thursdayAM = availabilityViewModel.mondayAM.value,
                        thursdayPM = availabilityViewModel.mondayPM.value,
                        fridayAM = availabilityViewModel.mondayAM.value,
                        fridayPM = availabilityViewModel.mondayPM.value,
                        saturday = availabilityViewModel.mondayAM.value,
                        sunday = availabilityViewModel.mondayPM.value,
                    )

                    // Check if the original employee is different from the current state of the ViewModel fields
                    if (availabilityViewModel.originalAvailability.value != currentAvailability) {
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
            }
        }
    } else {
        Log.e("EditEmployeeInfoScreen", "Employee not found")
    }
    CustomSnackbar(snackbarHostState = snackbarHostState)
}


@Composable
fun AvailabilityFields(
    avail: EmpAvail,
    availabilityViewModel: EmpAvailabilityViewModel,
    onAvailabilityChange: (EmpAvail) -> Unit
) {
    // Create a mutable copy of the employee
    val updatedAvailability = remember { mutableStateOf(avail.copy()) }

    val fields = listOf(
        Field(
            "MondayAM",
            FieldValue.BooleanField(updatedAvailability.value.mondayAM),
            "Monday Mornings: ",
            ""
        ) { newValue ->
            val newAvailability = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.mondayAM.value = newAvailability
            updatedAvailability.value = updatedAvailability.value.copy(mondayAM = newAvailability)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "MondayPM",
            FieldValue.BooleanField(updatedAvailability.value.mondayPM),
            "Monday Evenings: ",
            ""
        ) { newValue ->
            val newAvailability = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.mondayPM.value = newAvailability
            updatedAvailability.value = updatedAvailability.value.copy(mondayPM = newAvailability)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "TuesdayAM",
            FieldValue.BooleanField(updatedAvailability.value.tuesdayAM),
            "Tuesday Mornings: ",
            ""
        ) { newValue ->
            val newAvailability = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.tuesdayAM.value = newAvailability
            updatedAvailability.value = updatedAvailability.value.copy(tuesdayAM = newAvailability)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "TuesdayPM",
            FieldValue.BooleanField(updatedAvailability.value.tuesdayPM),
            "Tuesday Evenings: ",
            ""
        ) { newValue ->
            val newAvailability = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.tuesdayPM.value = newAvailability
            updatedAvailability.value = updatedAvailability.value.copy(tuesdayPM = newAvailability)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "WednesdayAM",
            FieldValue.BooleanField(updatedAvailability.value.wednesdayAM),
            "Wednesday Mornings: ",
            ""
        ) { newValue ->
            val newAvailability = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.wednesdayAM.value = newAvailability
            updatedAvailability.value = updatedAvailability.value.copy(wednesdayAM = newAvailability)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "WednesdayPM",
            FieldValue.BooleanField(updatedAvailability.value.wednesdayPM),
            "Wednesday Evenings: ",
            ""
        ) { newValue ->
            val newAvailability = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.wednesdayPM.value = newAvailability
            updatedAvailability.value = updatedAvailability.value.copy(wednesdayPM = newAvailability)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "ThursdayAM",
            FieldValue.BooleanField(updatedAvailability.value.thursdayAM),
            "Thursday Mornings: ",
            ""
        ) { newValue ->
            val newAvailability = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.thursdayAM.value = newAvailability
            updatedAvailability.value = updatedAvailability.value.copy(thursdayAM = newAvailability)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "ThursdayPM",
            FieldValue.BooleanField(updatedAvailability.value.thursdayPM),
            "Thursday Evenings: ",
            ""
        ) { newValue ->
            val newAvailability = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.thursdayPM.value = newAvailability
            updatedAvailability.value = updatedAvailability.value.copy(thursdayPM = newAvailability)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "FridayAM",
            FieldValue.BooleanField(updatedAvailability.value.fridayAM),
            "Friday Mornings: ",
            ""
        ) { newValue ->
            val newAvailability = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.fridayAM.value = newAvailability
            updatedAvailability.value = updatedAvailability.value.copy(fridayAM = newAvailability)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "FridayPM",
            FieldValue.BooleanField(updatedAvailability.value.fridayPM),
            "Friday Evenings: ",
            ""
        ) { newValue ->
            val newAvailability = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.fridayPM.value = newAvailability
            updatedAvailability.value = updatedAvailability.value.copy(fridayPM = newAvailability)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "Saturday",
            FieldValue.BooleanField(updatedAvailability.value.saturday),
            "Saturday: ",
            ""
        ) { newValue ->
            val newAvailability = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.saturday.value = newAvailability
            updatedAvailability.value = updatedAvailability.value.copy(saturday = newAvailability)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "Sunday",
            FieldValue.BooleanField(updatedAvailability.value.sunday),
            "Sunday: ",
            ""
        ) { newValue ->
            val newAvailability = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.sunday.value = newAvailability
            updatedAvailability.value = updatedAvailability.value.copy(sunday = newAvailability)
            availabilityViewModel.hasChanges.value = true
        },

    )

    LaunchedEffect(updatedAvailability.value) {
        onAvailabilityChange(updatedAvailability.value)
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
                            availabilityViewModel.hasChanges.value = true
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
                                    availabilityViewModel.hasChanges.value = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }
    // Only update the ViewModel when the "Edit Employee" button is clicked
    onAvailabilityChange(updatedAvailability.value)
}








