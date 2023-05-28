package com.example.kotlin_project_pdm.api

import retrofit2.http.GET
import retrofit2.http.Query
import com.example.kotlin_project_pdm.weather.WeatherResponse

// The WeatherAPI interface represents the API for fetching weather information from 'openweathermap.org'
interface WeatherAPI {
    // The @GET annotation tells Retrofit to make a GET request to the specified endpoint (in this case "data/2.5/weather")
    @GET("data/2.5/weather")
    // The getWeather method is a suspend function that retrieves weather data for a specific city
    suspend fun getWeather(
        // The @Query annotations are used to specify the query parameters for the request
        // i.e. q is for city, units is of Celsius/Fahranheit type of temperatures
        // and appid is related to api key
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = "27b97f0401863f51cde318ca0ef9317d"
    ): WeatherResponse
}
