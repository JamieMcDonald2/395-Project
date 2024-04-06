/**
 *   Settings Main page
 *   v1.03
 *
 *   Refs:
 *   - https://kotlinlang.org/docs/coroutines-overview.html
 *   - https://developer.android.com/kotlin/coroutines
 *   - https://kotlinlang.org/docs/coroutines-and-channels.html
 *
 *   - https://developer.android.com/jetpack/compose/components/scaffold
 *   - https://www.jetpackcompose.net/scaffold
 *
 *   - https://developer.android.com/jetpack/compose/touch-input/pointer-input
 *   - https://developer.android.com/jetpack/compose/touch-input/pointer-input/understand-gestures
 *   - https://www.composables.com/compose-ui/pointerinput
 *
 *   1.03
 *   - added verification and text field logic to conform to the standards set in rest of app
 *
 *   1.02
 *   - new scaffold to hold confirmation button and snackbar, pass values to database, retain
 *     values in text fields until changed, using mix of material 2 and material 3
 *
 *   1.01
 *   - added manager name field/user name field, removed placeholders
 */

package com.example.cmpt395solaris.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cmpt395solaris.ComponentFunctions.CustomSnackbar
import com.example.cmpt395solaris.ComponentFunctions.GenericTextField
import com.example.cmpt395solaris.database.TopBarViewModel
import com.example.cmpt395solaris.database.settings.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun SettingsMain(viewModel: SettingsViewModel, navController:NavController, topBarViewModel: TopBarViewModel) {

    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(key1 = currentRoute) {
        if (currentRoute == "setting1") {
            topBarViewModel.updateTopBarText("Settings")
        }
    }

    val isError = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    // Temporary variable to hold the text field value, initialized with currently saved username
    val tempInput = remember { mutableStateOf(viewModel.input.value) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GenericTextField(
            text = tempInput,
            isError = isError,
            label = "Username",
            placeholder = "Enter your username",
            onFocusChange = { isFocused -> /* handle focus change */ }
        ) { newValue ->
            tempInput.value = newValue
            // Update the hasChanges value in the topBarViewModel
            topBarViewModel.setHasChanges(newValue != viewModel.input.value)
        }

        Button(
            onClick = {
                // Only update viewModel.input.value when 'confirm' is clicked
                if (tempInput.value.isNotBlank()) {
                    if (tempInput.value != viewModel.input.value) {
                        viewModel.input.value = tempInput.value
                        viewModel.updateUsername()
                        // Show a Snackbar for confirmation
                        scope.launch {
                            snackbarHostState.showSnackbar("Settings saved")
                        }
                        // Reset the hasChanges value in the topBarViewModel
                        topBarViewModel.setHasChanges(false)
                    } else {
                        // Show a Snackbar if no changes were made
                        scope.launch {
                            snackbarHostState.showSnackbar("No changes were made")
                        }
                    }
                } else {
                    // Show a Snackbar if the field is empty
                    scope.launch {
                        snackbarHostState.showSnackbar("Username field cannot be empty")
                    }
                }
                focusManager.clearFocus()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
        ) {
            Text("Confirm")
        }
    }
    CustomSnackbar(snackbarHostState = snackbarHostState)
}












