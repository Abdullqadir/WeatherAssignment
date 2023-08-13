package com.example.weatherassignment.ui.screens.details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherassignment.R
import com.example.weatherassignment.ui.navigation.WEATHER_DETAIL_ARGUMENT
import com.example.weatherassignment.ui.screens.details_screen.components.DetailsBody
import com.example.weatherassignment.ui.screens.model.WeatherDataPerDay
import com.example.weatherassignment.ui.shared.components.DayDetails
import com.example.weatherassignment.ui.shared.components.TopAppBarComposable
import com.example.weatherassignment.ui.shared.theme.WeatherAssignmentTheme

@Composable
fun DetailsScreen(
    navController: NavController,
) {
    val weatherData =
        navController.previousBackStackEntry?.savedStateHandle?.get<WeatherDataPerDay>(WEATHER_DETAIL_ARGUMENT)
    Scaffold(
        topBar = {
            TopAppBarComposable(
                title = stringResource(R.string.details_screen_title),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = null
                        )
                    }
                },
                action = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Share, contentDescription = null
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert, contentDescription = null
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            DayDetails(
                weather = weatherData,
                modifier = Modifier
                    .weight(.4f)
                    .wrapContentSize(Alignment.Center),
                color = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.onPrimary
            )
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .weight(.6f)
                    .background(
                        color = MaterialTheme.colors.secondary
                    )
            ) {
                DetailsBody(
                    title = stringResource(R.string.evapotranspiration),
                    value = weatherData?.evapotranspiration.toString()
                )
                DetailsBody(
                    title = stringResource(R.string.uv_index),
                    value = weatherData?.uvIndexMax.toString(),
                )
                DetailsBody(
                    title = stringResource(R.string.wind),
                    value = stringResource(R.string.km_h, weatherData?.windSpeedMax.toString()),
                )
            }
        }
    }
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    WeatherAssignmentTheme {
        DetailsScreen(navController = rememberNavController())
    }
}
