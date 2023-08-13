package com.example.weatherassignment.ui.screens.main_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherassignment.ui.screens.main_screen.components.ErrorScreen
import com.example.weatherassignment.ui.screens.main_screen.components.LoadingScreen
import com.example.weatherassignment.ui.screens.main_screen.components.SuccessScreen

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel(
        factory = MainViewModel.Factory
    ),
) {

    val weatherUIState = mainViewModel.uiStatus.observeAsState()
    val isRefreshing = mainViewModel.isRefreshing.observeAsState()
    val swipeRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing.value == true,
        onRefresh = mainViewModel::getWeatherData,
    )

    when (weatherUIState.value) {
        is UIStatus.Loading -> LoadingScreen(
            modifier = Modifier.fillMaxSize()
        )

        is UIStatus.Error -> ErrorScreen(
            message = (weatherUIState.value as UIStatus.Error).message,
            action = {
                mainViewModel.getWeatherData()
            },
            modifier = Modifier.fillMaxSize()
        )

        is UIStatus.Success -> SuccessScreen(
            navController = navController,
            uiStatus = weatherUIState.value as UIStatus.Success,
            state = swipeRefreshState,
            refreshing = isRefreshing.value,
        )
        else -> {}
    }
}
