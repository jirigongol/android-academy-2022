package com.strv.movies.data

import com.strv.movies.model.Movie
import com.strv.movies.model.MovieDTO
import com.strv.movies.model.MovieDetail
import com.strv.movies.model.MovieDetailDTO
import javax.inject.Inject


class MovieMapper @Inject constructor(): Mapper<MovieDTO, Movie> {
    override fun map(from: MovieDTO): Movie {
        return Movie(
            id = from.id,
            title = from.title,
            posterPath = from.posterPath,
        )
    }
}
