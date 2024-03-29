package com.example.cmpt395aurora.ComponentFunctions

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.window.Dialog

@Composable
fun Overlay(showDialog: MutableState<Boolean>) {
    Button(onClick = { showDialog.value = true }) {
        Text("Show Overlay")
    }

    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            AlertDialog(
                title = { Text("Hello from overlay!") },
                onDismissRequest = { showDialog.value = false },
                confirmButton = { Button(onClick = { showDialog.value = false }) { Text("Close") } }
            )
        }
    }
}







