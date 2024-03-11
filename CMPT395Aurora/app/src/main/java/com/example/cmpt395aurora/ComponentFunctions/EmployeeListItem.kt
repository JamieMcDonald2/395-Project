/**
 * Employee List Item Component
 * v1.01
 *
 * - added loop for iterating over database and UI elements from figma component
 *  - https://developer.android.com/jetpack/compose/lists
 *
 *  v1.01
 *  - added proper dividers, initials to monogram, color to list items
 */

package com.example.cmpt395aurora.ComponentFunctions

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cmpt395aurora.database.employees.Employee
import com.example.cmpt395aurora.listitememployee.BuildingBlocksMonogram
import com.example.cmpt395aurora.listitememployee.Divider1
import com.example.cmpt395aurora.listitememployee.Icon
import com.example.cmpt395aurora.listitememployee.Icons1
import com.example.cmpt395aurora.listitememployee.Icons1a
import com.google.relay.compose.ColumnScopeInstanceImpl.weight
import com.google.relay.compose.RelayText

@Composable
fun EmployeeListItem(employee: Employee) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 64.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(0.5f),
            contentAlignment = Alignment.Center
        ) {
            EmployeeMonogram(employee)
        }

        Column(
            modifier = Modifier.weight(2f)
        ) {
            RelayText(
                content = employee.position,
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                fontFamily = MaterialTheme.typography.labelMedium.fontFamily,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                height = MaterialTheme.typography.labelMedium.lineHeight,
                letterSpacing = MaterialTheme.typography.labelMedium.letterSpacing,
                textAlign = TextAlign.Left,
                fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                maxLines = -1,
                modifier = Modifier.fillMaxWidth(1.0f).wrapContentHeight(
                    align = Alignment.CenterVertically,
                    unbounded = true
                )
            )
            RelayText(
                content = employee.fname,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                color = MaterialTheme.colorScheme.onSurface,
                height = MaterialTheme.typography.bodyLarge.lineHeight,
                letterSpacing = MaterialTheme.typography.bodyLarge.letterSpacing,
                textAlign = TextAlign.Left,
                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                maxLines = -1,
                modifier = Modifier.fillMaxWidth(1.0f).wrapContentHeight(
                    align = Alignment.CenterVertically,
                    unbounded = true
                )
            )
        }

        Row(modifier = Modifier.weight(0.3f)) {
            Icons1 {
                Icons1a {
                    Icon()
                }
            }
        }
    }
    ListItemDivider()
}

@Composable
fun EmployeeMonogram(employee: Employee) {
    Box(
        modifier = Modifier.weight(0.5f),
        contentAlignment = Alignment.Center
    ) {
        BuildingBlocksMonogram {}
        Text(
            text = employee.fname.first().toString(),
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

