package com.example.calculator.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.R
import com.example.calculator.navigation.Route
import com.example.calculator.ui.AppViewModelProvider
import com.example.calculator.ui.viewmodel.AppViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashScreen(
    navigateTo: (path: String) -> Unit = {},
    appViewModel: AppViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val firstLaunch = appViewModel.isFirstLaunch

    LaunchedEffect(Random.nextInt() * 10) {
        delay(3000L)
        if (firstLaunch) {
            navigateTo(Route.Onboard.path)
        } else {
            navigateTo(Route.Home.path)
        }
    }

    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.align(alignment = Alignment.Center),
                painter = painterResource(R.drawable.ic_launcher),
                contentDescription = "App Icon"
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Ai Calculator",
                    style = TextStyle(fontSize = 30.sp)
                )
                LinearProgressIndicator(
                    color = Color(0xFF76D4FF)
                )
            }
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}