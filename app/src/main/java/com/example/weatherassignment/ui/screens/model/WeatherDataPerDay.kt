package com.example.weatherassignment.ui.screens.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "weather")
data class WeatherDataPerDay(
    @PrimaryKey
    val dayOfWeek: String,
    val weatherCode: Int,
    val temperatureMax: Int,
    val temperatureMin: Int,
    val uvIndexMax: Double,
    val windSpeedMax: Double,
    val evapotranspiration: Double? = null,
) : Parcelable