package com.kiran.animeapp.data.remote.dto

data class TopAnimeResponse(
    val pagination: Pagination?,
    val data: List<AnimeDto>?
)

data class Pagination(
    val last_visible_page: Int,
    val has_next_page: Boolean,
    val current_page: Int
)