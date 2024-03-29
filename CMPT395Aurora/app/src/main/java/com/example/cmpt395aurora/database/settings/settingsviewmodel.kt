/**
 * Setting view model 1.0
 */

package com.example.cmpt395aurora.database.settings

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.cmpt395aurora.database.DatabaseHelper

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    val username = mutableStateOf("")
    val input = mutableStateOf("") // new MutableState for the text field input
    private val dbHelper = DatabaseHelper(application)

    init {
        // Fetch the username from the database when the ViewModel is initialized
        username.value = dbHelper.getUsername()
        input.value = username.value // initialize the input with the username
//        Log.d("SettingsViewModel", "Initialized username: ${username.value}") // test log

        // Create an instance of Settings and print its values
        val settings = Settings(username = username.value)
//        Log.d("SettingsViewModel", "Settings username: ${settings.username}")
    }

    fun updateUsername() {
        username.value = input.value // update the username with the input
        // update the username in your database
        dbHelper.updateUsername(input.value)

        // Create an instance of Settings and print its values
        val settings = Settings(username = username.value)
//        Log.d("SettingsViewModel", "Updated Settings username: ${settings.username}")
    }
}




