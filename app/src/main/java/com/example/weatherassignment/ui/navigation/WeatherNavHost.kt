package com.example.weatherassignment.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherassignment.ui.screens.details_screen.DetailsScreen
import com.example.weatherassignment.ui.screens.main_screen.MainScreen

enum class WeatherScreens {
    Main, Details
}

const val WEATHER_DETAIL_ARGUMENT = "WeatherDetailArgument"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherApp(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.Main.name,
    ) {
        composable(
            route = WeatherScreens.Main.name,
            content = { MainScreen(navController) }
        )
        composable(
            route = WeatherScreens.Details.name,
            content = { DetailsScreen(navController) }
        )
    }
}
