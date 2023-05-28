package com.example.kotlin_project_pdm.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.example.kotlin_project_pdm.R
import com.example.kotlin_project_pdm.ui.favorites.FavoritesActivity

// Activity responsible for displaying and handling settings screen
class SettingsActivity : AppCompatActivity() {

    // Initializes activity, loads SettingsFragment and prepares the button for the navigation to the FavoritesActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }


        var button = findViewById<Button>(R.id.btnFavorites)
        button.setOnClickListener {
            val intent = Intent(applicationContext, FavoritesActivity::class.java)
            startActivity(intent)
        }

        // enable support action bar (used for back button)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Handles menu menu item (back button, actually).
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Fragment responsible for displaying settings options from the XML resource file
    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}