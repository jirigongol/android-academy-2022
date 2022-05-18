package com.strv.movies.model

import com.squareup.moshi.Json

data class MovieDetailDTO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "revenue")
    val revenue: Int,
    @Json(name = "runtime")
    val runtime: Int
)

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseYear: String,
    val posterPath: String,
    val runtime: Int // TODO this could be nicely used in UI



)
