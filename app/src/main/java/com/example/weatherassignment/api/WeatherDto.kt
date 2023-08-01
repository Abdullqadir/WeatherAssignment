package com.example.weatherassignment.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherassignment.extensions.convertDateToDayOfWeek
import com.example.weatherassignment.ui.screens.model.WeatherDataPerDay
import com.squareup.moshi.Json


data class Weather(
    @field:Json(name = "daily")
    val weather: WeatherData,
)


data class WeatherData(
    @field:Json(name = "time")
    val date: List<String>,
    @field:Json(name = "weathercode")
    val weatherCode: List<Int>,
    @field:Json(name = "temperature_2m_max")
    val temperatureMax: List<Double>,
    @field:Json(name = "temperature_2m_min")
    val temperatureMin: List<Double>,
    @field:Json(name = "uv_index_max")
    val uvIndexMax: List<Double>,
    @field:Json(name = "windspeed_10m_max")
    val windSpeedMax: List<Double>,
    @field:Json(name = "et0_fao_evapotranspiration")
    val evapotranspiration: List<Double>?,
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun toWeatherDataPerDay(): List<WeatherDataPerDay> {
        val weatherDataPerDayList = mutableListOf<WeatherDataPerDay>()
        for (i in date.indices) {
            weatherDataPerDayList.add(
                WeatherDataPerDay(
                    dayOfWeek = date[i].convertDateToDayOfWeek(),
                    weatherCode = weatherCode[i],
                    temperatureMax = temperatureMax[i].toInt(),
                    temperatureMin = temperatureMin[i].toInt(),
                    uvIndexMax = uvIndexMax[i],
                    windSpeedMax = windSpeedMax[i],
                    evapotranspiration = evapotranspiration?.get(i),
                )
            )
        }
        return weatherDataPerDayList
    }
}

