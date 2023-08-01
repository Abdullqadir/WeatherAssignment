package com.example.weatherassignment.ui.screens.main_screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherassignment.R
import com.example.weatherassignment.ui.navigation.WEATHER_DETAIL_ARGUMENT
import com.example.weatherassignment.utils.WeatherCodeItem
import com.example.weatherassignment.ui.screens.model.WeatherDataPerDay
import com.example.weatherassignment.ui.navigation.WeatherScreens
import com.example.weatherassignment.ui.shared.components.DayDetails
import com.example.weatherassignment.ui.shared.components.TopAppBarComposable
import com.example.weatherassignment.ui.shared.theme.Keyline12
import com.example.weatherassignment.ui.shared.theme.Keyline16
import com.example.weatherassignment.ui.shared.theme.Keyline24
import com.example.weatherassignment.ui.shared.theme.Keyline48

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


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    message: String? = "Error",
    action: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        message?.let { Text(text = it, modifier = Modifier.padding(Keyline16)) }
        Snackbar(
            modifier = Modifier
                .padding(Keyline16)
                .wrapContentHeight(Alignment.Bottom),
            action = {
                TextButton(onClick = action) {
                    Text(text = stringResource(R.string.retry))
                }
            }
        ) {
            Text(text = stringResource(R.string.retry_again))
        }
    }
}

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


@Composable
fun WeatherItem(modifier: Modifier = Modifier, weatherDataPerDay: WeatherDataPerDay) {
    val weatherType = WeatherCodeItem.fromCode(weatherDataPerDay.weatherCode)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = Keyline12, horizontal = Keyline24)
    ) {
        Image(
            painter = painterResource(id = weatherType.icon),
            contentDescription = weatherType.title.toString(),
            modifier = Modifier.size(width = Keyline48, height = Keyline48)
        )
        Spacer(modifier = Modifier.width(Keyline12))
        Column {
            Text(text = weatherDataPerDay.dayOfWeek, style = MaterialTheme.typography.h3)
            Text(stringResource(id = weatherType.title), style = MaterialTheme.typography.h4)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.temperature, weatherDataPerDay.temperatureMax),
            style = MaterialTheme.typography.h2,
        )
        Spacer(modifier = Modifier.width(Keyline12))
        Text(
            text = stringResource(R.string.temperature, weatherDataPerDay.temperatureMin),
            style = MaterialTheme.typography.h2,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ShowWeatherItemZ() {
    val data = WeatherDataPerDay(
        dayOfWeek = "Today",
        weatherCode = 1,
        temperatureMax = 12,
        temperatureMin = 10,
        uvIndexMax = 1.0,
        windSpeedMax = 1.0,
        evapotranspiration = 1.0,
    )
    WeatherItem(weatherDataPerDay = data)
}
