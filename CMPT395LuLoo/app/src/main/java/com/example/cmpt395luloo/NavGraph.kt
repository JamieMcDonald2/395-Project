/**
 *   Navigation Graph v1.0
 *
 *   Animated Navigation Host is experimental apparently?
 *   can change these transitions to make our app look cooler!
 */

@file:Suppress("DEPRECATION")

package com.example.cmpt395luloo

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.cmpt395luloo.screens.EmployeeMain
import com.example.cmpt395luloo.screens.HomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(navController: NavHostController) {
    AnimatedNavHost(navController, startDestination = "home") {
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
        composable(
            "employee1",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(500)
                )
            }) {
            EmployeeMain(navController)
        }
    }
}