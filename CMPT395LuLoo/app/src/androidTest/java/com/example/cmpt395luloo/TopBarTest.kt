package com.example.cmpt395luloo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.example.cmpt395luloo.ComponentFunctions.TopBar
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TopBarTest {

    // Create a ComposeTestRule
    @get:Rule
    val composeTestRule = createComposeRule()

//    @Test
//    fun testBackButtonClick() {
//        // Create a mock NavController
//        val navController = mock(NavController::class.java)
//
//        // Launch the TopBar composable with the mock NavController
//        composeTestRule.setContent {
//            TopBar(navController = navController)
//        }
//
//        // Find and click the IconButton corresponding to the back button
//        composeTestRule.onNodeWithTag("backButton").performClick()
//
//        // Verify that the correct navigation action is called on the NavController
//        verify(navController).navigateUp()
    }
}
