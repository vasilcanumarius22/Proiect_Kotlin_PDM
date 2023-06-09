package com.example.kotlin_project_pdm.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.kotlin_project_pdm.api.WeatherAPI

// The WeatherRepository class is responsible for fetching data from the WeatherAPI
class WeatherRepository {
    // A Moshi object to handle JSON deserialization.
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    // A Retrofit object configured with the base URL for the openweathermap.org API
    // and the Moshi converter
    private val api = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")// imi da eroare daca nu e secure
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(WeatherAPI::class.java)

    // The getWeather method fetches weather data for the given city
    suspend fun getWeather(city: String) = api.getWeather(city)
}
