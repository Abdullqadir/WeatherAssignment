package com.example.weatherassignment.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.weatherassignment.R

sealed class WeatherCodeItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
) {
    object ClearSky : WeatherCodeItem(R.string.clear_sky, R.drawable.art_clear)
    object FewClouds :
        WeatherCodeItem(title = R.string.few_clouds, icon = R.drawable.art_light_clouds)

    object ScatteredClouds :
        WeatherCodeItem(title = R.string.scattered_clouds, icon = R.drawable.art_clouds)

    object ShowerRain :
        WeatherCodeItem(title = R.string.shower_rain, icon = R.drawable.art_light_rain)

    object Rain : WeatherCodeItem(title = R.string.rain, icon = R.drawable.art_rain)
    object Thunderstorm :
        WeatherCodeItem(title = R.string.thunderstorm, icon = R.drawable.art_storm)

    object Snow : WeatherCodeItem(title = R.string.snow, icon = R.drawable.art_snow)
    object Mist : WeatherCodeItem(title = R.string.mist, icon = R.drawable.art_fog)


    companion object {
        fun fromCode(code: Int): WeatherCodeItem {
            return when (code) {
                0 -> ClearSky
                1, 2 -> FewClouds
                3 -> ScatteredClouds
                45, 48 -> Mist
                51, 53, 55 -> ShowerRain
                56, 57 -> ShowerRain
                61, 63, 65 -> Rain
                66, 67 -> Rain
                71, 73, 75 -> Snow
                77 -> Snow
                80, 81, 82 -> Rain
                85, 86 -> Snow
                95 -> Thunderstorm
                96, 99 -> Thunderstorm
                else -> ClearSky
            }
        }
    }
}
