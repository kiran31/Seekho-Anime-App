package com.kiran.animeapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animes")
data class AnimeEntity(
    @PrimaryKey val mal_id: Int,
    val url: String?,
    val image_url: String?,
    val trailer_url: String?,
    val title: String,
    val type: String?,
    val episodes: Int?,
    val status: String?,
    val score: Double?,
    val rank: Int?,
    val popularity: Int?,
    val synopsis: String?,
    val background: String?,
    val season: String?,
    val year: Int?,
    val genres: List<String>,
    var isFavorite: Boolean = false
)
