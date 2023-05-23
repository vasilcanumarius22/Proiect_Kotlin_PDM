package com.example.kotlin_project_pdm.weather

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>
)
