package com.example.calculator.navigation

import com.example.calculator.ui.splash.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.calculator.ui.ai.AiCalculatorScreen
import com.example.calculator.ui.ai.AiHistoryScreen
import com.example.calculator.ui.currency.CurrencyScreen
import com.example.calculator.ui.basic.BasicScreen
import com.example.calculator.ui.home.CalModeScreen
import com.example.calculator.ui.history.HistoryScreen
import com.example.calculator.ui.home.OnboardScreen
import com.example.calculator.ui.screen.ScreenProps
import com.example.calculator.ui.settings.SettingScreen
import com.example.calculator.ui.unit.UnitConverterScreen

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

        composable(Route.CurCon.path) {
            CurrencyScreen(
                canPop = navController.previousBackStackEntry != null,
                onNavigate = {
                    navController.navigateUp()
                }
            )
        }

        composable(Route.Setting.path) {
            val canPop = navController.previousBackStackEntry != null
            SettingScreen(
                canPop = canPop,
                onPop = {
                    navController.navigateUp()
                },
                onNavigate = {
                    navController.navigate(Route.Onboard.path)
                }
            )
        }

        composable(Route.AiCal.path) {
            val canPop = navController.previousBackStackEntry != null
            AiCalculatorScreen(
                onPop = {
                    if (canPop)
                        navController.navigateUp()
                },
                onNavigate = {
                    navController.navigate(Route.AiHistory.path)
                }
            )
        }

        composable(
            "${Route.AiCal.path}/{id}", arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )) { navBackStackEntry ->
            val hId = navBackStackEntry
                .arguments
                ?.getInt("id")
            AiCalculatorScreen(
                id = hId,
                onPop = {
                    if (navController.previousBackStackEntry != null)
                        navController.navigateUp()
                },
                onNavigate = {
                    navController.navigate(Route.AiHistory.path)
                }
            )
        }
        composable(Route.AiHistory.path) {
            AiHistoryScreen(
                onPop = {
                    if (navController.previousBackStackEntry != null)
                        navController.navigateUp()
                },
                onNavigate = { id ->
                    navController.navigate("${Route.AiCal.path}/$id")
                }
            )
        }
    }
}