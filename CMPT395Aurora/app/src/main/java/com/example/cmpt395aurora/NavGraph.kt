/**
 *   Navigation Graph v1.5
 *
 *   Animated Navigation Host is experimental
 *   can change these transitions to make our app look better
 *
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
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.cmpt395aurora.database.employees.EmployeeViewModel
import com.example.cmpt395aurora.database.settings.SettingsViewModel
import com.example.cmpt395aurora.screens.AddEmployeeScreen
import com.example.cmpt395aurora.screens.EmployeeInfoScreen
import com.example.cmpt395aurora.screens.EmployeeMain
import com.example.cmpt395aurora.screens.HomeScreen
import com.example.cmpt395aurora.screens.ScheduleEmployeeScreen
import com.example.cmpt395aurora.screens.ScheduleMain
import com.example.cmpt395aurora.screens.SettingsMain
import com.google.accompanist.navigation.animation.AnimatedNavHost

/**
 * some imported packages are obsolete but needed
 */
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Navigation(navController: NavHostController, employeeViewModel: EmployeeViewModel, settingsViewModel: SettingsViewModel) {
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
            HomeScreen(navController)
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
            "employee3/{employeeId}",
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
            val empID = arguments?.getString("employeeId")
            if (empID != null) {
                EmployeeInfoScreen(navController, employeeViewModel, empID)
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
            "schedule2",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(500)
                )
            }) {
            ScheduleEmployeeScreen()
        }
    }
}






