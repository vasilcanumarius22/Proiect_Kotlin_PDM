package com.example.kotlin_project_pdm.ui.location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_project_pdm.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val businessLocation = LatLng(44.4479, 26.0979)  // Coordinates of Academia de Studii Economice, Clădirea Virgil Madgearu, București, Romania
        mMap.addMarker(MarkerOptions().position(businessLocation).title("Business Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(businessLocation, 15f))
    }
}
