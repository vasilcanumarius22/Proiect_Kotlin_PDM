package com.example.kotlin_project_pdm

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_project_pdm.ui.weather.WeatherViewModel

class WeatherActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        val cityInput = findViewById<EditText>(R.id.cityInput)
        val fetchButton = findViewById<Button>(R.id.fetchButton)
        val weatherText = findViewById<TextView>(R.id.weatherText)
        val cityNameText = findViewById<TextView>(R.id.cityNameText)

        fetchButton.setOnClickListener {
            viewModel.fetchWeather(cityInput.text.toString())
        }

        viewModel.weatherLiveData.observe(this) { weather ->
            weatherText.text = "Temperature: ${weather.main.temp}, Weather: ${weather.weather[0].main}"
        }

        viewModel.cityNameLiveData.observe(this) { cityName ->
            cityNameText.text = "City: $cityName"
        }

        // Fetch weather for Bucharest by default
        viewModel.fetchWeather("Bucharest")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

