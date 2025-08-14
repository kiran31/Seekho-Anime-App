package com.kiran.animeapp.ui.screens.explore

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kiran.animeapp.ui.components.*
import com.kiran.animeapp.ui.navigation.Screen

@Composable
fun ExploreScreen(
    navController: NavController,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val animePagingItems = viewModel.animePagingFlow.collectAsLazyPagingItems()

    val isOffline = animePagingItems.loadState.refresh is LoadState.Error
    val listHasContent = animePagingItems.itemCount > 0

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = searchQuery,
            onQueryChanged = viewModel::onSearchQueryChanged,
            onClearClicked = { viewModel.onSearchQueryChanged("") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OfflineBanner(isVisible = isOffline && listHasContent)

        Box(modifier = Modifier.weight(1f)) {
            if (animePagingItems.loadState.refresh is LoadState.Loading && !listHasContent) {
                ShimmerGrid()
            }
            else if (isOffline && !listHasContent) {
                ErrorScreen(
                    message = "Could not connect to the server.\nPlease check your internet connection.",
                    onRetry = { animePagingItems.retry() }
                )
            }

            else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(animePagingItems.itemCount) { index ->
                        val anime = animePagingItems[index]
                        if (anime != null) {
                            AnimeListItem(
                                anime = anime,
                                onFavoriteClicked = { viewModel.toggleFavorite(anime) },
                                onClick = {
                                    navController.navigate(Screen.Detail.route + "/${anime.mal_id}")
                                }
                            )
                        }
                    }

                    if (animePagingItems.loadState.append is LoadState.Loading) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}