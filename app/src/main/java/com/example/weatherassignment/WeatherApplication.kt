package com.example.weatherassignment

import android.app.Application
import com.example.weatherassignment.di.AppContainer
import com.example.weatherassignment.di.DefaultAppContainer

class WeatherApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}