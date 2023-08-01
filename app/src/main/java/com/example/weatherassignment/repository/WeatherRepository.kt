package com.example.weatherassignment.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherassignment.database.WeatherDao
import com.example.weatherassignment.api.WeatherApiService
import com.example.weatherassignment.ui.screens.model.WeatherDataPerDay
import com.example.weatherassignment.utils.CheckConnectivity
import com.example.weatherassignment.utils.CurrentLocation
import com.example.weatherassignment.utils.RepoStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber


/**
 * This is the repository class for the weather data
 * @param weatherApiService is the retrofit service for the weather data
 * @param weatherDao is the dao for the weather data
 */

interface WeatherRepository {

    suspend fun getWeatherData(): RepoStatus<List<WeatherDataPerDay>>

    /*
     * This function is used to get the weather data from the database
     */
    suspend fun getWeatherFromDB(): RepoStatus<List<WeatherDataPerDay>>

    /*
     * This function is used to insert the weather data into the database
     */
    suspend fun insertWeather(
        weather: WeatherDataPerDay,
    )

}


//write a documentation for this class
/**
 * This is the implementation of the WeatherRepository interface
 * @param weatherApiService is the retrofit service for the weather data
 * @param weatherDao is the dao for the weather data
 */
class WeatherRepositoryImpl(
    private val weatherApiService: WeatherApiService,
    private val weatherDao: WeatherDao,
    private val currentLocation: CurrentLocation,
    private val checkConnectivity: CheckConnectivity,
) : WeatherRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(): RepoStatus<List<WeatherDataPerDay>> =
        withContext(Dispatchers.IO) {
            val isConnected = checkConnectivity.checkForInternet()
            Timber.tag("isConnected123").d(isConnected.toString())
            return@withContext if (isConnected) {
                try {
                    val location = currentLocation.getCurrentLocation()
                    Timber.tag("location123").d(location?.latitude.toString())
                    val weatherData = weatherApiService.getWeather(
                        lat = location?.latitude,
                        long = location?.longitude,
                    )
                    val weatherDataPerDay = weatherData.weather.toWeatherDataPerDay()
                    weatherDataPerDay.forEach {
                        insertWeather(it)
                    }
                    RepoStatus.Success(
                        weatherDataPerDay,
                        "Weather data fetched successfully from API the latitude is ${location?.latitude} and longitude is ${location?.longitude}"
                    )
                } catch (e: Exception) {
                    Timber.tag("API Error").e(e)
                    RepoStatus.Error(e.message ?: "Something went wrong with the API")
                } catch (e: HttpException) {
                    Timber.tag("API Error").e(e)
                    RepoStatus.Error(
                        e.message ?: "Something went wrong with the API \n Error Code ${e.code()}"
                    )
                }
            } else {
                getWeatherFromDB()
            }
        }

    /**
     * This function is used to get the weather data from the database
     * @return RepoStatus<List<WeatherDataPerDay>> is the status of the api call
     */
    override suspend fun getWeatherFromDB(): RepoStatus<List<WeatherDataPerDay>> {
        return try {
            val weather = weatherDao.getAllWeather()
            Timber.d("getWeatherFromDB: $weather")
            RepoStatus.Success(weather, "Weather data fetched successfully from DB")
        } catch (e: Exception) {
            Timber.e(e)
            RepoStatus.Error(e.message ?: "Something went wrong")
        }
    }

    /**
     * This function is used to insert the weather data into the database
     * @param weather is the weather data to be inserted into the database
     */
    override suspend fun insertWeather(
        weather: WeatherDataPerDay,
    ) {
        weatherDao.insertWeather(weather)
    }
}
