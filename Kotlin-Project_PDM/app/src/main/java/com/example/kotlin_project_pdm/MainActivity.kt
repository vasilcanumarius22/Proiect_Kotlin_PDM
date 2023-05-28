package com.example.kotlin_project_pdm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_project_pdm.databinding.ActivityMainBinding
import com.example.kotlin_project_pdm.ui.settings.SettingsActivity

// Main activity, handles user preferences and navigation drawer.
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    // Activity starting point. Initializes layout, user name, and toolbar.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = intent.getStringExtra("username")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val headerView: View = navigationView.getHeaderView(0)

        val textViewUsername: TextView = headerView.findViewById(R.id.tvHeaderUsername)

        textViewUsername.text="Hello, "+username+"!"

        val sp = this.getSharedPreferences("favoritesSP", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("username", username)
        editor.apply()

        setSupportActionBar(binding.appBarMain.toolbar)

        // NO DRAWER PLS
//        val drawerLayout: DrawerLayout = binding.drawerLayout
//        val navView: NavigationView = binding.navView
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.nav_home),
//            drawerLayout
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    // Initializes the activity's options menu.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // Handles menu menu item (back button, actually).
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // Handles Up button press, navigates up in the app structure.
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}