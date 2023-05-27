package com.example.kotlin_project_pdm.ui.about

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import com.example.kotlin_project_pdm.R
import com.example.kotlin_project_pdm.ui.location.LocationActivity

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        val btn: Button = findViewById(R.id.btnSeeLocation)
        btn.setOnClickListener {
            val intent = Intent(this@AboutUsActivity, LocationActivity::class.java)
            startActivity(intent)
        }
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