/**
 * Employee List Item Component v1.05
 *
 *  v1.05
 *  - new inactive color status for list items
 *
 *  v1.04
 *  - new logic to prevent unneccessary recompostion of overline item to increase performance
 *    in an attempt to resolve transition error from these items
 *
 *  v1.03
 *  - adjusted overline to line up with heading
 *  - added logic for various possible combinations of training or lack of to display text
 *
 *  v1.02
 *  - changed position field to phone number
 *
 *  - added loop for iterating over database and UI elements from figma component
 *  - https://developer.android.com/jetpack/compose/lists
 *
 *  v1.01
 *  - added proper dividers, initials to monogram, color to list items
 *
 */

package com.example.cmpt395aurora.ComponentFunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cmpt395aurora.database.employees.Employee
import com.example.cmpt395aurora.database.employees.EmployeeViewModel
import com.example.cmpt395aurora.listitememployee.BuildingBlocksMonogram
import com.example.cmpt395aurora.listitememployee.Divider1
import com.google.relay.compose.ColumnScopeInstanceImpl.weight
import com.google.relay.compose.RelayText

@Composable
fun EmployeeListItem(navController: NavController, employee: Employee, viewModel: EmployeeViewModel) {
    //new logic v1.04
    val overlineString = remember(employee) {
        if(employee.opening && employee.closing){
            "Opening/Closing"
        } else if(employee.opening) {
            "Opening"
        } else if(employee.closing) {
            "Closing"
        } else {
            ""
        }
    }

    // Determine the color based on the employee's active status
    val textColor = if (employee.isActive) MaterialTheme.colorScheme.onSurface else Color.Gray
    // Determine the color based on the employee's active status
    val VeryLightGray = Color(0xFFEEEEEE)
    val boxColor = if (employee.isActive) MaterialTheme.colorScheme.background else VeryLightGray

    Box(
        modifier = Modifier.clickable(
            onClick = {
                val employeeID = employee.id.toString()
                navController.navigate("employee3/$employeeID") // goes to employee info on click
            }
        )
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 64.dp)
                .background(boxColor),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(0.5f), contentAlignment = Alignment.Center
            ) {
                EmployeeMonogram(employee)
            }

            Column(
                modifier = Modifier.weight(2f)
            ) {
                RelayText(
                    content = overlineString,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                    fontFamily = MaterialTheme.typography.labelMedium.fontFamily,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    height = MaterialTheme.typography.labelMedium.lineHeight,
                    letterSpacing = MaterialTheme.typography.labelMedium.letterSpacing,
                    textAlign = TextAlign.Left,
                    fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                    maxLines = -1,
                    modifier = Modifier
                        .fillMaxWidth(1.0f)
                        .wrapContentHeight(
                            align = Alignment.CenterVertically,
                            unbounded = true
                        )
                )
                RelayText(
                    content = employee.fname + " " + employee.lname,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                    color = textColor, // Use the color here
                    height = MaterialTheme.typography.bodyLarge.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodyLarge.letterSpacing,
                    textAlign = TextAlign.Left,
                    fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                    maxLines = -1,
                    modifier = Modifier
                        .fillMaxWidth(1.0f)
                        .wrapContentHeight(
                            align = Alignment.CenterVertically,
                            unbounded = true
                        )
                )
            }
        }
    }
    ListItemDivider()
}

@Composable
fun EmployeeMonogram(employee: Employee) {
    // Determine the color based on the employee's active status
    val monogramColor = if (employee.isActive) MaterialTheme.colorScheme.onSurface else Color.Gray

    Box(
        modifier = Modifier.weight(0.5f),
        contentAlignment = Alignment.Center
    ) {
        BuildingBlocksMonogram {}
        Text(
            text = employee.fname.first().toString(),
            color = monogramColor, // Use the color here
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ListItemDivider() {
    Box(modifier = Modifier.background(Color.LightGray)) {
        Divider1(modifier = Modifier.padding(0.5.dp))
    }
}

