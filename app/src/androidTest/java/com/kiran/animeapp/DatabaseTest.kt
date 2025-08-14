package com.kiran.animeapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kiran.animeapp.data.local.dao.AnimeDao
import com.kiran.animeapp.data.local.database.AnimeDatabase
import com.kiran.animeapp.data.local.entity.AnimeEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var db: AnimeDatabase
    private lateinit var dao: AnimeDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AnimeDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.animeDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertAndGetAnime() = runBlocking {
        val anime = AnimeEntity(1, "url", "image_url", null, "Test Anime", null, 24, "Airing", 8.5, 1, 100, "Synopsis", null, "Summer", 2025, listOf("Action"), false)
        dao.insertAll(listOf(anime))
        val byId = dao.getAnimeById(1)
        assertEquals(anime.title, byId?.title)
    }

    @Test
    fun insertAndGetFavorites() = runBlocking {
        val favoriteAnime = AnimeEntity(1, "url", "image_url", null, "Favorite Anime", null, 12, "Finished", 9.0, 2, 50, "Synopsis", null, "Fall", 2024, listOf("Drama"), true)
        val regularAnime = AnimeEntity(2, "url", "image_url", null, "Regular Anime", null, 12, "Finished", 8.0, 3, 60, "Synopsis", null, "Fall", 2024, listOf("Comedy"), false)
        dao.insertAll(listOf(favoriteAnime, regularAnime))

        val favorites = dao.getFavoriteAnime().first()
        assertEquals(1, favorites.size)
        assertEquals("Favorite Anime", favorites[0].title)
    }

    @Test
    fun updateAnimeToFavorite() = runBlocking {
        val anime = AnimeEntity(1, "url", "image_url", null, "Test Anime", null, 24, "Airing", 8.5, 1, 100, "Synopsis", null, "Summer", 2025, listOf("Action"), false)
        dao.insertAll(listOf(anime))
        var favorites = dao.getFavoriteAnime().first()
        assertTrue(favorites.isEmpty())

        val updatedAnime = anime.copy(isFavorite = true)
        dao.updateAnime(updatedAnime)

        favorites = dao.getFavoriteAnime().first()
        assertEquals(1, favorites.size)
    }
}