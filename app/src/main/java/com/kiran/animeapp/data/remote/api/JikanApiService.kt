package com.kiran.animeapp.data.remote.api

import com.kiran.animeapp.data.remote.dto.AnimeDetailResponse
import com.kiran.animeapp.data.remote.dto.TopAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApiService {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 25,
        @Query("type") type: String? = null
    ): TopAnimeResponse

    @GET("anime/{id}")
    suspend fun getAnimeDetails(
        @Path("id") id: Int
    ): AnimeDetailResponse
}