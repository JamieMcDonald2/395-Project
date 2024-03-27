
/**
 *   Schedule Employee page
 *   v1.00
 *
 */

package com.example.cmpt395aurora.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ScheduleEmployeeScreen(date: String?) {
    val safeDate = date ?: "No date selected"
    var selectedValue1 by remember { mutableStateOf("Select Employee") }
    var selectedValue2 by remember { mutableStateOf("Select Employee") }
    var selectedValue3 by remember { mutableStateOf("Select Employee") }
    var selectedValue4 by remember { mutableStateOf("Select Employee") }

    // Options for the dropdown menus (later to be populated by employees)
    val options1 = listOf("Option 1", "Option 2", "Option 3")
    val options2 = listOf("Option A", "Option B", "Option C")
    val options3 = listOf("Option D", "Option E", "Option F")
    val options4 = listOf("Option 5", "Option 6", "Option 7")

    // State variables to manage expansion of dropdown menus
    var isExpanded1 by remember { mutableStateOf(false) }
    var isExpanded2 by remember { mutableStateOf(false) }
    var isExpanded3 by remember { mutableStateOf(false) }
    var isExpanded4 by remember { mutableStateOf(false) }

    // Column for displaying the selected date
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally // Centering horizontally
    ) {
        Text(
            text = safeDate,
            style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
        )
    }

    // Adding space below the date
    Spacer(modifier = Modifier.height(20.dp))

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp), // Added padding to move LazyColumn down
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Text(
                text = "Morning Shift",
                modifier = Modifier.padding(start = 15.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )

            // Adding padding between Morning shift text and drop downs
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.padding(horizontal = 15.dp), // Adding horizontal padding
                horizontalAlignment = Alignment.Start // Aligning to the start horizontally
            ) {
                // Dropdown 1
                DropdownMenu(
                    selectedValue1,
                    isExpanded1,
                    options1,
                    { selectedValue1 = it },
                    { isExpanded1 = !isExpanded1 }
                )

                // Dropdown 2
                DropdownMenu(
                    selectedValue2,
                    isExpanded2,
                    options2,
                    { selectedValue2 = it },
                    { isExpanded2 = !isExpanded2 }
                )
            }

            // Adding space between morning and afternoon shifts
            Spacer(modifier = Modifier.height(20.dp))


            Text(
                text = "Afternoon Shift",
                modifier = Modifier.padding(start = 15.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )

            // Adding space between morning and afternoon shifts
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.padding(horizontal = 15.dp), // Adding horizontal padding
                horizontalAlignment = Alignment.Start // Aligning to the start horizontally
            ) {
                // Dropdown 3
                DropdownMenu(
                    selectedValue3,
                    isExpanded3,
                    options3,
                    { selectedValue3 = it },
                    { isExpanded3 = !isExpanded3 }
                )

                // Dropdown 4
                DropdownMenu(
                    selectedValue4,
                    isExpanded4,
                    options4,
                    { selectedValue4 = it },
                    { isExpanded4 = !isExpanded4 }
                )
            }
        }
    }
}

/**
 * Composable function to display a dropdown menu.
 *
 * @param selectedValue The currently selected value in the dropdown.
 * @param isExpanded Whether the dropdown is expanded or not.
 * @param options The list of options for the dropdown. (This will later be populated with
 * employees from the database)
 * @param onItemSelected Callback function to handle selection of an item.
 * @param onDropdownClicked Callback function to handle dropdown click.
 */
@Composable
fun DropdownMenu(
    selectedValue: String,
    isExpanded: Boolean,
    options: List<String>,
    onItemSelected: (String) -> Unit,
    onDropdownClicked: () -> Unit
) {
    // State variable to manage border size for the icon (optional)
    var iconBorder by remember { mutableStateOf(0.dp) }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onDropdownClicked() }
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.White)
                .border(1.dp, Color.Gray)
        ) {
            Text(
                text = selectedValue,
                modifier = Modifier.weight(1f).padding(start = 16.dp)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Icon",
                modifier = Modifier.padding(16.dp)
                //.border(iconBorder, Color.Black) This is optional, I don't know if I like it
            )
        }

        // If dropdown is clicked, display the list of options
        if (isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                options.forEach { option ->
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                            .border(1.dp, Color.Gray)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable {
                                onItemSelected(option)
                                onDropdownClicked()
                            }
                    ) {
                        Text(
                            text = option,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}



