/**
 *   Main page
 *   v1.02
 *      - to implement weekly calendar from prototype would like to test implementing complicated
 *        figma components, if i have time, but it is not necessary at all - Jamie
 *
 *        - https://developer.android.com/jetpack/compose/navigation
 *        - https://developer.android.com/codelabs/jetpack-compose-navigation
 *        - https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation
 *        - https://saurabhjadhavblogs.com/ultimate-guide-to-jetpack-compose-navigation
 *        - https://developer.android.com/jetpack/compose/components/scaffold
 */

package com.example.cmpt395aurora.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.cmpt395aurora.ComponentFunctions.NavigationBar
import com.example.cmpt395aurora.ComponentFunctions.TopBar
import com.example.cmpt395aurora.Navigation
import com.example.cmpt395aurora.ui.theme.CMPT395AuroraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CMPT395AuroraTheme {
                // Create a NavController
                val navController = rememberNavController()

                Scaffold(
                    topBar = { TopBar(navController) },
                    bottomBar = { NavigationBar(navController) }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        Navigation(navController)
                    }
                }
            }
        }
    }
}

