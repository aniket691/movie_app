package com.example.movietask3.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "movies"
)
class Movie(
    @PrimaryKey()
    val imdb_id: String = "",
    val title: String = "",
    val year: Int = 1,
    val summary: String = "",
    val short_summery: String = "",
    val genres: String = "",
    val runtime: Int = 1,
    val youtube_trailer: String? = "",
    val rating: Double = 1.0,
    val movie_poster: String = "",
    val director: String = "",
    val writers: String = "",
    val cast: String = "",
    val isFav: Int = 0,
    val iaDel: Int = 0
) {
}