package com.example.kotlin_project_pdm.ui.ourLocation

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kotlin_project_pdm.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLngBounds
import android.graphics.Color
import android.location.Location
import android.widget.Toast
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
            googleMap.myLocation?.let { location ->
                val currentLocation = LatLng(location.latitude, location.longitude)
                googleMap.addMarker(MarkerOptions().position(currentLocation).title("You"))
            }

            val officeLocation = LatLng(44.439663, 26.096306)
            googleMap.addMarker(MarkerOptions().position(officeLocation).title("Our location"))

            googleMap.myLocation?.let { location ->
                val currentLocation = LatLng(location.latitude, location.longitude)
                val officeLocation = LatLng(44.439663, 26.096306)

                // Calculate distance between current location and office
                val distance = FloatArray(1)
                Location.distanceBetween(
                    currentLocation.latitude,
                    currentLocation.longitude,
                    officeLocation.latitude,
                    officeLocation.longitude,
                    distance
                )

                // Move the camera to show both locations
                val builder = LatLngBounds.Builder()
                builder.include(currentLocation)
                builder.include(officeLocation)
                val bounds = builder.build()
                val padding = 100 // padding in pixels
                val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                googleMap.moveCamera(cameraUpdate)

                // Add dotted polyline between current location and office
                val polylineOptions = PolylineOptions()
                    .add(currentLocation, officeLocation)
                    .color(Color.BLUE)
                    .width(5f)
                    .pattern(arrayListOf(Dash(20f), Gap(10f)))
                googleMap.addPolyline(polylineOptions)

                // Display the distance as a toast message
                val distanceInKm = distance[0] / 1000
                Toast.makeText(
                    this,
                    "Distance to office: ${"%.2f".format(distanceInKm)} km",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}