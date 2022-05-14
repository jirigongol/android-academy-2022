package com.strv.movies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.strv.movies.ui.moviedetail.MovieDetailScreen
import com.strv.movies.ui.movieslist.MoviesListScreen
import com.strv.movies.ui.movieslogin.MoviesLogin

@Composable
fun MoviesNavGraph(
    navController: NavHostController = rememberNavController(),
    isDarkTheme: Boolean,
    changeTheme: (isDarkTheme: Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = MoviesDestinations.MOVIES_LOGIN_ROUTE
    ) {
        composable(route = MoviesDestinations.MOVIES_LOGIN_ROUTE) {
            MoviesLogin(navController = navController)
        }

        composable(route = MoviesDestinations.MOVIES_LIST_ROUTE) {
            MoviesListScreen(
                isDarkTheme = isDarkTheme,
                changeTheme = changeTheme,
                navController = navController,
                navigateToMovieDetail = { movieId ->
                    navController.navigate("${MoviesDestinations.MOVIE_DETAIL_ROUTE}/$movieId")
                },
                viewModel = hiltViewModel()
            )
        }

        composable(
            route = "${MoviesDestinations.MOVIE_DETAIL_ROUTE}/{${MoviesNavArguments.MOVIE_ID_KEY}}",
            arguments = listOf(
                navArgument(MoviesNavArguments.MOVIE_ID_KEY) {
                    type = NavType.IntType
                }
            )
        ) {
            MovieDetailScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                changeTheme = changeTheme,
                viewModel = hiltViewModel()
            )
        }
    }
}
