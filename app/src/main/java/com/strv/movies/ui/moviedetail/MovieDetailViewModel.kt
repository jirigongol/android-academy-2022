package com.strv.movies.ui.moviedetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.extension.fold
import com.strv.movies.network.MovieRepository
import com.strv.movies.ui.navigation.MoviesNavArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val movieId =
        requireNotNull(savedStateHandle.get<Int>(MoviesNavArguments.MOVIE_ID_KEY)) {
            "Movie id is missing..."
        }

    private val _viewState = MutableStateFlow(MovieDetailViewState(loading = true))
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val movieVideoDeferred = async { fetchMovieVideo() }
            val movieDetailDeferred = async { fetchMovieDetail() }

            movieDetailDeferred.await()
            movieVideoDeferred.await()
        }
    }

    private suspend fun fetchMovieDetail() {
            movieRepository.getMovieDetail(movieId).fold(
                { error ->
                    Log.d("TAG", "MovieDetailLoadingError: $error")
                    _viewState.update {
                        MovieDetailViewState(
                            error = error
                        )
                    }
                },
                { movie ->
                    Log.d("TAG", "MovieDetailSuccess: ${movie.title}")
                    _viewState.update {
                            it.copy(movie = movie, loading = false)

                    }
                }
            )
    }

    private suspend fun fetchMovieVideo() {
        Log.e("TAG", "MovieVideo - Start fetching data.")
        movieRepository.getMovieVideo(movieId).fold(
            { error ->
                Log.e("TAG", "MovieError: $error")
                _viewState.update {
                    MovieDetailViewState(error = error)
                }
            },
            { video ->
                Log.e("TAG", "MovieSuccess: ")
                _viewState.update {
                    it.copy(video = video, loading = false)
                }
            }
        )
    }

    fun updateVideoProgress(progress: Float) {
        _viewState.update { it.copy(videoProgress = progress) }
    }
}
