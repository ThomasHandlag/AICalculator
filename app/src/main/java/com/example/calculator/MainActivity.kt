package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.calculator.navigation.NavigationGraph
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Calculator()
            }
        }
    }
}

@Composable
fun Calculator() {
    val navController: NavHostController = rememberNavController()
    NavigationGraph(
        navController = navController,
    )
}

@Preview
@Composable
fun CalculatorPreview() {
    Calculator()
}