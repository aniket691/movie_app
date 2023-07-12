package com.example.movietask3.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movietask3.data.remote.MovieApi
import com.example.movietask3.data.remote.dto.MovieResponse
import com.example.movietask3.data.remote.dto.toMovieList
import com.example.movietask3.domain.db.MovieDao
import com.example.movietask3.domain.model.Movie

class MovieRepository(private val movieDao: MovieDao, private val movieService: MovieApi) {

    //data that holds movie api
    private val movieMutLiveDataApi = MutableLiveData<MovieResponse>()
    val moviesLiveDataApi: LiveData<MovieResponse>
        get() = movieMutLiveDataApi

    //get data from api and store it in database
    suspend fun getMovies(fileName: String) {
        val result = movieService.getMovies(fileName)
        if (result.isSuccessful && result.body() != null) {
            movieMutLiveDataApi.postValue(result.body())
            if (result.body() != null) {
                if (result.body()!!.movieList.isEmpty()) {
                    insertMovieList(result.body()!!.toMovieList())
                }
            }
        }
    }

    //function for inserting movie into the database
    private suspend fun insertMovieList(movieList: List<Movie>) {
        movieDao.upsert(movieList)
    }

    //function for deleting movie from the database
    suspend fun deleteMovie(movie: Movie) {
        movieDao.deleteMovie(movie)
    }

    //function for deleting movie from the database
    suspend fun addToFav(id: String) {
        movieDao.addToFav(id)
    }

    //function for deleting movie from the database
    fun getMoviesFromDatabase(): LiveData<List<Movie>> {
        return movieDao.getAllMovies()
    }

    //function for deleting movie from the database
    fun getFavouriteMoviesFromDatabase(): LiveData<List<Movie>> {
        return movieDao.getFavouriteMovies()
    }

    //get movies from api
    suspend fun getMoviesApi(fileName: String) =
        movieService.getMovies(fileName)

}