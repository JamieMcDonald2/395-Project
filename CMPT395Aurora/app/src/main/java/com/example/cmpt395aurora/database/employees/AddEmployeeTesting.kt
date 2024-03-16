/**
 * Add Employee Testing Class
 * v1.00
 *
 *  - can add functions for testing add employee functions/UI
 */

package com.example.cmpt395aurora.database.employees

import kotlin.random.Random

class AddEmployeeTesting {

    // Used to populate add employee fields through a temporary button on add employee page
    fun populateTestData(viewModel: EmployeeViewModel) {
        val names = listOf(
            "Lola Bunny",
            "Marvin the Martian",
            "Pepé Le Pew",
            "Granny",
            "Tasmanian Devil (Taz)",
            "Charlie Dog",
            "Melissa Duck",
            "Ralph Wolf",
            "Sam Sheepdog"
        )
        val pnumbers = listOf(
            "7805551234",
            "7805551234",
            "7805551234",
            "7805551234"
        )

        // Select a full name from the names list
        val fullName = names.random()
        val (firstName, lastName) = fullName.split(" ")

        viewModel.fname.value = firstName
        viewModel.lname.value = lastName

        // Generate a nickname based on the first name
        viewModel.nname.value = firstName.take(1) + lastName.take(1)

        viewModel.email.value =
            "${viewModel.fname.value.lowercase()}.${viewModel.lname.value.lowercase()}@example.com"
        viewModel.pnumber.value = pnumbers.random()
        viewModel.isActive.value = Random.nextBoolean()
        viewModel.opening.value = Random.nextBoolean()
        viewModel.closing.value = Random.nextBoolean()
    }
}