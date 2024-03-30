/**
 *   Schedule Main page
 *   v1.01
 *
 *   v1.01
 *   Added navigation to the dates
 *
 */

package com.example.cmpt395aurora.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


@ExperimentalMaterial3Api
@Composable
fun ScheduleMain(navController: NavController) {
    val selectedDate = remember { mutableStateOf<Date?>(null) }
    val datePickerState = rememberDatePickerState()

    // Update selectedDate when datePickerState changes
    LaunchedEffect(datePickerState.selectedDateMillis) {
        val selectedMillis = datePickerState.selectedDateMillis ?: return@LaunchedEffect
        val calendar = Calendar.getInstance() // Use the system's default time zone
        calendar.timeInMillis = selectedMillis

        // Set time to the start of the day in the system's default time zone.
        // For some reason the hour of day HAS to be 24 and not 0 to display
        // the correct day. If it is set to 0 then the previous day is passed
        // to the next screen.
        calendar.set(Calendar.HOUR_OF_DAY, 24)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        selectedDate.value = calendar.time
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 0.dp)
        ) {
            item {
                DatePicker(
                    state = datePickerState,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            selectedDate.value?.let {
                                if (isWeekend(it)) {
                                    val dateString = formatDate(it)
                                    navController.navigate("schedule3/$dateString")
                                } else {
                                    val dateString = formatDate(it)
                                    navController.navigate("schedule2/$dateString")
                                }
                            }
                        },
                        enabled = selectedDate.value != null,
                        modifier = Modifier
                            .width(150.dp)
                            .height(50.dp)
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}


fun formatDate(date: Date?): String? {
    if (date == null) return null
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    // No need to set time zone, it uses the system's default time zone
    return format.format(date)
}

fun isWeekend(date: Date?): Boolean {
    if (date == null) return false
    val calendar = Calendar.getInstance()
    calendar.time = date
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY
}