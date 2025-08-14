package com.kiran.animeapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.kiran.animeapp.data.local.entity.AnimeEntity

data class AnimeDto(
    val mal_id: Int,
    val url: String?,
    val images: ImagesDto?,
    val trailer: TrailerDto?,
    val title: String,
    val type: String?,
    val title_english: String?,
    val title_japanese: String?,
    val episodes: Int?,
    val status: String?,
    val score: Double?,
    val rank: Int?,
    val popularity: Int?,
    val synopsis: String?,
    val background: String?,
    val season: String?,
    val year: Int?,
    val genres: List<GenreDto>?
)

data class ImagesDto(
    val jpg: ImageUrlDto?,
    val webp: ImageUrlDto?
)

data class ImageUrlDto(
    val image_url: String?,
    val small_image_url: String?,
    val large_image_url: String?
)

data class TrailerDto(
    @SerializedName("youtube_id") val youtubeId: String?,
    val url: String?,
    val embed_url: String?
)

data class GenreDto(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
)

fun AnimeDto.toEntity(): AnimeEntity {
    return AnimeEntity(
        mal_id = mal_id,
        url = url,
        image_url = images?.jpg?.large_image_url ?: images?.jpg?.image_url,
        trailer_url = trailer?.url,
        title = title,
        episodes = episodes,
        status = status,
        score = score,
        rank = rank,
        type = type,
        popularity = popularity,
        synopsis = synopsis,
        background = background,
        season = season,
        year = year,
        genres = genres?.map { it.name } ?: emptyList()
    )
}