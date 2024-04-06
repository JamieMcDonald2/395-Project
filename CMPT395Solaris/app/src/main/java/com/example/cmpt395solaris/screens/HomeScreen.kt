/**
 * home screen v1.05
 *
 * 1.05
 * - new logic for displaying text in topbar
 *
 * 1.04
 * - major debugging of calendar and alignment of screen
 *
 * 1.03:
 * - Added Weekly Calendar UI
 *
 * 1.02:
 * - Removed 'add employee button in favor of a FAB on Employee screen (see employee screen)
 *
 * 1.01:
 * - added new structure for alignment, and an 'add employee' button
 *
 */

package com.example.cmpt395solaris.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cmpt395solaris.ComponentFunctions.ThisWeek
import com.example.cmpt395solaris.database.TopBarViewModel
import com.example.cmpt395solaris.database.settings.SettingsViewModel

@Composable
fun HomeScreen(navController: NavHostController, topBarViewModel: TopBarViewModel, settingsViewModel: SettingsViewModel) {

    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(key1 = currentRoute) {
        if (currentRoute == "home") {
            topBarViewModel.updateTopBarText("Hi, ${settingsViewModel.username.value}")
        }
    }

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
