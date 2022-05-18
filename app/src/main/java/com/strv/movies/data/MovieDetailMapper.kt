package com.strv.movies.data

import com.strv.movies.model.MovieDetail
import com.strv.movies.model.MovieDetailDTO
import javax.inject.Inject

// It is good thing to call mappers after target classes
class MovieDetailMapper @Inject constructor(): Mapper<MovieDetailDTO, MovieDetail> {
    override fun map(from: MovieDetailDTO): MovieDetail {
        return MovieDetail(
            id = from.id,
            title = from.title,
            overview = from.overview,
            releaseYear = from.releaseDate.substringBefore("-"),
            posterPath = from.posterPath,
            runtime = from.runtime
        )
    }
}
