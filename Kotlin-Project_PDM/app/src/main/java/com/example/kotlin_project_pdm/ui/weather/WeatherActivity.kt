package com.example.kotlin_project_pdm.ui.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.kotlin_project_pdm.R
import com.example.kotlin_project_pdm.database.DatabaseHelper
import com.google.android.gms.location.*
import java.util.*

// WeatherActivity is an AppCompatActivity that displays the current weather for a specific city
// The user can input the city name manually or use the location service to get the weather for their current location
class WeatherActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    // The onCreate function sets up the UI when the activity is created.
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        // Initialize ViewModel and FusedLocationProviderClient
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize views
        val cityInput = findViewById<EditText>(R.id.cityInput)
        val fetchButton = findViewById<Button>(R.id.fetchButton)
        val locationButton = findViewById<Button>(R.id.locationButton)
        val weatherText = findViewById<TextView>(R.id.weatherText)
        val temperatureText = findViewById<TextView>(R.id.temperatureText)
        val cityNameText = findViewById<TextView>(R.id.cityNameText)

        // Fetch weather data when the fetch button is clicked
        fetchButton.setOnClickListener {
            viewModel.fetchWeather(cityInput.text.toString())
        }

        // Get favorite cities from shared preferences and display them in a spinner
        val sp = this.getSharedPreferences("favoritesSP", Context.MODE_PRIVATE)
        val userText = sp.getString("username", "error_reading_username").toString()
        val spFavorites = findViewById<Spinner>(R.id.spFavorites)
        val dbHelper = DatabaseHelper(applicationContext)
        val favorites  = dbHelper.getFavoritesItems(userText)

        spFavorites.adapter =
            ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, favorites)

        spFavorites.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                cityInput.setText(favorites.get(position))
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Nothing (at the moment)
            }
        })

        // Request location updates when the location button is clicked
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

        // Observe changes to weather data and update the UI accordingly
        viewModel.weatherLiveData.observe(this) { temperature ->
            var tipTemperatura = "Celsius"
            tipTemperatura = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                    .getString("tip_temperatura", null).toString()

            if(tipTemperatura == "Fahrenheit")
            {
                val tempFahrenheit = (temperature.main.temp * 1.8 + 32)
                temperatureText.text = "Temperature: ${String.format(
                    "%.2f", tempFahrenheit)}\u00B0 Fahrenheit"
            }
            else {
                temperatureText.text = "Temperature: ${temperature.main.temp}\u00B0 Celsius"
            }

        }

        viewModel.weatherLiveData.observe(this) { weather ->
            weatherText.text = "Weather: ${weather.weather[0].main}"
        }

        viewModel.cityNameLiveData.observe(this) { cityName ->
            cityNameText.text = "City: $cityName"
        }

        // Fetch weather for Bucharest by default
        // viewModel.fetchWeather("Bucharest")

        // Fetch weather for the current location when the activity is created
        requestLocationUpdates()

        // enable support action bar (used for back button)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Handle the up button in the action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Request location updates from FusedLocationProviderClient
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
                        viewModel.weatherLiveData.observe(this) {

                            val sp = this.getSharedPreferences("weatherSP", Context.MODE_PRIVATE)
                            val editor = sp.edit()
                            editor.putString("city", cityName)
                            editor.putFloat("temp", it.main.temp.toFloat())
                            editor.apply()

                        }
                    }
                }
            }
        }
    }

    // Handle the result of the location permission request
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
