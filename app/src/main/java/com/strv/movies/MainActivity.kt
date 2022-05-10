package com.strv.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.strv.movies.data.OfflineMoviesProvider
import com.strv.movies.ui.moviedetail.MovieDetail
import com.strv.movies.ui.movieslist.MoviesList
import com.strv.movies.ui.movieslogin.MoviesLogin
import com.strv.movies.ui.theme.MoviesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val isSystemDarkTheme = isSystemInDarkTheme()
            val isDarkTheme = remember {
                mutableStateOf(isSystemDarkTheme)

            }
            MoviesTheme(darkTheme = isDarkTheme.value) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    /*Column {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.app_name))
                            },
                            backgroundColor = MaterialTheme.colors.primary,
                            actions = {
                                DarkLightIcon(isDarkTheme)
                            }
                        )*/
//                        MovieDetail(movie = OfflineMoviesProvider.getMovieDetail(1))
//                        MoviesList(movies = OfflineMoviesProvider.getMovies())

                        MoviesLogin()
                        /*Column {
                            Button(onClick = {  }, modifier = Modifier.padding(16.dp)) {
                                Text(text = "Hello Android")
                                
                            }
                            Checkbox(checked = true, onCheckedChange = {})
                            TextField(value = "", onValueChange = {}, isError = true, label = {
                                Text(text = "This is real bad error")
                            })

                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))

                        }*/
                    }
                }
            }
        }
    }
/*
    @Composable
    private fun DarkLightIcon(isDarkTheme: MutableState<Boolean>) {
        Icon(
            painter = painterResource(
                id = if (isDarkTheme.value) R.drawable.ic_light_theme else R.drawable.ic_dark_theme
            ),
            contentDescription = "Change theme",
            modifier = Modifier
                .padding(end = 12.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(bounded = false)
                ) {
                    isDarkTheme.value = !isDarkTheme.value
                }
        )
    }
}*/

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoviesTheme {
        Greeting("Android")
    }
}
