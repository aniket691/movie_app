package com.example.movietask3.presentation.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietask3.common.Resource
import com.example.movietask3.domain.model.Movie
import com.example.movietask3.data.remote.dto.MovieResponse
import com.example.movietask3.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieViewModel(context: Context, private val repository: MovieRepository) :
    ViewModel() {

    //mutable live data for holding movie api data
    val moviesApiData: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()


    fun deleteMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMovie(movie)
        }
    }

    fun saveMovies(fileName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (repository.getMovieRowCount() == 0) {
                repository.saveMovies(fileName)
            }
        }
    }


    fun getAllMoviesDatabase(): LiveData<List<Movie>> {
        return repository.getMoviesFromDatabase()
    }

    fun addToFav(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFav(id)
        }
    }

    fun getAllFavouriteDatabase(): LiveData<List<Movie>> {
        return repository.getFavouriteMoviesFromDatabase()
    }

    fun getMoviesFromApi(fileName: String) = viewModelScope.launch {
        Log.d("TAG", "second api called")
        moviesApiData.postValue(Resource.Loading())
        val response = repository.getMoviesApi(fileName)
        moviesApiData.postValue(handleBreakingNewsResponse(response))
    }

    //function for handling response
    private fun handleBreakingNewsResponse(response: Response<MovieResponse>): Resource<MovieResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}