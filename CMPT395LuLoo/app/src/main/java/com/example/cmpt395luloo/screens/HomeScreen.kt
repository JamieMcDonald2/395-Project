/**
 * home screen v1.01
 *
 * 1.01:
 * - added new structure for alignment, and an 'add employee' button
 */

package com.example.cmpt395luloo.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Weekly Calendar Goes Here",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp) // Add padding to the top item
        )

        Text(
            text = "Middle of the screen",
            modifier = Modifier.align(Alignment.Center)
        )

        // Decorative box
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
//                .offset(y = -25.dp) // Adjust this to move the box higher
                .size(width = 350.dp, height = 250.dp) // Adjust this to change the size of the box
//                .padding(bottom = 48.dp) // Add padding to the bottom of the box
                .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)) // This adds a border to the box
        ) {
            // Button
            Button(
                onClick = { navController.navigate("employee2") },
                modifier = Modifier
                    .align(Alignment.Center) // Align the button to the center of the box
                    .size(210.dp, 40.dp) // Specify the size of the button
            ) {
                val text1 = "+"
                val text2 = "Add New Employee"
                Text(text = "\u0020" + text1 + "\u0020\u0020\u0020\u0020\u0020" + text2 + "\u0020")
            }
        }
    }
}