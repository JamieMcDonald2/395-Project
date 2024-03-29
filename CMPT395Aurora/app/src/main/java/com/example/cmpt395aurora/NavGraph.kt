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

package com.example.cmpt395aurora

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
import com.example.cmpt395aurora.database.TopBarViewModel
import androidx.navigation.navArgument
import com.example.cmpt395aurora.database.employees.EmployeeViewModel
import com.example.cmpt395aurora.database.settings.SettingsViewModel
import com.example.cmpt395aurora.screens.AddEmployeeScreen
import com.example.cmpt395aurora.screens.EditEmployeeInfoScreen
import com.example.cmpt395aurora.screens.EmployeeMain
import com.example.cmpt395aurora.screens.HomeScreen
import com.example.cmpt395aurora.screens.ScheduleWeekDay
import com.example.cmpt395aurora.screens.ScheduleMain
import com.example.cmpt395aurora.screens.ScheduleWeekDay
import com.example.cmpt395aurora.screens.ScheduleWeekEnd
import com.example.cmpt395aurora.screens.SettingsMain
import com.google.accompanist.navigation.animation.AnimatedNavHost

/**
 * some imported packages are obsolete but needed
 */
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Navigation(navController: NavHostController, employeeViewModel: EmployeeViewModel, topBarViewModel: TopBarViewModel, settingsViewModel: SettingsViewModel) {
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
            EmployeeMain(navController, employeeViewModel)
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
            AddEmployeeScreen(employeeViewModel)
        }
        //employee info screen - does not transition properly - known issue with android studio
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
            val empID = arguments?.getString("id")
            if (empID != null) {
                EditEmployeeInfoScreen(navController, employeeViewModel, topBarViewModel, empID)
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
            ScheduleMain(navController)
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
            SettingsMain(settingsViewModel)
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
            ScheduleWeekEnd(date, employeeViewModel)
        }
    }
}






