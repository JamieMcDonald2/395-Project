package com.example.cmpt395aurora.database.employees

import android.content.Context
import com.example.cmpt395aurora.database.DatabaseHelper

// seed database - made random generator for fun and practice
fun seedDatabase(context: Context) {
    val dbHelper = DatabaseHelper(context)
    dbHelper.clearDatabase()

    val names = listOf(
        "Bugs Bunny",
        "Daffy Duck",
        "Elmer Fudd",
        "Foghorn Leghorn",
        "Sylvester Cat",
        "Tweety Bird",
        "Yosemite Sam",
        "Wile Coyote",
        "Road Runner",
        "Porky Pig",
        "Speedy Gonzales"
    )
    val emails = listOf(
        "bugs.bunny@example.com",
        "daffy.duck@example.com",
        "elmer.fudd@example.com",
        "foghorn.leghorn@example.com",
        "sylvester.cat@example.com",
        "tweety.bird@example.com",
        "yosemite.sam@example.com",
        "wile.e.coyote@example.com",
        "road.runner@example.com",
        "porky.pig@example.com",
        "speedy.gonzales@example.com"
    )
    val positions = listOf(
        "Ambassador of Buzz",
        "Aspiring Novelist",
        "Brand Warrior",
        "Colon Lover",
        "Conversation Architect",
        "Conversion Optimization Wrangler",
        "Copy Cruncher"
    )

    for (i in names.indices) {
        val nameParts = names[i].split(" ")
        val email = emails[i]
        val position = positions.random()
        val isActive = (i % 2 == 0)
        val opening = (i % 3 == 0)
        val closing = (i % 5 == 0)

        dbHelper.addEmployee(nameParts[0], nameParts[1], nameParts[0], email, position, isActive, opening, closing)
    }
}
