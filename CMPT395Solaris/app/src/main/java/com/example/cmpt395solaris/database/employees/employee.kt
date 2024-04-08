/**
 * Employee Data Class v1.1
 *
 *  v1.1:
 *  - new fields as per specs
 */

package com.example.cmpt395solaris.database.employees

import androidx.compose.runtime.MutableState

data class Employee(
    var id: Int,
    val fname: String,
    val lname: String,
    val nname: String,
    val email: String,
    val pnumber: String,
    val isActive: Boolean,
    val opening: Boolean,
    val closing: Boolean,
)