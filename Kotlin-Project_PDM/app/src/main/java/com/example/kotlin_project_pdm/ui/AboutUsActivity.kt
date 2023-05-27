package com.example.kotlin_project_pdm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.kotlin_project_pdm.R
import com.example.kotlin_project_pdm.ui.location.LocationActivity

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        val btn: Button =findViewById(R.id.btnSeeLocation)
        btn.setOnClickListener {
            val intent = Intent(this@AboutUsActivity, LocationActivity::class.java)
            startActivity(intent)
        }
    }
}