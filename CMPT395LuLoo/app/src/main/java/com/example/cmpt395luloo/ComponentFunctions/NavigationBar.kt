/**
 * navigation bar v1.0
 */

package com.example.cmpt395luloo.ComponentFunctions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cmpt395luloo.navigationbarfinal.IconContainer1a
import com.example.cmpt395luloo.navigationbarfinal.LabelText1a
import com.example.cmpt395luloo.navigationbarfinal.NavigationBarFinal1
import com.example.cmpt395luloo.navigationbarfinal.Segment1
import com.example.cmpt395luloo.navigationbarfinal.StateLayer1a
import com.example.cmpt395luloo.navigationbarfinal.TopLevel
import com.google.relay.compose.BoxScopeInstance.columnWeight
import com.google.relay.compose.BoxScopeInstance.rowWeight

@Composable
fun NavigationBar(navController: NavController) {
    TopLevel {
        NavigationBarFinal1 {
            Segment1(Modifier.rowWeight(1.0f)) {
                IconContainer1a {
                    StateLayer1a {
                        CustomIcon1a(navController)
                    }
                }
                LabelText1a(Modifier.rowWeight(1.0f))
            }
            // Add the rest of the nav icons later same layout but need to space the icons evenly, should be automatic as added
        }
    }
}

// imported icon from figma project, need to import the rest here in the same manner
@Composable
fun CustomIcon1a(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isEmployeePage = currentRoute == "employee1"

    Icon(
        imageVector = Icons.Default.AccountCircle,
        contentDescription = "Employees",
        modifier = Modifier
            .rowWeight(1.0f)
            .columnWeight(1.0f)
            .clip(CircleShape)
            .clickable(
                // will disable when page is reached
                // has ripple effect
                enabled = !isEmployeePage,
            ) {
                // might need to clean this up sorry
                if (!isEmployeePage) {
                    navController.navigate("employee1")
                }
            },
        //disable the button once your on the page
        tint = if (isEmployeePage) Color.Gray else Color.Unspecified
    )
}