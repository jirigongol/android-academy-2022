package com.strv.movies.ui.movieslogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class MoviesLoginViewModel @Inject constructor() : ViewModel() {
    private val _viewState = MutableStateFlow(MoviesLoginViewState(loading = true))
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            _viewState.update {
                val randomNumber = Random.nextInt(10)
                if (randomNumber < 3) {
                    MoviesLoginViewState(error = "Something went wrong!")
                } else {
                    MoviesLoginViewState()
                }
            }
        }
    }
}
