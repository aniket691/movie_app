package com.example.movietask3.data.remote


import com.example.movietask3.data.remote.dto.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("/{endpoint}")
    suspend fun getMovies(
        @Path("endpoint") endpoint: String
    ): Response<MovieResponse>

}