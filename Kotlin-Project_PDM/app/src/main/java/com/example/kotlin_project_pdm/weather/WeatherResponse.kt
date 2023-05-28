package com.example.kotlin_project_pdm.weather

// The WeatherResponse class represents the complete weather response,
// which includes both main weather data and a list of weather conditions
data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>
)
