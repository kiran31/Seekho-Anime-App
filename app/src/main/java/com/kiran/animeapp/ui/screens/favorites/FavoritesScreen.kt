package com.kiran.animeapp.ui.screens.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kiran.animeapp.ui.components.AnimeListItem
import com.kiran.animeapp.ui.components.EmptyScreen
import com.kiran.animeapp.ui.navigation.Screen

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoriteAnime by viewModel.favorites.collectAsState()

    if (favoriteAnime.isEmpty()) {
        EmptyScreen(message = "You haven't added any favorites yet.\nTap the heart icon on an anime to save it here!")
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(favoriteAnime, key = { it.mal_id }) { anime ->
                AnimeListItem(
                    anime = anime,
                    onFavoriteClicked = { viewModel.toggleFavorite(anime) },
                    onClick = {
                        navController.navigate(Screen.Detail.route + "/${anime.mal_id}")
                    }
                )
            }
        }
    }
}