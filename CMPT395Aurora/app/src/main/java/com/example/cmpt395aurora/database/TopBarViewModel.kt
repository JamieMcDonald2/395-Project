/**
 *  Top Bar View Model v1.0
 *
 *  - https://developer.android.com/develop/ui/compose/state
 *  - https://developer.android.com/topic/architecture/ui-layer/stateholders
 */

package com.example.cmpt395aurora.database

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TopBarViewModel : ViewModel() {
    val topBarText = mutableStateOf("Loading...")
    val hasChanges = mutableStateOf(false) // Add this line

    fun updateTopBarText(newText: String) {
        Log.d("TopBarViewModel", "updateTopBarText called with: $newText")
        topBarText.value = newText
    }

    // Add this function to update the hasChanges property
    fun setHasChanges(hasChanges: Boolean) {
        this.hasChanges.value = hasChanges
    }
}

