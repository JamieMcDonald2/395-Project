/**
 *   Employee Main page
 *   v1.00
 *   This is likely where database should start
 */

package com.example.cmpt395luloo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun EmployeeMain() {
    // add list, alphabetical by default that accesses database using employee list icon component
    // will be hard will need to meet probably
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Employee Main Screen")
    }
}
