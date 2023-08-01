package com.example.weatherassignment.ui.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherassignment.R
import com.example.weatherassignment.utils.WeatherCodeItem
import com.example.weatherassignment.ui.screens.model.WeatherDataPerDay
import com.example.weatherassignment.ui.shared.theme.Keyline100
import com.example.weatherassignment.ui.shared.theme.Keyline24
import com.example.weatherassignment.ui.shared.theme.Keyline48


@Composable
fun DayDetails(
    modifier: Modifier = Modifier,
    weather: WeatherDataPerDay?,
    color: Color = MaterialTheme.colors.onPrimary,
    backgroundColor: Color = MaterialTheme.colors.primary,
) {
    val weatherType = weather?.weatherCode?.let { WeatherCodeItem.fromCode(it) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weather?.dayOfWeek ?: "",
                style = MaterialTheme.typography.h3.copy(color = color),
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Keyline24, horizontal = Keyline48)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    weatherType?.let {
                        Image(
                            painter = painterResource(
                                id = it.icon
                            ),
                            contentDescription = it.icon.toString(),
                            modifier = Modifier.size(width = Keyline100, height = Keyline100)
                        )
                        Text(stringResource(id = it.title), color = color)
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.temperature, weather?.temperatureMax.toString()),
                        style = MaterialTheme.typography.h1.copy(
                            color = color,
                        ),
                    )
                    Text(
                        text = stringResource(R.string.temperature, weather?.temperatureMin.toString()),
                        style = MaterialTheme.typography.h2.copy(
                            color = color,
                            fontWeight = FontWeight.W300,
                        ),
                    )
                }
            }
        }
    }
}


@Preview(showBackground = false)
@Composable
fun DayDetailsPreview() {
    val data = WeatherDataPerDay(
        dayOfWeek = "12:00",
        weatherCode = 1,
        temperatureMax = 12,
        temperatureMin = 10,
        uvIndexMax = 1.0,
        windSpeedMax = 1.0,
        evapotranspiration = 1.0,
    )
    DayDetails(weather = data)
}