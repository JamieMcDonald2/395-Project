/**
 * Add Employee FAB v1.01
 *
 * - FAB button for adding employees
 *
 * 1.01
 * - Changed click/clip location in hierarchy to have ripple work correctly
 */

package com.example.cmpt395aurora.ComponentFunctions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.example.cmpt395aurora.addemployeefab.AddFAB
import com.example.cmpt395aurora.addemployeefab.Icon
import com.example.cmpt395aurora.addemployeefab.IconsAdd24px
import com.example.cmpt395aurora.addemployeefab.PlusIcon
import com.example.cmpt395aurora.addemployeefab.StateLayer
import com.example.cmpt395aurora.addemployeefab.TopLevel

@Composable
fun AddEmployeeFab(navController: NavController, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        TopLevel(modifier = modifier) {
            AddFAB {
                StateLayer(
                    modifier = Modifier
                        .clip(CircleShape) // Apply clip to the StateLayer for ripple
                        .clickable(
                            onClick = {
                                navController.navigate("employee2")
                            },
                        ) // Apply clickable to the StateLayer for ripple
                ) {
                    Icon {
                        IconsAdd24px(
                            modifier = Modifier
                                .rowWeight(1.0f)
                                .columnWeight(1.0f)
                        ) {
                            PlusIcon(
                                modifier = Modifier
                                    .rowWeight(1.0f)
                                    .columnWeight(1.0f)
                            )
                        }
                    }
                }
            }
        }
    }
}

    /**
     * Dummy Test FAB
     */

//fun AddEmployeeFab(modifier: Modifier = Modifier) {
//    TopLevel(modifier = modifier) {
//        AddFAB {
//            StateLayer {
//                Icon {
//                    IconsAdd24px(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)) {
//                        PlusIcon(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f))
//                    }
//                }
//            }
//        }
//    }
//}