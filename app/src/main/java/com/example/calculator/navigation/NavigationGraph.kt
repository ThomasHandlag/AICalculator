package com.example.calculator.navigation

import com.example.calculator.ui.screen.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.calculator.ui.screen.BasicScreen
import com.example.calculator.ui.screen.CalModeScreen
import com.example.calculator.ui.screen.HistoryScreen
import com.example.calculator.ui.screen.OnboardScreen
import com.example.calculator.ui.screen.ScreenProps
import com.example.calculator.ui.screen.UnitConverterScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = Route.Splash.path,
    ) {


        composable(Route.Splash.path) {
            SplashScreen(
                navigateTo = { path -> navController.navigate(path) }
            )
        }

        composable(Route.Home.path) {
            CalModeScreen(
                navController = navController,
            )
        }

        composable(
            "${Route.BasicCal.path}/{id}", arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )) { navBackStackEntry ->
            val canPop = navController.previousBackStackEntry != null
            val initInput = navBackStackEntry
                .arguments
                ?.getInt("id")
            BasicScreen(
                canPop = canPop,
                initId = initInput,
                props = ScreenProps(
                    title = "Basic Calculator",
                    onNavigate = {
                        navController.navigate(Route.History.path)
                    },
                    onPop = {
                        if (canPop)
                            navController.navigateUp()
                    }
                )
            )
        }

        composable(
            Route.BasicCal.path
        ) {
            val canPop = navController.previousBackStackEntry != null
            BasicScreen(
                canPop = canPop,
                props = ScreenProps(
                    title = "Basic Calculator",
                    onNavigate = {
                        navController.navigate(Route.History.path)
                    },
                    onPop = {
                        if (canPop)
                            navController.navigateUp()
                    }
                )
            )
        }

        composable(Route.Onboard.path) {
            OnboardScreen(
                navigateTo = { path -> navController.navigate(path) }
            )
        }

        composable(Route.UnitCon.path) {
            val canPop = navController.previousBackStackEntry != null
            UnitConverterScreen(
                onNavigate = {
                    navController.navigateUp()
                },
                canPop = canPop
            )
        }

        composable(Route.History.path) {
            HistoryScreen(
                props = ScreenProps(
                    title = "History",
                    onPop = {
                        navController.navigateUp()
                    },
                    onNavigate = {
                        navController.navigate(Route.BasicCal.path)
                    }
                ),
                onNavigateToCal = { id ->
                    navController.navigate("${Route.BasicCal.path}/$id")
                }
            )
        }
    }
}