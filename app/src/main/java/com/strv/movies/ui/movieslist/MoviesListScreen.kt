package com.strv.movies.ui.movieslist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.strv.movies.R
import com.strv.movies.model.Movie
import com.strv.movies.ui.darklightmodeswitchicon.DarkLightModeSwitchIcon
import com.strv.movies.ui.error.ErrorScreen
import com.strv.movies.ui.loading.LoadingScreen
import com.strv.movies.ui.navigation.MoviesNavGraph

@Composable
fun MoviesListScreen(
    navController: NavController,
    navigateToMovieDetail: (movieId: Int) -> Unit,
    viewModel: MoviesListViewModel = viewModel(),
    isDarkTheme: Boolean,
    changeTheme: (isDarkTheme: Boolean) -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()

    if (viewState.loading) {
        LoadingScreen()
    } else if (viewState.error != null) {
        ErrorScreen(errorMessage = viewState.error!!)
    } else {
        MoviesList(
            navController = navController,
            movies = viewState.movies,
            onMovieClick = navigateToMovieDetail,
            isDarkTheme = isDarkTheme,
            changeTheme = changeTheme
        )
    }
}

@Composable
fun MovieItem(movie: Movie, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(all = 8.dp)
    ) {
        AsyncImage(
            contentScale = ContentScale.FillBounds,
            model = "https://image.tmdb.org/t/p/h632${movie.posterPath}",
            contentDescription = stringResource(id = R.string.movie_image)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MoviesList(
    navController: NavController,
    movies: List<Movie>,
    onMovieClick: (movieId: Int) -> Unit,
    isDarkTheme: Boolean,
    changeTheme: (isDarkTheme: Boolean) -> Unit
) {
    Column {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.app_name))
            },
            backgroundColor = MaterialTheme.colors.primary,
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = { DarkLightModeSwitchIcon(
                isDarkTheme = isDarkTheme,
                changeTheme = changeTheme
            )}
        )
        LazyVerticalGrid(
            modifier = Modifier.padding(8.dp),
            cells = GridCells.Fixed(2)
        ) {
            items(movies) { movie ->
                val state = remember {
                    MutableTransitionState(false).apply {
                        // Start the animation immediately.
                        targetState = true
                    }
                }
                AnimatedVisibility(
                    visibleState = state,
                    enter = fadeIn(animationSpec = tween(300)) + scaleIn(animationSpec = tween(300))
                ) {
                    MovieItem(
                        movie = movie,
                        modifier = Modifier
                            .animateItemPlacement()
                            .clickable { onMovieClick(movie.id) }
                    )
                }
            }
        }
    }
}
