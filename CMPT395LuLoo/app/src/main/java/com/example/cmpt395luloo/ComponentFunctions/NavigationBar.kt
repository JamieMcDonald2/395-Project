/**
 * navigation bar v1.5
 *
 * - new focused/selected state for icons (imported from figma)
 */

package com.example.cmpt395luloo.ComponentFunctions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cmpt395luloo.navigationbarfinal.Icon1aProperty1Default
import com.example.cmpt395luloo.navigationbarfinal.Icon1aProperty1Variant2
import com.example.cmpt395luloo.navigationbarfinal.Icon2aProperty1Variant2
import com.example.cmpt395luloo.navigationbarfinal.IconContainer1aProperty1Default
import com.example.cmpt395luloo.navigationbarfinal.IconContainer1bProperty1Default
import com.example.cmpt395luloo.navigationbarfinal.IconContainer1cProperty1Default
import com.example.cmpt395luloo.navigationbarfinal.IconContainer2aProperty1Variant2
import com.example.cmpt395luloo.navigationbarfinal.IconsAccountCircleFilled24pxProperty1Default
import com.example.cmpt395luloo.navigationbarfinal.IconsAccountCircleFilled24pxProperty1Variant2
import com.example.cmpt395luloo.navigationbarfinal.LabelText1aProperty1Default
import com.example.cmpt395luloo.navigationbarfinal.LabelText1aProperty1Variant2
import com.example.cmpt395luloo.navigationbarfinal.LabelText1bProperty1Default
import com.example.cmpt395luloo.navigationbarfinal.LabelText1cProperty1Default
import com.example.cmpt395luloo.navigationbarfinal.NavigationBarFinal1Property1Default
import com.example.cmpt395luloo.navigationbarfinal.Segment1Property1Default
import com.example.cmpt395luloo.navigationbarfinal.Segment1Property1Variant2
import com.example.cmpt395luloo.navigationbarfinal.Segment2Property1Default
import com.example.cmpt395luloo.navigationbarfinal.Segment3Property1Default
import com.example.cmpt395luloo.navigationbarfinal.StateLayer1aProperty1Default
import com.example.cmpt395luloo.navigationbarfinal.StateLayer1bProperty1Default
import com.example.cmpt395luloo.navigationbarfinal.StateLayer1cProperty1Default
import com.example.cmpt395luloo.navigationbarfinal.StateLayer2aProperty1Variant2
import com.example.cmpt395luloo.navigationbarfinal.TopLevelProperty1Default
import com.google.relay.compose.BoxScopeInstance.columnWeight
import com.google.relay.compose.BoxScopeInstance.rowWeight

@Composable
fun NavigationBar(navController: NavController) {
    TopLevelProperty1Default {
        NavigationBarFinal1Property1Default {
            Segment1Property1Default(Modifier.rowWeight(1.0f)) {
                    EmployeeIcon(navController)
            }
            Segment2Property1Default(Modifier.rowWeight(1.0f)) {
                IconContainer1bProperty1Default {
                    StateLayer1bProperty1Default {
                        CustomIcon1b(navController)

                    }
                }
                LabelText1bProperty1Default(Modifier.rowWeight(1.0f))
                // Add the rest of the nav icons later same layout but need to space the icons evenly, should be automatic as added
            }
            Segment3Property1Default(Modifier.rowWeight(1.0f)) {
                IconContainer1cProperty1Default {
                    StateLayer1cProperty1Default {
                        CustomIcon1c(navController)

                    }
                }
                LabelText1cProperty1Default(Modifier.rowWeight(1.0f))
            }
        }
    }
}

// Buttons have multiple states
@Composable
fun EmployeeIcon(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute == "employee1") {
        Box(
            modifier = Modifier.clickable { /* No action needed if already on the page */ }
        ) {
            Segment1Property1Variant2(modifier = Modifier) {
                IconContainer2aProperty1Variant2 {
                    StateLayer2aProperty1Variant2 {
                        Icon2aProperty1Variant2 {
                            IconsAccountCircleFilled24pxProperty1Variant2 {
                                Icon1aProperty1Variant2()
                            }
                        }
                    }
                }
                LabelText1aProperty1Variant2()
            }
        }
    } else {
        Box(
            modifier = Modifier.clickable { navController.navigate("employee1") }
        ) {
            Segment1Property1Default(modifier = Modifier) {
                IconContainer1aProperty1Default {
                    StateLayer1aProperty1Default {
                        Icon1aProperty1Default {
                            IconsAccountCircleFilled24pxProperty1Default {
                                Icon1aProperty1Default()
                            }
                        }
                    }
                }
                LabelText1aProperty1Default()
            }
        }
    }
}

@Composable
fun CustomIcon1b(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isSchedulePage = currentRoute == "schedule1"

    Icon(
        imageVector = Icons.Default.DateRange,
        contentDescription = "Scheduling",
        modifier = Modifier
            .rowWeight(1.0f)
            .columnWeight(1.0f)
            .clip(CircleShape)
            .clickable(
                // will disable when page is reached but label doesn't disable needs work
                // has ripple effect but only default, doesnt cover label, looks wrong
                enabled = !isSchedulePage,
            ) {
                // might need to clean this up sorry
                if (!isSchedulePage) {
                    navController.navigate("schedule1")
                }
            },
        //disable the button once your on the page
        tint = if (isSchedulePage) Color.Gray else Color.Unspecified
    )
}

@Composable
fun CustomIcon1c(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isSettingPage = currentRoute == "setting1"

    Icon(
        imageVector = Icons.Default.Settings,
        contentDescription = "Settings",
        modifier = Modifier
            .rowWeight(1.0f)
            .columnWeight(1.0f)
            .clip(CircleShape)
            .clickable(
                // will disable when page is reached but label doesn't disable needs work
                // has ripple effect but only default, doesnt cover label, looks wrong
                enabled = !isSettingPage,
            ) {
                // might need to clean this up sorry
                if (!isSettingPage) {
                    navController.navigate("setting1")
                }
            },
        //disable the button once your on the page
        tint = if (isSettingPage) Color.Gray else Color.Unspecified
    )
}

/**
 * Get rid of this later, retained for now during testing in case we need to revert to these
 */
//@Composable
//fun FocusedIcon2a(navController: NavController, isEmployeePage: Boolean) {
//    val myIcon = MyCustomIcon() // replace 'MyCustomIcon' with your actual Composable function name
//
//    Icon(
//        painter = myIcon,
//        contentDescription = "Employees",
//        modifier = Modifier
//            .rowWeight(1.0f)
//            .columnWeight(1.0f)
//            .clip(CircleShape)
//            .clickable(
//                enabled = !isEmployeePage,
//            ) {
//                if (!isEmployeePage) {
//                    navController.navigate("employee1")
//                }
//            },
//        tint = if (isEmployeePage) Color.Gray else Color.Unspecified
//    )
//}

/**
 * Probably get rid of this custom button/icon
 */
//@Composable
//fun CustomIcon1a(navController: NavController, isEmployeePage) {
//    Icon(
//        imageVector = Icons.Default.AccountCircle,
//        contentDescription = "Employees",
//        modifier = Modifier
//            .rowWeight(1.0f)
//            .columnWeight(1.0f)
//            .clip(CircleShape)
//            .clickable(
//                // will disable when page is reached but label doesn't disable needs work
//                // has ripple effect but only default, doesnt cover label, looks wrong
//                enabled = !isEmployeePage,
//            ) {
//                // might need to clean this up sorry
//                if (!isEmployeePage) {
//                    navController.navigate("employee1")
//                }
//            },
//        //disable the button once your on the page
//        tint = if (isEmployeePage) Color.Gray else Color.Unspecified
//    )
//}
