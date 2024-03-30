/**
 * top bar v2.0
 *
 *     2.0
 *     - new text field - aligned with existing icons
 *     - draw on db user name field for home page
 *          - https://developer.android.com/jetpack/compose/mental-model
 *          - https://developer.android.com/codelabs/basic-android-kotlin-compose-sql
 *
 *     1.5
 *     - added padding for icons to line up better and make ripple effect centered/more predominant
 *
 *     1.41
 *     - Adjusted Home Button
 *
 *     1.4
 *     - New logic to allow back button to use transitions - Jamie
 *     - New home button - Grant
 *
 */

package com.example.cmpt395aurora.ComponentFunctions

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cmpt395aurora.database.TopBarViewModel
import com.google.relay.compose.ColumnScopeInstanceImpl.weight

@Composable
fun TopBar(navController: NavController, topBarViewModel: TopBarViewModel) {
    // Observe the NavController back stack
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp), // Set a fixed height
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Check if the current destination is not the home page
            if (currentRoute != "home") {
                IconButton(onClick = {
                    // Get the previous destination from NavController's back stack
                    val previousDestination = navController.previousBackStackEntry?.destination

                    if (previousDestination != null) {
                        // Navigate to the previous destination
                        previousDestination.route?.let { route ->
                            navController.navigate(route) {
                                // Pop up to the previous destination, inclusive
                                popUpTo(route) { inclusive = true }
                                // Reuse the previous destination if it's in the back stack
                                launchSingleTop = true
                            }
                        }
                    } else {
                        // If there's no previous destination, simply pop the back stack
                        navController.popBackStack()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.padding(0.dp) // Remove the default padding
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(48.dp)) // for home page
            }

//            Log.d("TopBar", "Current topBarText: ${topBarViewModel.topBarText.value}")  // testing
            CustomTextBlock(topBarViewModel)

            if (currentRoute != "home") {
                IconButton(
                    onClick = { navController.navigate("home") }, // Navigate to home page upon click
                    modifier = Modifier.padding(0.dp) // Remove the default padding
                ) {
                    CustomIconHome(navController = navController) // home icon
                }
            } else {
                Spacer(modifier = Modifier.width(48.dp)) // for home page
            }
        }
        Spacer(modifier = Modifier.height(2.dp)) // Add space above the divider for balanced look
        Divider()
    }
}

// Simple components don't need their own file, or figma relay connections, such as this divider
@Composable
fun Divider(color: Color = Color.LightGray, thickness: Dp = 1.dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(thickness)
            .background(color)
    )
}

@Composable
fun CustomIconHome(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isEmployeePage = currentRoute == "home"

    Icon(
        imageVector = Icons.Default.Home,
        contentDescription = "Home",
        modifier = Modifier
            .padding(0.dp) // Remove the default padding
    )
}

// Can pass values to the text field now
@Composable
fun CustomTextBlock(topBarViewModel: TopBarViewModel) {
    Text(
        text = topBarViewModel.topBarText.value,
        textAlign = TextAlign.Center,
        modifier = Modifier.weight(1f)
    )
}