package com.strv.movies.network

import com.strv.movies.extension.Either
import com.strv.movies.model.MovieDetailDTO
import com.strv.movies.model.PopularMoviesDTO
import com.strv.movies.model.VideoDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): MovieDetailDTO

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int = 1): PopularMoviesDTO

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideo(@Query("page") movieId: Int): VideoDTO
}
