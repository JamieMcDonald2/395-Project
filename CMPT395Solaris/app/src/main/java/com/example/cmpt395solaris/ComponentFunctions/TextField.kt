/**
 *  Text Field v1.04
 *
 *  - https://m3.material.io/components/text-fields/guidelines
 *  - https://developer.android.com/jetpack/compose/text/user-input
 *
 *  v1.04:
 *      - focus logic so that changes are made, if changes are made, whether field is selected
 *        or not
 *
 *  v1.02:
 *      - ability to update value change of entry
 *
 *  v1.01:
 *      - padding/spacing/size
 */

package com.example.cmpt395solaris.ComponentFunctions

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericTextField(
    text: MutableState<String>,
    isError: MutableState<Boolean>,
    label: String,
    placeholder: String,
    onFocusChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val isFocused = remember { mutableStateOf(false) }
    val textFieldValue = remember { mutableStateOf(text.value) } // Local state for the text field

    OutlinedTextField(
        value = textFieldValue.value,
        onValueChange = { newText ->
            Log.d("TextField", "New text: $newText")   // testing
            textFieldValue.value = newText
            onValueChange(newText) // Call onValueChange every time the text changes
        },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        isError = isError.value,
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f), // Disabled alpha
            cursorColor = MaterialTheme.colorScheme.primary,
            errorBorderColor = MaterialTheme.colorScheme.error,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) // Medium alpha
        ),
        trailingIcon = {
            if (textFieldValue.value.isNotEmpty()) {
                IconButton(onClick = {
                    textFieldValue.value = ""
                    onValueChange(textFieldValue.value) // Trigger the validation check
                }) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear text")
                }
            }
        },
        modifier = Modifier
            .focusRequester(focusRequester)
            .onFocusChanged { state ->
                isFocused.value = state.isFocused
                onFocusChange(state.isFocused)
            }
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally) // Center
            .width(width = 300.dp)
    )
}

