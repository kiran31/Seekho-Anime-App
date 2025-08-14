package com.kiran.animeapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    object Main : Screen("main_screen")
    object Detail : Screen("detail_screen")
    object Explore : Screen("explore_screen", "Explore", Icons.Default.Explore)
    object Favorites : Screen("favorites_screen", "Favorites", Icons.Default.Favorite)
    object About : Screen("about_screen", "About", Icons.Default.Info)
}