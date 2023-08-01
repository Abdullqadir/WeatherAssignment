package com.example.weatherassignment.ui.screens.main_screen


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherassignment.WeatherApplication
import com.example.weatherassignment.repository.WeatherRepository
import com.example.weatherassignment.utils.RepoStatus
import kotlinx.coroutines.launch
import timber.log.Timber


@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    private val _uiStatus = MutableLiveData<UIStatus>()
    val uiStatus: LiveData<UIStatus>
        get() = _uiStatus

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing


    init {
        Timber.tag("MainViewModel").d("MainViewModel is called")
        _uiStatus.postValue(UIStatus.Loading)
        getWeatherData()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeatherData() {
        viewModelScope.launch {
            _isRefreshing.postValue(true)
            when (val weatherData = weatherRepository.getWeatherData()) {
                is RepoStatus.Success -> _uiStatus.postValue(
                    UIStatus.Success(
                        weatherData.data,
                        weatherData.message,
                    )
                )
                is RepoStatus.Error -> _uiStatus.postValue(UIStatus.Error(weatherData.message))
            }
            _isRefreshing.postValue(false)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WeatherApplication)
                val weatherRepository = application.container.weatherRepository
                MainViewModel(
                    weatherRepository = weatherRepository,
                )
            }
        }
    }
}