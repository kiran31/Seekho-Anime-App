package com.kiran.animeapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kiran.animeapp.data.local.database.AnimeDatabase
import com.kiran.animeapp.data.local.entity.AnimeEntity
import com.kiran.animeapp.data.remote.api.JikanApiService
import com.kiran.animeapp.data.remote.dto.toEntity
import com.kiran.animeapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(ExperimentalPagingApi::class)
class AnimeRepository @Inject constructor(
    private val jikanApiService: JikanApiService,
    private val db: AnimeDatabase
) {
    private val animeDao = db.animeDao()

    fun getTopAnime(): Flow<PagingData<AnimeEntity>> {
        val pagingSourceFactory = { animeDao.getAnimePagingSource() }
        return Pager(
            config = PagingConfig(pageSize = 25),
            remoteMediator = AnimeRemoteMediator(
                jikanApiService = jikanApiService,
                animeDatabase = db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun searchAnime(query: String): Flow<PagingData<AnimeEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = { animeDao.searchAnimePagingSource("%$query%") }
        ).flow
    }

    fun getAnimeDetails(id: Int): Flow<Resource<AnimeEntity>> = flow {
        emit(Resource.Loading())
        val localAnime = animeDao.getAnimeById(id)
        if (localAnime != null) {
            emit(Resource.Success(localAnime))
        }

        try {
            val remoteAnime = jikanApiService.getAnimeDetails(id).data.toEntity()
            val finalAnime = remoteAnime.copy(isFavorite = localAnime?.isFavorite ?: false)
            animeDao.insertAll(listOf(finalAnime))
            emit(Resource.Success(data = animeDao.getAnimeById(id)!!))
        } catch (e: HttpException) {
            if (localAnime == null) {
                emit(Resource.Error("Oops, something went wrong!"))
            }
        } catch (e: IOException) {
            if (localAnime == null) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }
        }
    }

    fun getFavoriteAnime(): Flow<List<AnimeEntity>> {
        return animeDao.getFavoriteAnime()
    }

    suspend fun toggleFavoriteStatus(anime: AnimeEntity) {
        val updatedAnime = anime.copy(isFavorite = !anime.isFavorite)
        animeDao.updateAnime(updatedAnime)
    }
}
