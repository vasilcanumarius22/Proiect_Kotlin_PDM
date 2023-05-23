package com.example.kotlin_project_pdm.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_project_pdm.weather.WeatherResponse
import com.example.kotlin_project_pdm.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()
    val weatherLiveData = MutableLiveData<WeatherResponse>()
    val cityNameLiveData = MutableLiveData<String>()

    fun fetchWeather(city: String) {
        cityNameLiveData.postValue(city) // Post the city name to the live data
        viewModelScope.launch {
            val weather = repository.getWeather(city)
            weatherLiveData.postValue(weather)
        }
    }
}
