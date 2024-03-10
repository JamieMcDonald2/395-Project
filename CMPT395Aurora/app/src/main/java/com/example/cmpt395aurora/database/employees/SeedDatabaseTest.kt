package com.example.cmpt395aurora.database.employees

import android.content.Context
import com.example.cmpt395aurora.database.DatabaseHelper

// seed database
fun seedDatabase(context: Context) {
    val dbHelper = DatabaseHelper(context)
    dbHelper.clearDatabase()

    dbHelper.addEmployee("Justin Time", "Time Management Coach")
    dbHelper.addEmployee("Anita Hand", "Assistant Principal")
    dbHelper.addEmployee("Eura Lee", "Morning Supervisor")
    dbHelper.addEmployee("Aaron DeHurry", "Track Coach")
    dbHelper.addEmployee("Wanda Help", "Guidance Counselor")
    dbHelper.addEmployee("Will Startnow", "Procrastination Therapist")
    dbHelper.addEmployee("Ima Late", "Tardy Monitor")
    dbHelper.addEmployee("Evan Elpful", "Peer Tutor")
    dbHelper.addEmployee("Savannah Day", "Day Planner")
    dbHelper.addEmployee("Upton O'Goode", "Behavioral Specialist")
}