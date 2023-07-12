package com.example.movietask3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.movietask3.domain.db.MovieDao
import com.example.movietask3.domain.db.MovieDatabase
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
//        val quote = Quote(0, "This is a test quote", "CheezyCode")
//        quotesDao.insertQuote(quote)
//
//        val result = quotesDao.getQuotes().getOrAwaitValue()
//
//        Assert.assertEquals(1, result.size)
//        Assert.assertEquals("This is a test quote", result[0].text)
    }

    @Test
    fun deleteQuote_expectedNoResults() = runBlocking {
//        val quote = Quote(0, "This is a test quote", "CheezyCode")
//        quotesDao.insertQuote(quote)
//
//        quotesDao.delete()
//
//        val result = quotesDao.getQuotes().getOrAwaitValue()
//
//        Assert.assertEquals(0, result.size)
    }


    @After
    fun tearDown() {
        movieDatabase.close()
    }
}