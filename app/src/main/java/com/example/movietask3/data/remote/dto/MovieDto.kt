package com.example.movietask3.data.remote.dto

import androidx.room.ColumnInfo
import com.example.movietask3.domain.model.Movie
import com.google.gson.annotations.SerializedName


data class MovieDTO(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: Int,
    @SerializedName("Summary") val summary: String,
    @SerializedName("Short Summary") val shortSummary: String,
    @SerializedName("Genres") val genres: String,
    @SerializedName("IMDBID") val iMDBID: String,
    @SerializedName("Runtime") val runtime: Int,
    @ColumnInfo(defaultValue = "")
    @SerializedName("YouTube Trailer") val youTubeTrailer: String,
    @SerializedName("Rating") val rating: Double,
    @SerializedName("Movie Poster") val moviePoster: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Writers") val writers: String,
    @SerializedName("Cast") val cast: String

)

fun MovieDTO.toMovie(): Movie {
    return Movie(
        imdb_id = iMDBID,
        title = title,
        year = year,
        summary = summary,
        short_summery = shortSummary,
        genres = genres,
        runtime = runtime,
        youtube_trailer = youTubeTrailer,
        rating = rating,
        movie_poster = moviePoster,
        director = director,
        writers = writers,
        cast = cast
    )
}