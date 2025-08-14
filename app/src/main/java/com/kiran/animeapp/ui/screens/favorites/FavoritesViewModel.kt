package com.kiran.animeapp.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiran.animeapp.data.local.entity.AnimeEntity
import com.kiran.animeapp.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    val favorites = repository.getFavoriteAnime()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleFavorite(anime: AnimeEntity) {
        viewModelScope.launch {
            repository.toggleFavoriteStatus(anime)
        }
    }
}