/**
 * top bar v1.4
 *     - New logic to allow back button to use transitions - Jamie
 *     - New home button - Grant
 */

package com.example.cmpt395luloo.ComponentFunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cmpt395luloo.iconsbackbutton.IconsBackButton
import com.google.relay.compose.BoxScopeInstance.columnWeight
import com.google.relay.compose.BoxScopeInstance.rowWeight

@Composable
fun TopBar(navController: NavController) {
    // Observe the NavController back stack
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column {
        Row(
            // back button logic
            modifier = Modifier.fillMaxWidth(),
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
                }){
                    IconsBackButton()// back button icon
                }

                IconButton(onClick = {
                }){

                    CustomIconHome(navController = navController)// back button icon

                }
            } else {
                Spacer(modifier = Modifier.size(48.dp)) // for home page
            }
            // Add other content for the top bar here such as page title or the 'hi, [manager name]'
            // will depend which page you are on in app so be careful!
        }
        Spacer(modifier = Modifier.height(4.dp)) // Add space above the divider for balanced look
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
            .rowWeight(1.0f)
            .columnWeight(1.0f)
            .clip(CircleShape)
            .clickable(
                // will disable when page is reached but label doesn't disable needs work
                // has ripple effect but only default, doesnt cover label, looks wrong
                enabled = !isEmployeePage,
            ) {
                // might need to clean this up sorry
                if (!isEmployeePage) {
                    navController.navigate("home")
                }
            },
        //disable the button once your on the page
        tint = if (isEmployeePage) Color.Gray else Color.Unspecified
    )
}