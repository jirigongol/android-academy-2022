package com.strv.movies.ui.moviedetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.extension.Either
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
            val movieDetail = async { movieRepository.getMovieDetail(movieId) }
            val videos = async { movieRepository.getMovieVideo(movieId) }

            val movieDetailResponse = movieDetail.await()
            val videosResponse = videos.await()

            // We want to show error if we do not get movie detail. If trailer is not there, we can just hide it.
            when (movieDetailResponse) {
                is Either.Error -> {
                    // Here we use smart cast, so no need to .fold() here. We already checked the type in this WHEN branch.
                    val error = movieDetailResponse.error
                    Log.d("TAG", "MovieDetailLoadingError: $error")
                    _viewState.update {
                        MovieDetailViewState(
                            error = error // MovieDetail is crucial for this screen, set error.
                        )
                    }
                }
                is Either.Value -> {
                    videosResponse.fold(
                        { error ->
                            Log.d("TAG", "MovieTrailerLoadingError: $error")
                            _viewState.update {
                                MovieDetailViewState(
                                    movie = movieDetailResponse.value // We do not care about this error too much
                                )
                            }
                        },
                        { videosList ->
                            Log.d("TAG", "MovieTrailerSuccess: ${videosList.size}")
                            _viewState.update {
                                MovieDetailViewState(
                                    movie = movieDetailResponse.value,
                                    video = videosList.first()
                                )
                            }
                        }
                    )
                }
            }
        }
    }

    fun updateVideoProgress(progress: Float) {
        _viewState.update { it.copy(videoProgress = progress) }
    }
}
