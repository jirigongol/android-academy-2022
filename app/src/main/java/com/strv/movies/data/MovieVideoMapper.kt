package com.strv.movies.data

import com.strv.movies.model.Movie
import com.strv.movies.model.MovieDTO
import com.strv.movies.model.Video
import com.strv.movies.model.VideoDTO
import javax.inject.Inject

class MovieVideoMapper @Inject constructor(): Mapper<VideoDTO, Video> {
    override fun map(from: VideoDTO): Video {
        return Video(
            id = from.id,
            key = from.key
        )
    }
}
