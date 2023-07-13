package com.example.movietask3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietask3.data.remote.MovieApi
import com.example.movietask3.data.remote.dto.MovieResponse
import com.example.movietask3.domain.db.MovieDao
import com.example.movietask3.domain.model.Movie
import com.example.movietask3.domain.repository.MovieRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class MovieRepositoryTest {


    @Mock
    lateinit var movieApi: MovieApi
    @Mock
    lateinit var movieDao: MovieDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetMoviesFromApi() = runTest {

        // Prepare mock response
        val mockResponse = MovieResponse(emptyList())

        Mockito.`when`(movieApi.getMovies("1.json"))
            .thenReturn(Response.success(mockResponse))

        // Create an instance of MovieRepository
        val sut = MovieRepository(movieDao, movieApi)

        // Call the method under test
        val result = sut.getMoviesApi("1.json")

        // Verify the API call was made
        Mockito.verify(movieApi).getMovies("1.json")

        // Verify the result
        assertTrue(result.isSuccessful)
        assertEquals(mockResponse.movieList, result.body()?.movieList)
    }

    @Test
    fun testSaveMoviesToDatabase() = runBlocking {
        // Prepare sample movies
        val movies = emptyList<Movie>()

        // Create an instance of MovieRepository
        val sut = MovieRepository(movieDao, movieApi)

        // Call the method under test
        sut.insertMovieList(movies)

        // Verify the database call was made to save movies
        Mockito.verify(movieDao).upsert(movies)
    }


    @Test
    fun testApiError() = runBlocking {
        // Prepare mock API error response
        val mockResponse =
            Response.error<MovieResponse>(404, ResponseBody.create(null, "Not Found"))

        Mockito.`when`(movieApi.getMovies("5.json"))
            .thenReturn(mockResponse)

        // Create an instance of MovieRepository
        val sut = MovieRepository(movieDao, movieApi)

        // Call the method under test
        val result = sut.getMoviesApi("5.json")

        // Verify the API call was made
        Mockito.verify(movieApi).getMovies("5.json")

        // Verify the result indicates API error
        assertFalse(result.isSuccessful)
        assertNull(result.body())
    }


    @After
    fun tearDown() {

    }
}