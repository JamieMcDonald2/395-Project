/**
 *   Main page v1.07
 *
 *        - https://developer.android.com/jetpack/compose/navigation
 *        - https://developer.android.com/codelabs/jetpack-compose-navigation
 *        - https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation
 *        - https://saurabhjadhavblogs.com/ultimate-guide-to-jetpack-compose-navigation
 *        - https://developer.android.com/jetpack/compose/components/scaffold
 *        - https://developer.android.com/reference/android/graphics/Paint
 *
 *   v1.07
 *      - New logic for top bar / changing text displayed depending on page you are on (settings view model)
 *
 *   v1.06
 *      - Database logging for testing
 *
 *   v1.05
 *      - added settings view model for database values to settings page
 *
 *   v1.04
 *      - added seed logic, for testing
 */

package com.example.cmpt395solaris.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.cmpt395solaris.ComponentFunctions.NavigationBar
import com.example.cmpt395solaris.ComponentFunctions.TopBar
import com.example.cmpt395solaris.Navigation
import com.example.cmpt395solaris.database.DatabaseHelper
import com.example.cmpt395solaris.database.SharedViewModel
import com.example.cmpt395solaris.database.TopBarViewModel
import com.example.cmpt395solaris.database.availability.EmpAvailabilityViewModel
import com.example.cmpt395solaris.database.employees.EmployeeViewModel
import com.example.cmpt395solaris.database.settings.SettingsViewModel
import com.example.cmpt395solaris.ui.theme.CMPT395SolarisTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Contains database seeder we need to remove later!
 */

class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CMPT395SolarisTheme {
                // Create a NavController
                val navController = rememberNavController()
                val employeeViewModel: EmployeeViewModel = viewModel()

                // Create an instance of TopBarViewModel
                val topBarViewModel: TopBarViewModel = viewModel()

                // Create an instance of SettingsViewModel
                val settingsViewModel: SettingsViewModel = viewModel()

                val scheduleViewModel: SharedViewModel = viewModel()

                val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                val availabilityViewModel: EmpAvailabilityViewModel = viewModel()

                Scaffold(
                    topBar = { TopBar(navController, topBarViewModel) },
                    bottomBar = { NavigationBar(navController) }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        //ScheduleWeekEnd(date = currentDate, employeeViewModel, navController, sharedViewModel = sharedViewModel)
                        Navigation(navController, employeeViewModel, topBarViewModel, settingsViewModel, scheduleViewModel, availabilityViewModel)
                    }
                }
                // remove this when testing is over
                dbHelper = DatabaseHelper(this)
                dbHelper.logDatabaseTables()
            }
        }
        // get rid of this!
//        seedDatabase(this) // remove this when testing is over
    }
}





