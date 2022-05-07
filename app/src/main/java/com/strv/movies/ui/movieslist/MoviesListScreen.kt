package com.strv.movies.ui.movieslist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.strv.movies.R
import com.strv.movies.model.Movie

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovieItem(movie: Movie) {
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn(initialAlpha = 0.5f, animationSpec = tween(durationMillis = 300)) + scaleIn(
            animationSpec = tween(durationMillis = 300)
        )
    ) {
        Card(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = stringResource(id = R.string.movie_image),
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesList(movies: List<Movie>) {
    LazyVerticalGrid(
        modifier = Modifier.padding(all = 8.dp),
        cells = GridCells.Fixed(2)
    ) {
        items(movies) { movie ->
            MovieItem(movie)
        }
    }
}
