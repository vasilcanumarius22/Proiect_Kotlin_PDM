package com.example.kotlin_project_pdm

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_project_pdm.ui.weather.WeatherViewModel
import com.google.android.gms.location.*
import java.util.*

class WeatherActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val cityInput = findViewById<EditText>(R.id.cityInput)
        val fetchButton = findViewById<Button>(R.id.fetchButton)
        val locationButton = findViewById<Button>(R.id.locationButton)
        val weatherText = findViewById<TextView>(R.id.weatherText)
        val cityNameText = findViewById<TextView>(R.id.cityNameText)

        fetchButton.setOnClickListener {
            viewModel.fetchWeather(cityInput.text.toString())
        }

        locationButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                // Request location permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    0
                )
            } else {
                requestLocationUpdates()
            }
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

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener { location ->
            if (location != null) {
                val geocoder = Geocoder(this@WeatherActivity, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                addresses?.let { addr ->
                    if (addr.isNotEmpty()) {
                        val cityName = addr[0].locality ?: return@let
                        viewModel.fetchWeather(cityName)
                    }
                }
            }
        }
    }

    // Commented this because app crashes when i click back btn after fetch weather
//    override fun onPause() {
//        super.onPause()
//        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            0 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission was granted, get the location
                    requestLocationUpdates()
                } else {
                    // Permission denied, disable the functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
