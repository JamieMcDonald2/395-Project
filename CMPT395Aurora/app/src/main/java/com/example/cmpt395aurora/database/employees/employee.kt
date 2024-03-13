/**
 * Employee Data Class v1.1
 *
 *  v1.1:
 *  - new fields as per specs
 */

package com.example.cmpt395aurora.database.employees

data class Employee(
    val id: Int,
    val fname: String,
    val lname: String,
    val nname: String,
    val email: String,
    val position: String,
    val isActive: Boolean,
    val opening: Boolean,
    val closing: Boolean,
)