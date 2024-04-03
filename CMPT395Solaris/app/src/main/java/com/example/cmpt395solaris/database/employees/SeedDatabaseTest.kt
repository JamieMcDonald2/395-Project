/**
 *  Seed Database 1.3
 *
 *  v1.3
 *      - added id
 */

package com.example.cmpt395solaris.database.employees

import android.content.Context
import com.example.cmpt395solaris.database.DatabaseHelper
import kotlin.random.Random

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
    val pnumbers = listOf(
        "7805551234",
        "7805551234",
        "7805551234",
        "7805551234",
        "7805551234",
        "7805551234",
        "7805551234"
    )

    for (i in names.indices) {
        val id = Random.nextInt(100, 999)
        val nameParts = names[i].split(" ")
        val email = emails[i]
        val pnumber = pnumbers.random()
        val isActive = (i % 2 == 0)
        val opening = (i % 3 == 0)
        val closing = (i % 5 == 0)

        dbHelper.addEmployee(id, nameParts[0], nameParts[1], nameParts[0], email, pnumber, isActive, opening, closing)
    }
}