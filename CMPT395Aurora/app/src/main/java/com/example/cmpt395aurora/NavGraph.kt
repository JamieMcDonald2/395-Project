/**
 *   Navigation Graph v1.4
 *
 *   Animated Navigation Host is experimental apparently?
 *   can change these transitions to make our app look cooler!
 *
 *   - 1.2 - Grant added screens
 *   - 1.3 - Jamie added add employee screen
 *   - 1.4 - navcontroller updates for database
 */

// some imported packages are obsolete but needed
@file:Suppress("DEPRECATION")

package com.example.cmpt395aurora

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.cmpt395aurora.database.employees.EmployeeViewModel
import com.example.cmpt395aurora.screens.AddEmployeeScreen
import com.example.cmpt395aurora.screens.EmployeeInfoScreen
import com.example.cmpt395aurora.screens.EmployeeMain
import com.example.cmpt395aurora.screens.HomeScreen
import com.example.cmpt395aurora.screens.ScheduleEmployeeScreen
import com.example.cmpt395aurora.screens.ScheduleMain
import com.example.cmpt395aurora.screens.SettingsMain
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Navigation(navController: NavHostController, viewModel: EmployeeViewModel) {
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
            EmployeeMain(navController, viewModel)
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
            AddEmployeeScreen(viewModel)
        }
        //employee info screen
        composable(
            "employee3",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(500)
                )
            }) {
            EmployeeInfoScreen(viewModel)
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
            SettingsMain()
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