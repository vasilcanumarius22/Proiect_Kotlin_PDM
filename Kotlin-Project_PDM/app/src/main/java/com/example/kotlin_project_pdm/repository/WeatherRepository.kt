package com.example.kotlin_project_pdm.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.kotlin_project_pdm.api.WeatherAPI

class WeatherRepository {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val api = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(WeatherAPI::class.java)

    suspend fun getWeather(city: String) = api.getWeather(city)
}
