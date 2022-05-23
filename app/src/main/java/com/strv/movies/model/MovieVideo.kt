package com.strv.movies.model

import com.squareup.moshi.Json


data class VideoDTO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "key")
    val key: String
)

data class Video(
    val id: Int,
    val key: String,
)
