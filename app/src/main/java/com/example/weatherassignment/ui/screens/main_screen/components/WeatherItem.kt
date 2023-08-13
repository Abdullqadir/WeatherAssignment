package com.example.weatherassignment.ui.screens.main_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherassignment.R
import com.example.weatherassignment.ui.screens.model.WeatherDataPerDay
import com.example.weatherassignment.ui.shared.theme.Keyline12
import com.example.weatherassignment.ui.shared.theme.Keyline24
import com.example.weatherassignment.ui.shared.theme.Keyline48
import com.example.weatherassignment.utils.WeatherCodeItem

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
fun ShowWeatherItem() {
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
