/**
 *   Navigation Graph v1.6
 *
 *   Animated Navigation Host is experimental
 *   can change these transitions to make our app look better
 *
 *   - 1.6 - new logic for topbar text field
 *   - 1.5 - new settings updates
 *   - 1.4 - navcontroller updates for database
 *   - 1.3 - Jamie added add employee screen
 *   - 1.2 - Grant added screens
 */

@file:Suppress("DEPRECATION")

package com.example.cmpt395solaris

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cmpt395solaris.database.SharedViewModel
import com.example.cmpt395solaris.database.TopBarViewModel
import com.example.cmpt395solaris.database.availability.EmpAvailabilityViewModel
import com.example.cmpt395solaris.database.employees.EmployeeViewModel
import com.example.cmpt395solaris.database.settings.SettingsViewModel
import com.example.cmpt395solaris.screens.AddEmployeeScreen2
import com.example.cmpt395solaris.screens.EditEmployeeInfoScreen
import com.example.cmpt395solaris.screens.EmployeeAvailabilityScreen
import com.example.cmpt395solaris.screens.EmployeeMain
import com.example.cmpt395solaris.screens.HomeScreen
import com.example.cmpt395solaris.screens.ScheduleMain
import com.example.cmpt395solaris.screens.ScheduleWeekDay
import com.example.cmpt395solaris.screens.ScheduleWeekEnd
import com.example.cmpt395solaris.screens.SettingsMain
import com.google.accompanist.navigation.animation.AnimatedNavHost


/**
 * some imported packages are obsolete but needed
 */
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    navController: NavHostController,
    employeeViewModel: EmployeeViewModel,
    topBarViewModel: TopBarViewModel,
    settingsViewModel: SettingsViewModel,
    sharedViewModel: SharedViewModel,
    availabilityViewModel: EmpAvailabilityViewModel) {
    AnimatedNavHost(navController, startDestination = "home") {
        //home screen
        composable(
            "home",
            exitTransition = {
                // custom transitions
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(500)
                )
            }) {
            HomeScreen(navController, topBarViewModel, settingsViewModel)
        }
        //employee main screen
        composable(
            "employee1",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(500)
                )
            }) {
            EmployeeMain(navController, employeeViewModel, topBarViewModel)
        }
        //add employee screen
        composable(
            "employee2",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(500)
                )
            }) {
            AddEmployeeScreen2(navController ,employeeViewModel, topBarViewModel)
        }
        // edit employee info screen - does not transition properly - known issue with android studio
        composable(
            "employee3/{id}",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -1000 },
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 1000 },
                    animationSpec = tween(500)
                )
            }
        )
        {
            val arguments = navController.currentBackStackEntry?.arguments
            var empID = arguments?.getString("id")
            if(empID == "{id}"){
                empID = employeeViewModel.id.intValue.toString()
            }
            if (empID != null) {
                EditEmployeeInfoScreen(navController, employeeViewModel, topBarViewModel, empID)
            } else {
                // need to change this to snackbar probably
                Text("Error: No employee ID provided")
            }
        }
        composable(
            "Availability1/{id}",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(500)
                )
            }) {
            val arguments = navController.currentBackStackEntry?.arguments
            var empID = arguments?.getString("id")
            if(empID == "{id}"){
                empID = employeeViewModel.id.intValue.toString()
            }
            if (empID != null) {
                EmployeeAvailabilityScreen(
                    navController = navController,
                    viewModel = employeeViewModel,
                    topBarViewModel = topBarViewModel,
                    id = empID!!,
                    availabilityViewModel = availabilityViewModel
                )
            } else {
                // need to change this to snackbar probably
                Text("Error: No employee ID provided")
            }

        }
        //schedule main
        composable(
            "schedule1",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(500)
                )
            }) {
            ScheduleMain(navController, topBarViewModel)
        }
        //settings page
        composable(
            "setting1",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(500)
                )
            }) {
            SettingsMain(settingsViewModel, navController, topBarViewModel)
        }
        //schedule employee page
        composable(
            route = "schedule2/{date}",
            arguments = listOf(navArgument("date") { type = NavType.StringType }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(500)
                )
            }
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")
            ScheduleWeekDay(date, employeeViewModel)
        }

        //schedule employee page
        composable(
            route = "schedule3/{date}",
            arguments = listOf(navArgument("date") { type = NavType.StringType }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(500)
                )
            }
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")
            ScheduleWeekEnd(date, employeeViewModel, navController, sharedViewModel)
        }
    }
}






