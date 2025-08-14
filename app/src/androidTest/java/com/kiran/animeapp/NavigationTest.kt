// In: src/androidTest/kotlin/com/kiran/animeapp/NavigationTest.kt

package com.kiran.animeapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.kiran.animeapp.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun app_launchesAndDisplaysExploreScreen() {
        composeRule.waitUntil(timeoutMillis = 10_000) {
            composeRule
                .onAllNodesWithText("Search Anime...")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText("Explore").assertIsDisplayed()
    }

    @Test
    fun bottomNavigation_navigateToFavoritesScreen() {
        composeRule.waitUntil(timeoutMillis = 10_000) {
            composeRule
                .onAllNodesWithText("Favorites")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText("Favorites").performClick()
        composeRule.onNodeWithText("You haven't added any favorites yet.", substring = true).assertIsDisplayed()
    }

    @Test
    fun bottomNavigation_navigateToAboutScreen() {
        composeRule.waitUntil(timeoutMillis = 10_000) {
            composeRule
                .onAllNodesWithText("About")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText("About").performClick()
        composeRule.onNodeWithText("App Features").assertIsDisplayed()
        composeRule.onNodeWithText("Modern UI").assertIsDisplayed()
    }
}