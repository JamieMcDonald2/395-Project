/**
 *   Settings Main page
 *   v1.02
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
 *   1.02
 *   - new scaffold to hold confirmation button and snackbar, pass values to database, retain
 *     values in text fields until changed, using mix of material 2 and material 3
 *
 *   1.01
 *   - added manager name field/user name field, removed placeholders
 */

package com.example.cmpt395aurora.screens

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.cmpt395aurora.ComponentFunctions.CustomSnackbar
import com.example.cmpt395aurora.ComponentFunctions.GenericTextField
import com.example.cmpt395aurora.database.settings.SettingsViewModel
import com.google.relay.compose.BoxScopeInstanceImpl.align
import kotlinx.coroutines.launch

@Composable
fun SettingsMain(viewModel: SettingsViewModel) {
    val isError = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

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
//    Log.d("SettingsMain", "Rendering with username: ${viewModel.username.value}") //test
    GenericTextField(
        text = viewModel.input,
        isError = isError,
        label = "Username",
        placeholder = "Enter your username",
        onFocusChange = { isFocused -> /* handle focus change */ }
    ) { newValue ->
        viewModel.input.value = newValue
    }
        // Add more fields here later

        Button(
            onClick = {
                viewModel.updateUsername()
                // Show a Snackbar for confirmation
                scope.launch {
                    snackbarHostState.showSnackbar("Settings saved")
                }
                focusManager.clearFocus()
            },
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        ) {
            Text("Confirm")
        }
    }
    CustomSnackbar(snackbarHostState = snackbarHostState)
}













