package com.example.weatherassignment.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.example.weatherassignment.api.WeatherApiService
import com.example.weatherassignment.database.WeatherDatabase
import com.example.weatherassignment.repository.WeatherRepository
import com.example.weatherassignment.repository.WeatherRepositoryImpl
import com.example.weatherassignment.utils.CheckConnectivity
import com.example.weatherassignment.utils.CheckConnectivityImpl
import com.example.weatherassignment.utils.CurrentLocation
import com.example.weatherassignment.utils.CurrentLocationImpl
import com.google.android.gms.location.LocationServices
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber


/**
 * The `AppContainer` interface provides a way to access dependencies needed across the app.
 */


/**
 * The `AppContainer` interface provides a way to access dependencies needed across the app.
 */
interface AppContainer {
    val weatherRepository: WeatherRepository
    val currentLocation: CurrentLocation
    val checkConnectivity: CheckConnectivity
}

/**
 * Default implementation of [AppContainer] that uses production endpoints.
 */
class DefaultAppContainer(
    private val context: Context,
) : AppContainer {

    private val baseUrl = "https://api.open-meteo.com/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttp)
        .build()

    private val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }

    /**
     * The [CurrentLocation] instance.
     */
    override val currentLocation: CurrentLocation by lazy {
        CurrentLocationImpl(
            LocationServices.getFusedLocationProviderClient(context),
            context as Application
        )
    }

    override val checkConnectivity: CheckConnectivity by lazy {
        CheckConnectivityImpl(context)
    }

    /**
     * The [WeatherRepository] instance.
     */
    override val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(
            retrofitService,
            WeatherDatabase.getDatabase(context = context).weatherDao(),
            currentLocation,
            checkConnectivity,
        )
    }
}
