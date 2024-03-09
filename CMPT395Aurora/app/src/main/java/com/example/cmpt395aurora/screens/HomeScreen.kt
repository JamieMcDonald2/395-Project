/**
 * home screen v1.02
 *
 * 1.01:
 * - added new structure for alignment, and an 'add employee' button
 *
 * 1.02:
 * - Removed 'add employee button in favor of a FAB on Employee screen (see employee screen)
 */

package com.example.cmpt395aurora.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    }
}