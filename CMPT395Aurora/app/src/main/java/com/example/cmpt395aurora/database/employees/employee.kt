/**
 * Employee Data Class
 */

package com.example.cmpt395aurora.database.employees

data class Employee(
    val id: Int,
    val fname: String,
    val lname: String,
    val nname: String,
    val email: String,
    val isActive: String,
    val opening: String,
    val closing: String,
    val position: String
)