package com.strv.movies.model

import com.squareup.moshi.Json

data class PopularMoviesDTO(
    @Json(name = "results")
    val results: List<MovieDTO>,
    @Json(name = "total_results")
    val totalResults: Int,
)

data class MovieDTO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "poster_path")
    val posterPath: String
)

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String
)

