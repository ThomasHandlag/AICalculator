package com.example.calculator.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Balance
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.CurrencyExchange
import androidx.compose.material.icons.rounded.Percent
import androidx.compose.material.icons.rounded.Scale
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.twotone.Paid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.calculator.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalModeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val modeList: List<Mode> = listOf(
        Mode(name = "Ai Calculator", path = "ai", imageVector = Icons.Rounded.AutoAwesome),
        Mode(name = "Basic Calculator", path = "basic", imageVector = Icons.Rounded.Calculate),
        Mode(name = "Unit Converter", path = "unit", imageVector = Icons.Rounded.Balance),
        Mode(
            name = "Currency Converter",
            path = "currency",
            imageVector = Icons.Rounded.CurrencyExchange
        ),
        Mode(
            name = "Discount Calculator",
            path = "discount",
            imageVector = Icons.Rounded.Percent
        ),
        Mode(name = "Tip Calculator", path = "tip", imageVector = Icons.TwoTone.Paid),
        Mode(name = "Date Calculator", path = "date", imageVector = Icons.Rounded.CalendarMonth),
        Mode(name = "Loan Calculator", path = "loan", imageVector = Icons.Rounded.AccountBalance),
        Mode(name = "GPA Calculator", path = "gpa", imageVector = Icons.Rounded.School),
        Mode(name = "BMI Calculator", path = "bmi", imageVector = Icons.Rounded.Scale)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Ai Calculator")
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Route.Setting.path)
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Settings,
                            contentDescription = "Setting",
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                modeList.forEach { mode ->
                    item {
                        ModeItem(
                            path = mode.path,
                            imageVector = mode.imageVector,
                            name = mode.name,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ModeItem(path: String, navController: NavController, imageVector: ImageVector, name: String) {
    Surface(
        shape = RoundedCornerShape(16.dp), // Rounded corners
        color = Color.White,
        onClick = {
            navController.navigate(path, builder = {
                launchSingleTop = true
                popUpTo(Route.Home.path) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            })
        },
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(imageVector = imageVector, contentDescription = "Mode")
            Text(name)
        }
    }
}

data class Mode(val name: String, val imageVector: ImageVector, val path: String)