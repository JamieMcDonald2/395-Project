package com.example.cmpt395solaris.screens

import android.annotation.SuppressLint
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
import com.example.cmpt395solaris.database.employees.EmployeeViewModel
import com.example.cmpt395solaris.database.TopBarViewModel
import com.example.cmpt395solaris.database.availability.EmpAvail
import com.example.cmpt395solaris.database.availability.EmpAvailabilityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 *  Do not alter without consulting Jamie!
 */


@Composable
fun EmployeeAvailabilityScreen(
    navController: NavController,
    viewModel: EmployeeViewModel,
    topBarViewModel: TopBarViewModel,
    id: String,
    availabilityViewModel: EmpAvailabilityViewModel
) {

    val empID = id.toInt()

    val avail: EmpAvail? = availabilityViewModel.getAvailability(empID)

//    if (avail != null) {
//        availabilityViewModel.loadViewModel(avail)
//    }

    availabilityViewModel.loadAvailability(empID)

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val showSnackbar = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    val veryLightGray = Color(0xFFF5F5F5)
    val focusManager = LocalFocusManager.current

    val originalAvailability = remember { mutableStateOf(availabilityViewModel.getAvailability(empID)) }
    val editAvailability =
        remember { mutableStateOf(originalAvailability.value?.copy()) } // Create a copy for editing

    Log.d("EditAvailabilityScreen - Main Function", "Employee ID: $empID, Availability: $avail")


//    avail: EmpAvail,
//    availabilityViewModel: EmpAvailabilityViewModel,
//    onAvailabilityChange: (EmpAvail) -> Unit


    if (avail != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
        ) {
            Box(modifier = Modifier.weight(1f)) {
                AvailabilityFields(
                    avail,
                    availabilityViewModel
                ) { updatedAvailability ->
                    editAvailability.value = updatedAvailability // Update the copy for editing

                    // Create a new Employee instance from the ViewModel fields
                    val currentAvailability = EmpAvail(
                        id = viewModel.id.intValue,
                        mondayAM = availabilityViewModel.mondayAM.value,
                        mondayPM = availabilityViewModel.mondayPM.value,
                        tuesdayAM = availabilityViewModel.tuesdayAM.value,
                        tuesdayPM = availabilityViewModel.tuesdayPM.value,
                        wednesdayAM = availabilityViewModel.wednesdayAM.value,
                        wednesdayPM = availabilityViewModel.wednesdayPM.value,
                        thursdayAM = availabilityViewModel.thursdayAM.value,
                        thursdayPM = availabilityViewModel.thursdayPM.value,
                        fridayAM = availabilityViewModel.fridayAM.value,
                        fridayPM = availabilityViewModel.fridayPM.value,
                        saturday = availabilityViewModel.saturday.value,
                        sunday = availabilityViewModel.sunday.value,
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
                AvailabilitySubmitButton(
                    focusManager = focusManager,
                    availabilityViewModel = availabilityViewModel,
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
        Log.e("EditAvailabilityScreen", "Employee not found")
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
            val newMondayAM = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.mondayAM.value = newMondayAM
            updatedAvailability.value = updatedAvailability.value.copy(mondayAM = newMondayAM)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "MondayPM",
            FieldValue.BooleanField(updatedAvailability.value.mondayPM),
            "Monday Evenings: ",
            ""
        ) { newValue ->
            val newMondayPM = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.mondayPM.value = newMondayPM
            updatedAvailability.value = updatedAvailability.value.copy(mondayPM = newMondayPM)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "TuesdayAM",
            FieldValue.BooleanField(updatedAvailability.value.tuesdayAM),
            "Tuesday Mornings: ",
            ""
        ) { newValue ->
            val newTuesdayAM = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.tuesdayAM.value = newTuesdayAM
            updatedAvailability.value = updatedAvailability.value.copy(tuesdayAM = newTuesdayAM)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "TuesdayPM",
            FieldValue.BooleanField(updatedAvailability.value.tuesdayPM),
            "Tuesday Evenings: ",
            ""
        ) { newValue ->
            val newTuesdayPM = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.tuesdayPM.value = newTuesdayPM
            updatedAvailability.value = updatedAvailability.value.copy(tuesdayPM = newTuesdayPM)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "WednesdayAM",
            FieldValue.BooleanField(updatedAvailability.value.wednesdayAM),
            "Wednesday Mornings: ",
            ""
        ) { newValue ->
            val newWednesdayAM = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.wednesdayAM.value = newWednesdayAM
            updatedAvailability.value = updatedAvailability.value.copy(wednesdayAM = newWednesdayAM)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "WednesdayPM",
            FieldValue.BooleanField(updatedAvailability.value.wednesdayPM),
            "Wednesday Evenings: ",
            ""
        ) { newValue ->
            val newWednesdayPM = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.wednesdayPM.value = newWednesdayPM
            updatedAvailability.value = updatedAvailability.value.copy(wednesdayPM = newWednesdayPM)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "ThursdayAM",
            FieldValue.BooleanField(updatedAvailability.value.thursdayAM),
            "Thursday Mornings: ",
            ""
        ) { newValue ->
            val newThursdayAM = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.thursdayAM.value = newThursdayAM
            updatedAvailability.value = updatedAvailability.value.copy(thursdayAM = newThursdayAM)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "ThursdayPM",
            FieldValue.BooleanField(updatedAvailability.value.thursdayPM),
            "Thursday Evenings: ",
            ""
        ) { newValue ->
            val newThursdayPM = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.thursdayPM.value = newThursdayPM
            updatedAvailability.value = updatedAvailability.value.copy(thursdayPM = newThursdayPM)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "FridayAM",
            FieldValue.BooleanField(updatedAvailability.value.fridayAM),
            "Friday Mornings: ",
            ""
        ) { newValue ->
            val newFridayAM = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.fridayAM.value = newFridayAM
            updatedAvailability.value = updatedAvailability.value.copy(fridayAM = newFridayAM)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "FridayPM",
            FieldValue.BooleanField(updatedAvailability.value.fridayPM),
            "Friday Evenings: ",
            ""
        ) { newValue ->
            val newFridayPM = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.fridayPM.value = newFridayPM
            updatedAvailability.value = updatedAvailability.value.copy(fridayPM = newFridayPM)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "Saturday",
            FieldValue.BooleanField(updatedAvailability.value.saturday),
            "Saturday: ",
            ""
        ) { newValue ->
            val newSaturday = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.saturday.value = newSaturday
            updatedAvailability.value = updatedAvailability.value.copy(saturday = newSaturday)
            availabilityViewModel.hasChanges.value = true
        },
        Field(
            "Sunday",
            FieldValue.BooleanField(updatedAvailability.value.sunday),
            "Sunday: ",
            ""
        ) { newValue ->
            val newSunday = (newValue as FieldValue.BooleanField).value
            availabilityViewModel.sunday.value = newSunday
            updatedAvailability.value = updatedAvailability.value.copy(sunday = newSunday)
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



@SuppressLint("RestrictedApi")
@Composable
fun AvailabilitySubmitButton(
    focusManager: FocusManager,
    availabilityViewModel: EmpAvailabilityViewModel,
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
            val currentAvailability = EmpAvail(
                id = availabilityViewModel.id.intValue,
                mondayAM = availabilityViewModel.mondayAM.value,
                mondayPM = availabilityViewModel.mondayPM.value,
                tuesdayAM = availabilityViewModel.tuesdayAM.value,
                tuesdayPM = availabilityViewModel.tuesdayPM.value,
                wednesdayAM = availabilityViewModel.wednesdayAM.value,
                wednesdayPM = availabilityViewModel.wednesdayPM.value,
                thursdayAM = availabilityViewModel.thursdayAM.value,
                thursdayPM = availabilityViewModel.thursdayPM.value,
                fridayAM = availabilityViewModel.fridayAM.value,
                fridayPM = availabilityViewModel.fridayPM.value,
                saturday = availabilityViewModel.saturday.value,
                sunday = availabilityViewModel.sunday.value,
            )

            // If the fields are valid, check if any changes were made
            if (availabilityViewModel.originalAvailability.value != currentAvailability) {
                // If they are different, update the employee info in the ViewModel and the database
                availabilityViewModel.updateAvailability(currentAvailability)
                availabilityViewModel.updateAvailabilityInfo()
                availabilityViewModel.originalAvailability.value = currentAvailability.copy()

                // Show a snackbar message and navigate back
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Availability edited successfully.",
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
        }
    ) {
        Text("Submit")
    }
}



