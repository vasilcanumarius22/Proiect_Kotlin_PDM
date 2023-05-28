package com.example.kotlin_project_pdm.weather

// The Main class represents the main part of the weather response,
// including the current temperature and humidity
data class Main(
    val temp: Double,
    val humidity: Int
)
