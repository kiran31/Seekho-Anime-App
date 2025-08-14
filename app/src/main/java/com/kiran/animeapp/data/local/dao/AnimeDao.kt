package com.kiran.animeapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.kiran.animeapp.data.local.entity.AnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<AnimeEntity>)

    @Query("SELECT * FROM animes ORDER BY popularity ASC, title ASC")
    fun getAnimePagingSource(): PagingSource<Int, AnimeEntity>

    @Query("SELECT * FROM animes WHERE title LIKE :query ORDER BY popularity ASC, title ASC")
    fun searchAnimePagingSource(query: String): PagingSource<Int, AnimeEntity>

    @Query("SELECT * FROM animes WHERE mal_id = :id")
    suspend fun getAnimeById(id: Int): AnimeEntity?

    @Query("SELECT * FROM animes WHERE isFavorite = 1 ORDER BY title ASC")
    fun getFavoriteAnime(): Flow<List<AnimeEntity>>

    @Update
    suspend fun updateAnime(anime: AnimeEntity)

    @Query("DELETE FROM animes")
    suspend fun clearAll()
}
