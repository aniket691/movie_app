package com.example.movietask3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.movietask3.domain.db.MovieDao
import com.example.movietask3.domain.db.MovieDatabase
import com.example.movietask3.domain.model.Movie
import kotlinx.coroutines.runBlocking
import org.junit.*

class MovieDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var movieDatabase: MovieDatabase
    lateinit var moviesDao: MovieDao

    @Before
    fun setUp() {
        movieDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        moviesDao = movieDatabase.getMovieDao()
    }


    @Test
    fun insertQuote_expectedSingleQuote() = runBlocking {
        val movie = Movie(
            imdb_id = "tt1234567",
            title = "Example Movie",
            year = 2023,
            summary = "This is an example movie.",
            short_summery = "Short summary",
            genres = "Action, Thriller",
            runtime = 120,
            youtube_trailer = "https://www.youtube.com/watch?v=12345",
            rating = 7.5,
            movie_poster = "https://example.com/poster.jpg",
            director = "John Doe",
            writers = "Jane Smith",
            cast = "Actor A, Actress B",
            isFav = 0,
            iaDel = 0
        )
        moviesDao.upsert(listOf(movie))
        val result = moviesDao.getAllMovies().getOrAwaitValue()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals("Example Movie", result[0].title)
    }

    @After
    fun tearDown() {
        movieDatabase.close()
    }
}