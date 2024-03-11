package com.example.cmpt395aurora.database.employees

import android.content.Context
import com.example.cmpt395aurora.database.DatabaseHelper

// seed database
fun seedDatabase(context: Context) {
    val dbHelper = DatabaseHelper(context)
    dbHelper.clearDatabase()
    dbHelper.addEmployee("Justin", "Time", "JDawg", "jdawg@hotmail.com", "1", "1", "1", "General Staff")
    dbHelper.addEmployee("Justine", "Timee", "JFlower", "jflower@hotmail.com", "1", "1", "1", "Time Management Coach")
    dbHelper.addEmployee("Brittany", "Thompson", "Taylor", "emartin@yahoo.com", "1", "1", "1", "Sales Representative")
    dbHelper.addEmployee("Joshua", "Dean", "Andrew", "ashleymartinez@gmail.com", "1", "1", "1", "Data Analyst")
    dbHelper.addEmployee("Carol", "Hunt", "Brett", "hokatelyn@bautista.com", "1", "1", "1", "Project Manager")
    dbHelper.addEmployee("Shawna", "Jones", "Mark", "bonillahannah@stone.info", "1", "1", "1", "UX Designer")
    dbHelper.addEmployee("Alec", "Zhang", "Michael", "norma78@scott.com", "1", "1", "1", "Software Engineer")
    dbHelper.addEmployee("Laura", "Morales", "James", "jason96@gmail.com", "1", "1", "1", "UX Designer")
    dbHelper.addEmployee("Wesley", "Johnston", "Andrew", "vlutz@gmail.com", "1", "1", "1", "Product Manager")
    dbHelper.addEmployee("Steven", "Wise", "Linda", "benjamin05@hotmail.com", "1", "1", "1", "Graphic Designer")
    dbHelper.addEmployee("Nancy", "Calderon", "Heather", "conleymatthew@bowen.net", "1", "1", "1", "Software Engineer")
}