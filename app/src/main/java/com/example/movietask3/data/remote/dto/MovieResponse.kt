package com.example.movietask3.data.remote.dto

import com.example.movietask3.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Movie List")
    val movieList: List<MovieDTO>
)


fun MovieResponse.toMovieList(): List<Movie> {
    val movieArrayList = ArrayList<Movie>(0)
    for (i in movieList) {
        movieArrayList.add(i.toMovie())
    }
    return movieArrayList
}

