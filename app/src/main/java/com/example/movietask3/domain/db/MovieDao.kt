package com.example.movietask3.domain.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movietask3.domain.model.Movie


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun upsert(movie: List<Movie>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Movie>>

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("UPDATE movies SET isFav = 1 where imdb_id= :id")
    suspend fun addToFav(id: String)

    @Query("SELECT * FROM movies WHERE isFav=1")
    fun getFavouriteMovies(): LiveData<List<Movie>>

}