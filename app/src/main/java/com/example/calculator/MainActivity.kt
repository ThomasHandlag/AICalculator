package com.example.calculator

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.calculator.navigation.NavigationGraph
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : AppCompatActivity() {

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
