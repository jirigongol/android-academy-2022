package com.strv.movies.model

import com.squareup.moshi.Json

data class VideosDTO(
    @Json(name = "results")
    val results: List<VideoDTO>
)

data class VideoDTO(
    @Json(name = "key")
    val key: String
)

data class Video(
    val key: String
)
