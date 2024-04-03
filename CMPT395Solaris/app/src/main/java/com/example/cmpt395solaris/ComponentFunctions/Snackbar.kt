/**
 * Snackbar v1.01
 *
 *  - https://developer.android.com/jetpack/compose/components/snackbar
 *
 *  v1.01
 *  - added dismiss action
 */

package com.example.cmpt395solaris.ComponentFunctions

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.relay.compose.BoxScopeInstanceImpl.align
import com.google.relay.compose.ColumnScopeInstanceImpl.weight

@Composable
fun CustomSnackbar(snackbarHostState: SnackbarHostState) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.align(Alignment.BottomCenter)
    ) { data ->
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                TextButton(onClick = { snackbarHostState.currentSnackbarData?.dismiss() }) {
                    Text("Dismiss")
                }
            },
            content = {
                Text(data.visuals.message)
                Spacer(Modifier.weight(1f))
            }
        )
    }
}










