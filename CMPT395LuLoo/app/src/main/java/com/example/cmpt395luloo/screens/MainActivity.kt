/**
 *   Main page
 *   v1.00
 *      - to implement weekly calendar from prototype would like to test implementing complicated
 *        figma components, if i have time, but it is not necessary at all - Jamie
 */

package com.example.cmpt395luloo.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.cmpt395luloo.ComponentFunctions.NavigationBar
import com.example.cmpt395luloo.ComponentFunctions.TopBar
import com.example.cmpt395luloo.Navigation
import com.example.cmpt395luloo.ui.theme.CMPT395LuLooTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CMPT395LuLooTheme {
                // Create a NavController
                val navController = rememberNavController()
                Navigation(navController)

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                    // more stuff here ? probably, maybe above too though still working out
                    // but this is essentially how everything is added!
                ) {
                    // add the top bar for the entire app
                    TopBar(navController)
                    Spacer(modifier = Modifier.weight(1f))
                    // add the navigation bar for the entire app
                    NavigationBar(navController)
                }
            }
        }
    }
}

