/**
 *  Employee Main page v1.05
 *
 *  1.05:
 *  - ability to sort by inactive/active as well as alphabetical
 *
 *  1.04:
 *  - FAB for adding employees (removed button from main screen)
 *  - Tooltip for FAB
 *    - https://m3.material.io/components/floating-action-button/guidelines
 *
 *  1.03:
 *  - added employees to UI list item and added loop for iterating over list of employees
 *  - moved list item to its own file "EmployeeListItem.kt" - will do same for searchbar later
 *  - "LazyList" for scrolling: https://developer.android.com/jetpack/compose/lists
 *  - alphabetical ordering
 *    - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/sorted-by.html
 *    - https://www.kotlinsos.com/community-libraries/how-to-use-sortby-in-kotlin/
 *
 *  1.02:
 *  - Began importing UI elements
 *  - https://developer.android.com/jetpack/compose/documentation
 *
 *  1.01:
 *  - added info from db test
 *  - https://developer.android.com/training/data-storage/sqlite
 *
*/

package com.example.cmpt395aurora.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cmpt395aurora.ComponentFunctions.AddEmployeeFab
import com.example.cmpt395aurora.ComponentFunctions.Divider
import com.example.cmpt395aurora.ComponentFunctions.EmployeeListItem
import com.example.cmpt395aurora.database.employees.EmployeeViewModel
import com.example.cmpt395aurora.searchbar.SearchBar

@Composable
fun EmployeeMain(navController: NavHostController, viewModel: EmployeeViewModel) {
    // This effect will run every time the composable is recomposed
    LaunchedEffect(Unit) {
        viewModel.refreshEmployees()
    }

    // Use the ViewModel to get the data
    val employees by viewModel.employees

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) { // Box to allow layering of composables
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, start =  0.dp, end = 0.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmployeeSearchBar()

            Spacer(modifier = Modifier.height(8.dp)) // space

            Divider()

            // Display our employees here with this loop over list of employees
            // new "LazyList" for scrolling!
            // Maybe make all inactive employees go to bottom?
            LazyColumn {
                items(employees.size) { index ->
                    EmployeeListItem(navController, employees[index], viewModel)
                }
            }
        }
        // Add the FAB here
        AddEmployeeFab(
            navController,
            modifier = Modifier
                .padding(16.dp) // Add some padding from the end and bottom
        )
    }
}

/**
 * wrapper so we can add functionality to the search bar -
 * will replace "SearchBar()" with it's sub-components and add modifier's - like the navbar icons
*/
@Composable
fun EmployeeSearchBar() {
    SearchBar(modifier = Modifier.scale(0.8f))
}

