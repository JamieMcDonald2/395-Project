
/**
 *   Schedule Employee page
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

@Composable
fun ScheduleEmployeeScreen() {
    //This screen should have the selected date at the top and drop down menus
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Schedule Employee Main Screen")
    }
}