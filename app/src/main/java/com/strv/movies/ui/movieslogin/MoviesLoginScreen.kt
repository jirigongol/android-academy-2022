package com.strv.movies.ui.movieslogin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strv.movies.R
import com.strv.movies.ui.theme.ghost_white
import com.strv.movies.ui.theme.md_theme_light_onPrimary

@Composable
fun MoviesLogin() {
    Box(modifier = Modifier.fillMaxSize()) {

        Box(contentAlignment = Alignment.Center, modifier = Modifier.height(280.dp)) {
            HeaderView()
        }
        Card(
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            backgroundColor = ghost_white,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 250.dp)

        ) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                TwoPartText()
                EmailLogin()
            }
        }
    }
}


@Composable
fun HeaderView() {
    val fontFamily = FontFamily(
        Font(R.font.bebas_neue)
    )
    Image(
        painter = painterResource(id = R.drawable.movies),
        contentDescription = "Login screen background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 40.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App logo icon"
        )
        Text(
            text = "Movies",
            color = Color.Yellow,
            style = TextStyle(fontSize = 60.sp),
            letterSpacing = 3.sp,
            fontFamily = fontFamily,
        )

    }
}

@Composable
fun TwoPartText(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(32.dp)
    ) {
        Text(text = "Log in ", color = Color.Green, fontSize = 22.sp)
        Text(text = "to your account.", color = Color.Gray, fontSize = 22.sp)
    }
}

@Composable
fun EmailLogin(modifier: Modifier = Modifier) {
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
                .fillMaxWidth(),
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
                .padding(start = 32.dp, end = 32.dp),
            shape = RoundedCornerShape(10.dp),
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()

        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, end = 32.dp)
                .clickable { },
            text = "Forgot Password",
            textAlign = TextAlign.End,
            fontStyle = FontStyle.Italic,
            fontSize = 14.sp,
            color = Color.Green,
        )
        Button(
            onClick = { /*TODO*/ }, modifier = modifier
                .padding(top = 30.dp, bottom = 34.dp, start = 32.dp, end = 32.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                text = "Login",
                style = MaterialTheme.typography.button
            )
        }
    }
}
