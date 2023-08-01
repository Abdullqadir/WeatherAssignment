package com.example.weatherassignment.ui.screens.main_screen

import com.example.weatherassignment.ui.screens.model.WeatherDataPerDay


sealed interface UIStatus {
    object Loading : UIStatus
    data class Success(val weatherData: List<WeatherDataPerDay>?, val message: String?) :
        UIStatus
    data class Error(val message: String?) : UIStatus
}
