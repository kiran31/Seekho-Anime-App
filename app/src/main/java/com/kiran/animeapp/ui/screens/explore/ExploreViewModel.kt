package com.kiran.animeapp.ui.screens.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kiran.animeapp.data.local.entity.AnimeEntity
import com.kiran.animeapp.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val animePagingFlow: Flow<PagingData<AnimeEntity>> = searchQuery
        .debounce(500)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.getTopAnime()
            } else {
                repository.searchAnime(query)
            }
        }.cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun toggleFavorite(anime: AnimeEntity) {
        viewModelScope.launch {
            repository.toggleFavoriteStatus(anime)
        }
    }
}