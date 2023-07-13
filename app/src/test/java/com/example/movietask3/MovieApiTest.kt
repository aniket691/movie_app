package com.example.movietask3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietask3.data.remote.MovieApi
import com.example.movietask3.data.remote.dto.MovieResponse
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieApiTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var movieApi: MovieApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        movieApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    @Test
    fun testGetMovies() = runTest {
        // Enqueue a mock response with an empty list of movies
        val mockResponse = MockResponse().setBody("{\"Movie List\": []}")
        mockWebServer.enqueue(mockResponse)

        // Make the API call
        val response = movieApi.getMovies("1.json")

        // Verify the request was sent
        val request = mockWebServer.takeRequest()
        assertEquals("/1.json", request.path)

        // Verify the response
        assertTrue(response.isSuccessful)
        assertEquals(200, response.code())
        assertEquals(0, response.body()?.movieList?.size)
    }

    @Test
    fun testGetMovies2() = runTest {
        // Enqueue a mock response with an empty body
        val mockResponse = MockResponse().setResponseCode(404)
        mockWebServer.enqueue(mockResponse)

        // Make the API call
        val response = movieApi.getMovies("5.json")

        // Verify the request was sent
        val request = mockWebServer.takeRequest()
        assertEquals("/5.json", request.path)

        // Verify the response
        assertFalse(response.isSuccessful)
        assertEquals(404, response.code())
        assertNull(response.body())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}