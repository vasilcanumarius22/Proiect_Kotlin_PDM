package com.example.kotlin_project_pdm.ui.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_project_pdm.weather.WeatherResponse
import com.example.kotlin_project_pdm.repository.WeatherRepository
import kotlinx.coroutines.launch

// WeatherViewModel is responsible for preparing and managing the data for the WeatherActivity
class WeatherViewModel : ViewModel() {
    // Repository instance to fetch the weather data
    private val repository = WeatherRepository()

    // LiveData instances to hold the fetched data and observe changes
    val weatherLiveData = MutableLiveData<WeatherResponse>()
    val cityNameLiveData = MutableLiveData<String>()

    // The fetchWeather function fetches the weather for the given city
    fun fetchWeather(city: String) {
        Log.i("WeatherViewModel", "fetchWeather called with city: $city")

        // Post the city name to the live data
        cityNameLiveData.postValue(city)
        viewModelScope.launch {
            val weather = repository.getWeather(city)
            weatherLiveData.postValue(weather)
        }
    }

}
