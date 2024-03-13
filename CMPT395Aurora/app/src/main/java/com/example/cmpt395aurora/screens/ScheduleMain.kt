/**
 *   Schedule Main page
 *   v1.00
 *
 */

package com.example.cmpt395aurora.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState


@ExperimentalMaterial3Api
@Composable
fun ScheduleMain() {
    // Assuming you want the initial date to be the current date
    // You can replace LocalDate.now() with any other LocalDate if needed
    val datePickerState = rememberDatePickerState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Schedule Main Screen")
        // Pass the datePickerState to the DatePicker
        DatePicker(modifier = Modifier.padding(), state = datePickerState)
    }
}
