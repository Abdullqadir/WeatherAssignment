package com.example.weatherassignment.api

import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") lat: Double?,
        @Query("longitude") long: Double?,
        @Query("daily") daily: String = DAILY,
        @Query("timezone") timezone: String = TIME_ZONE
    ): Weather

    companion object {
        const val DAILY =
            "weathercode,temperature_2m_max,temperature_2m_min,uv_index_max,windspeed_10m_max,et0_fao_evapotranspiration"
        const val TIME_ZONE = "Asia/Baghdad"
    }
}












