package com.example.kotlin_project_pdm.api

import retrofit2.http.GET
import retrofit2.http.Query
import com.example.kotlin_project_pdm.weather.WeatherResponse

interface WeatherAPI {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = "27b97f0401863f51cde318ca0ef9317d"
    ): WeatherResponse
}
