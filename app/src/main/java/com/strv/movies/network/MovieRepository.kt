package com.strv.movies.network

import com.strv.movies.extension.Either
import com.strv.movies.model.MovieDetailEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val api: MovieApi
) {
    suspend fun getMovieDetail(movieId: Int): Either<String, MovieDetailEntity> {
        return try {
            val movie = api.getMovieDetail(movieId)
            Either.Value(movie)
        } catch (exception: Throwable) {
            Either.Error(exception.localizedMessage?: "Network error")
        }
    }
}