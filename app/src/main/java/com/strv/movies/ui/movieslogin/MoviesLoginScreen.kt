package com.strv.movies.ui.movieslogin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strv.movies.R
import com.strv.movies.ui.error.ErrorScreen
import com.strv.movies.ui.loading.LoadingScreen

@Composable
fun MoviesLoginScreen(
    viewModel: MoviesLoginViewModel = viewModel(),
    navigateToMovieList: () -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()

    if (viewState.loading) {
        LoadingScreen()
    } else if (viewState.error != null) {
        ErrorScreen(errorMessage = viewState.error!!)

    } else {
        MoviesLogin(navigateToMovieList = navigateToMovieList)
    }
}

@Composable
fun MoviesLogin(navigateToMovieList: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(280.dp)
        ) {
            HeaderView()
        }
        Card(
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 240.dp)

        ) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                TwoPartText()
                EmailLogin(navigateToMovieList = navigateToMovieList)
                OrContinueWith()
                NewRegister()
            }
        }
    }
}

@Composable
fun HeaderView() {
    Image(
        painter = painterResource(id = R.mipmap.movies),
        contentDescription = "Login screen background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black
                    ), startY = 90f
                )
            )
    ) {

    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 40.dp)
    ) {
        Text(
            text = "WELCOME TO",
            color = Color.White,
            style = TextStyle(fontSize = 40.sp),
            letterSpacing = 3.sp,
            fontFamily = FontFamily(
                Font(R.font.bebas_neue)
            )
        )
        Text(
            text = "Movies",
            color = Color.Yellow,
            style = TextStyle(fontSize = 60.sp),
            letterSpacing = 3.sp,
            fontFamily = FontFamily(
                Font(R.font.bebas_neue)
            ),
        )
    }
}

@Composable
fun TwoPartText(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(bottom = 32.dp, top = 40.dp)
    ) {
        Text(
            text = "Log in ",
            color = colorResource(id = R.color.colorPrimary),
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(R.font.helvetica_neue_regular))
        )
        Text(
            text = "to your account.",
            color = colorResource(id = R.color.dark_gray),
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(R.font.helvetica_neue_regular))
        )
    }
}

@Composable
fun EmailLogin(
    modifier: Modifier = Modifier,
    navigateToMovieList: () -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.Start) {

        val textEmailAdress = remember { mutableStateOf(TextFieldValue()) }
        val textPassword = remember { mutableStateOf(TextFieldValue()) }
        var passwordVisibility by remember { mutableStateOf(false) }

        val icon = if (passwordVisibility)
            painterResource(id = R.drawable.ic_visibility_on)
        else
            painterResource(id = R.drawable.ic_visibility_off)

        OutlinedTextField(
            value = textEmailAdress.value,
            onValueChange = { textEmailAdress.value = it },
            label = { Text("Email Adress") },
            textStyle = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Email, contentDescription = "Email icon")
            },
            modifier = Modifier
                .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(10.dp),
        )

        OutlinedTextField(
            value = textPassword.value,
            onValueChange = { textPassword.value = it },
            label = { Text("Password") },
            textStyle = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password icon")
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Visibility icon"
                    )
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp)
                .height(60.dp),
            shape = RoundedCornerShape(10.dp),
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()

        )

        Row(modifier = modifier) {
            Spacer(modifier = modifier.weight(1f))
            Text(
                modifier = Modifier
                    .padding(top = 4.dp, end = 32.dp)
                    .clickable { },
                text = "Forgot Password",
                textAlign = TextAlign.End,
                fontSize = 14.sp,
                color = colorResource(id = R.color.light_gray),
            )
        }

        Button(
            onClick = navigateToMovieList,
            modifier = modifier
                .padding(top = 30.dp, bottom = 34.dp, start = 32.dp, end = 32.dp)
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                text = "Login",
                style = MaterialTheme.typography.button,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun OrContinueWith(modifier: Modifier = Modifier) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
    ) {
        Divider(
            color = colorResource(id = R.color.light_gray),
            modifier = Modifier
                .weight(1f)
                .padding(start = 30.dp)
        )
        Text(
            text = "Or continue with",
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.light_gray),
            fontSize = 13.sp,
            modifier = Modifier.padding(8.dp)

        )
        Divider(
            color = colorResource(id = R.color.light_gray),
            modifier = Modifier
                .padding(end = 30.dp)
                .weight(1f)
        )
    }

    Row {
        Image(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = "Google login option",
            modifier = modifier
                .padding(4.dp)
                .clickable(

                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(bounded = false),
                ) {
                }
        )
        Image(
            painter = painterResource(id = R.drawable.ic_fb),
            contentDescription = "Facebook login option",
            modifier = modifier
                .padding(top = 4.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(bounded = false),
                ) {
                }
        )
    }
}

@Composable
fun NewRegister(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(25.dp)
    ) {
        Text(
            text = "New to Movies?",
            color = colorResource(id = R.color.light_gray),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.helvetica_neue_regular))

        )
        Text(
            text = " Register ",
            color = colorResource(id = R.color.colorPrimary),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.helvetica_neue_regular)),
            modifier = modifier.clickable { }
        )
    }
}
