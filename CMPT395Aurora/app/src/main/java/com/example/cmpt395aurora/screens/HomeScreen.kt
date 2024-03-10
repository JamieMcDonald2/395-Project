/**
 * home screen v1.04
 *
 * 1.01:
 * - added new structure for alignment, and an 'add employee' button
 *
 * 1.02:
 * - Removed 'add employee button in favor of a FAB on Employee screen (see employee screen)
 *
 * 1.03:
 * - Added Weekly Calendar UI
 *
 * 1.04
 * - major debugging of calendar and alignment of screen
 */

package com.example.cmpt395aurora.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.cmpt395aurora.ComponentFunctions.ThisWeek

@Composable
fun HomeScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Weekly Calendar
            ThisWeek()
        }

        Text(
            text = "Middle of the screen",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}