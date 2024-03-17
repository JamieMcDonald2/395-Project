/**
 * navigation bar v2.0
 *
 * - focused/selected state for icons (imported from figma) (1.4)
 * - Removed native components in favor of full relay imports from previous versions (1.5)
 * - Added modifiers to adjust position of icons (1.6)
 * - Added modifiers to adjust size of navigation bar box (1.7)
 * - Removed imported Bar toplevel because of unalterable padding around icons, replaced
 *   with native nav bar and placed imported icons/states inside of it for more control (1.8)
 *
 * 2.0:
 *  - new column/box layout to have proper ripple/focused effects
 *  - Employee icon only in this version - need to do other two icons
 *
 * - Refs:
 *      - https://developer.android.com/jetpack/compose/modifiers
 *
 * 2.1:
 *  - padding/space adjustments for UX
 *  - added circular ripple to all buttons
 */

package com.example.cmpt395aurora.ComponentFunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cmpt395aurora.navigationbarfinal.Icon1aProperty1Default
import com.example.cmpt395aurora.navigationbarfinal.Icon1aProperty1Variant2
import com.example.cmpt395aurora.navigationbarfinal.Icon1bProperty1Default
import com.example.cmpt395aurora.navigationbarfinal.Icon1bProperty1Variant2
import com.example.cmpt395aurora.navigationbarfinal.Icon1cProperty1Default
import com.example.cmpt395aurora.navigationbarfinal.Icon1cProperty1Variant2
import com.example.cmpt395aurora.navigationbarfinal.Icon2aProperty1Variant2
import com.example.cmpt395aurora.navigationbarfinal.Icon2bProperty1Variant2
import com.example.cmpt395aurora.navigationbarfinal.Icon2cProperty1Variant2
import com.example.cmpt395aurora.navigationbarfinal.IconContainer1aProperty1Default
import com.example.cmpt395aurora.navigationbarfinal.IconContainer1bProperty1Default
import com.example.cmpt395aurora.navigationbarfinal.IconContainer1cProperty1Default
import com.example.cmpt395aurora.navigationbarfinal.IconContainer2aProperty1Variant2
import com.example.cmpt395aurora.navigationbarfinal.IconContainer2bProperty1Variant2
import com.example.cmpt395aurora.navigationbarfinal.IconContainer2cProperty1Variant2
import com.example.cmpt395aurora.navigationbarfinal.IconsAccountCircleFilled24pxProperty1Default
import com.example.cmpt395aurora.navigationbarfinal.IconsAccountCircleFilled24pxProperty1Variant2
import com.example.cmpt395aurora.navigationbarfinal.LabelText1aProperty1Default
import com.example.cmpt395aurora.navigationbarfinal.LabelText1aProperty1Variant2
import com.example.cmpt395aurora.navigationbarfinal.LabelText1bProperty1Default
import com.example.cmpt395aurora.navigationbarfinal.LabelText1bProperty1Variant2
import com.example.cmpt395aurora.navigationbarfinal.LabelText1cProperty1Default
import com.example.cmpt395aurora.navigationbarfinal.LabelText1cProperty1Variant2
import com.example.cmpt395aurora.navigationbarfinal.Segment1Property1Default
import com.example.cmpt395aurora.navigationbarfinal.Segment1Property1Variant2
import com.example.cmpt395aurora.navigationbarfinal.Segment2Property1Default
import com.example.cmpt395aurora.navigationbarfinal.Segment2Property1Variant2
import com.example.cmpt395aurora.navigationbarfinal.Segment3Property1Default
import com.example.cmpt395aurora.navigationbarfinal.Segment3Property1Variant2
import com.example.cmpt395aurora.navigationbarfinal.StateLayer1aProperty1Default
import com.example.cmpt395aurora.navigationbarfinal.StateLayer1bProperty1Default
import com.example.cmpt395aurora.navigationbarfinal.StateLayer1cProperty1Default
import com.example.cmpt395aurora.navigationbarfinal.StateLayer2aProperty1Variant2
import com.example.cmpt395aurora.navigationbarfinal.StateLayer2bProperty1Variant2
import com.example.cmpt395aurora.navigationbarfinal.StateLayer2cProperty1Variant2

@Composable
fun NavigationBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(MaterialTheme.colorScheme.inverseOnSurface),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        EmployeeIcon(navController)
        ScheduleIcon(navController)
        SettingIcon(navController)
    }
}

// Buttons have multiple states
@Composable
fun EmployeeIcon(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute == "employee1") {
        Box(
            modifier = Modifier
                .padding(0.dp) // Adjust padding as needed
                .size(65.dp) // Adjust size as needed
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .clickable { /* No action needed if already on the page */ }
                    .align(Alignment.TopCenter) // Align to center
            )
            Segment1Property1Variant2(modifier = Modifier.align(Alignment.Center)) { // Align to center
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
            modifier = Modifier
                .padding(0.dp) // Adjust padding if needed
                .size(65.dp) // Adjust size if needed

        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .clickable { navController.navigate("employee1") }
                    .align(Alignment.TopCenter) // tried to make it bigger no joy
            )
            Segment1Property1Default(modifier = Modifier.align(Alignment.TopCenter)) { // Align to center
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
fun ScheduleIcon(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute == "schedule1") {
        Box(
            modifier = Modifier
                .padding(0.dp) // Adjust padding as needed
                .size(65.dp) // Adjust size as needed
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .clickable { /* No action needed if already on the page */ }
                    .align(Alignment.TopCenter) // Align to center
            )
            Segment2Property1Variant2(modifier = Modifier) {
                IconContainer2bProperty1Variant2 {
                    StateLayer2bProperty1Variant2 {
                        Icon2bProperty1Variant2 {
                            IconsAccountCircleFilled24pxProperty1Variant2 {
                                Icon1bProperty1Variant2()
                            }
                        }
                    }
                }
                LabelText1bProperty1Variant2()
            }
        }
    } else {
        Box(
            modifier = Modifier
                .padding(0.dp) // Adjust padding if needed
                .size(65.dp) // Adjust size if needed

        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .clickable { navController.navigate("schedule1") }
                    .align(Alignment.TopCenter) // tried to make it bigger no joy
            )
            Segment2Property1Default(modifier = Modifier) {
                IconContainer1bProperty1Default {
                    StateLayer1bProperty1Default {
                        Icon1bProperty1Default {
                            IconsAccountCircleFilled24pxProperty1Default {
                                Icon1bProperty1Default()
                            }
                        }
                    }
                }
                LabelText1bProperty1Default()
            }
        }
    }
}

@Composable
fun SettingIcon(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute == "setting1") {
        Box(
            modifier = Modifier
                .padding(0.dp) // Adjust padding as needed
                .size(65.dp) // Adjust size as needed
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .clickable { /* No action needed if already on the page */ }
                    .align(Alignment.TopCenter) // Align to center
            )
            Segment3Property1Variant2(modifier = Modifier) {
                IconContainer2cProperty1Variant2 {
                    StateLayer2cProperty1Variant2 {
                        Icon2cProperty1Variant2 {
                            IconsAccountCircleFilled24pxProperty1Variant2 {
                                Icon1cProperty1Variant2()
                            }
                        }
                    }
                }
                LabelText1cProperty1Variant2()
            }
        }
    } else {
        Box(
            modifier = Modifier
                .padding(0.dp) // Adjust padding if needed
                .size(65.dp) // Adjust size if needed

        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .clickable { navController.navigate("setting1") }
                    .align(Alignment.TopCenter) // tried to make it bigger no joy
            )
            Segment3Property1Default(modifier = Modifier) {
                IconContainer1cProperty1Default {
                    StateLayer1cProperty1Default {
                        Icon1cProperty1Default {
                            IconsAccountCircleFilled24pxProperty1Default {
                                Icon1cProperty1Default()
                            }
                        }
                    }
                }
                LabelText1cProperty1Default()
            }
        }
    }
}