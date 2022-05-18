package com.strv.movies.ui.movieslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.data.OfflineMoviesProvider
import com.strv.movies.extension.fold
import com.strv.movies.network.MovieRepository
import com.strv.movies.ui.moviedetail.MovieDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MoviesListViewModel @Inject constructor(movieRepository: MovieRepository) : ViewModel() {

    private val _viewState = MutableStateFlow(MoviesListViewState(loading = true))
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.getPopularMovies().fold(
                { error ->
                    Log.e("TAG", "MovieListError: $error")
                    _viewState.update {
                        MoviesListViewState(error = error)
                    }
                },
                { movieList ->
                    Log.e("TAG", "MovieListSucces: ${movieList.size}")
                    _viewState.update {
                        MoviesListViewState(movies = movieList)
                    }
                }
            )
        }
    }
}
