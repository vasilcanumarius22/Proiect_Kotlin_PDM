package com.example.kotlin_project_pdm.ui.favorites

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_project_pdm.R
import com.example.kotlin_project_pdm.database.DatabaseHelper

class FavoritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val sp = this.getSharedPreferences("favoritesSP", Context.MODE_PRIVATE)
        val userText = sp.getString("username", "error_reading_username").toString()


        val et = findViewById<EditText>(R.id.orasInput)



        val button = findViewById<Button>(R.id.btnAdaugaOras)
        button.setOnClickListener {
            val dbHelper = DatabaseHelper(applicationContext)
            dbHelper.addFavoriteItem(et.text.toString(), userText)


            val favorites  = dbHelper.getFavoritesItems(userText)

            val lvFavorites = findViewById<ListView>(R.id.lvFavorites)

            favorites.let {
                val adapter =
                    ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, it)
                lvFavorites.adapter = adapter
            }
        }



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



