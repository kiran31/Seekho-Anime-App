package com.kiran.animeapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.gson.Gson
import com.kiran.animeapp.data.local.database.AnimeDatabase
import com.kiran.animeapp.data.remote.api.JikanApiService
import com.kiran.animeapp.data.remote.dto.AnimeDetailResponse
import com.kiran.animeapp.data.remote.dto.AnimeDto
import com.kiran.animeapp.data.repository.AnimeRepository
import com.kiran.animeapp.utils.Resource
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(RobolectricTestRunner::class)
class AnimeRepositoryTest {

    private lateinit var repository: AnimeRepository
    private lateinit var mockWebServer: MockWebServer
    private lateinit var db: AnimeDatabase

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        val apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JikanApiService::class.java)

        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AnimeDatabase::class.java
        ).allowMainThreadQueries().build()

        repository = AnimeRepository(apiService, db)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        db.close()
    }

    @Test
    fun getAnimeDetails_successFromNetwork() = runBlocking {
        val mockAnimeDto = AnimeDto(1, "url", null, null, "Mock Anime", null, null, null, 24, null, 9.5, 1, 1, "synopsis", null, null, null, emptyList())
        val mockResponse = MockResponse()
            .setBody(Gson().toJson(AnimeDetailResponse(mockAnimeDto)))
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val flow = repository.getAnimeDetails(1)
        val result = flow.last()

        assertTrue(result is Resource.Success)
        assertEquals("Mock Anime", result.data?.title)
    }

    @Test
    fun getAnimeDetails_errorFromNetwork_returnsCachedData() = runBlocking {
        val cachedAnime = com.kiran.animeapp.data.local.entity.AnimeEntity(1, "url", "image", null, "Cached Anime", null, 12, "Airing", 8.0, 1, 1, "synopsis", null, null, null, emptyList(), false)
        db.animeDao().insertAll(listOf(cachedAnime))

        val mockResponse = MockResponse().setResponseCode(404)
        mockWebServer.enqueue(mockResponse)

        val flow = repository.getAnimeDetails(1)
        val result = flow.last()

        assertTrue(result is Resource.Success)
        assertEquals("Cached Anime", result.data?.title)
    }
}