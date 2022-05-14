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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.strv.movies.extension.assistedViewModel
import com.strv.movies.ui.navigation.MoviesNavGraph
import com.strv.movies.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModel.MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val viewModel by assistedViewModel {
                mainViewModelFactory.create(it, isSystemInDarkTheme())
            }

            val isDarkTheme by viewModel.isDarkTheme.collectAsState()
//            changeStatusBarColor(isDarkTheme)

            MoviesTheme(useDarkTheme = isDarkTheme) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MoviesNavGraph(
                        isDarkTheme = isDarkTheme,
                        changeTheme = viewModel::changeTheme
                    )
                }
            }
        }
    }

    /*private fun changeStatusBarColor(isDarkMode: Boolean) {
        val color = if (isDarkMode) R.color.statusBarDarkMode else R.color.statusBarLightMode
        window.statusBarColor = resources.getColor(color, this@MainActivity.theme)
    }*/
}

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
