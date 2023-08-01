package com.example.weatherassignment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherassignment.ui.screens.model.WeatherDataPerDay


@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherDataPerDay)

    @Query("SELECT * FROM weather")
    suspend fun getAllWeather(): List<WeatherDataPerDay>
}