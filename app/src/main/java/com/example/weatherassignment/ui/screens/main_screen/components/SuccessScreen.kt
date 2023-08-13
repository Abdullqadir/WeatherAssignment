package com.example.weatherassignment.ui.screens.main_screen.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.weatherassignment.R
import com.example.weatherassignment.ui.navigation.WEATHER_DETAIL_ARGUMENT
import com.example.weatherassignment.ui.navigation.WeatherScreens
import com.example.weatherassignment.ui.screens.main_screen.UIStatus
import com.example.weatherassignment.ui.shared.components.DayDetails
import com.example.weatherassignment.ui.shared.components.TopAppBarComposable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SuccessScreen(
    navController: NavController,
    uiStatus: UIStatus,
    state: PullRefreshState?,
    refreshing: Boolean? = false,
) {
    Scaffold(
        topBar = {
            TopAppBarComposable(
                title = stringResource(id = R.string.weather_screen_title),
                action = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert, contentDescription = "more"
                        )
                    }
                },
            )
        },
    ) { padding ->

        val weatherData = (uiStatus as UIStatus.Success).weatherData
        val message = uiStatus.message

        Box(Modifier.pullRefresh(state!!)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                item {
                    DayDetails(
                        weather = weatherData?.first(),
                        modifier = Modifier.clickable {
                            navController.currentBackStackEntry?.savedStateHandle?.apply {
                                set(WEATHER_DETAIL_ARGUMENT, weatherData?.first())
                            }
                            navController.navigate(WeatherScreens.Details.name)
                        },
                        color = MaterialTheme.colors.onPrimary,
                        backgroundColor = MaterialTheme.colors.primary
                    )
                }
                weatherData?.let {
                    items(it.drop(1)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colors.onPrimary)
                        ) {
                            WeatherItem(
                                modifier = Modifier.clickable {
                                    navController.currentBackStackEntry?.savedStateHandle?.apply {
                                        set(WEATHER_DETAIL_ARGUMENT, it)
                                    }
                                    navController.navigate(WeatherScreens.Details.name)
                                },
                                weatherDataPerDay = it
                            )
                        }

                    }
                }
            }


            if (refreshing != null) {
                PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
            }


            Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
        }
    }
}
