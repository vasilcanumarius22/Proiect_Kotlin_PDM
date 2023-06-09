package com.example.kotlin_project_pdm.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_project_pdm.databinding.FragmentHomeBinding
import com.example.kotlin_project_pdm.ui.about.AboutUsActivity
import com.example.kotlin_project_pdm.ui.contact.ContactActivity
import com.example.kotlin_project_pdm.ui.weather.WeatherActivity

// Home fragment, displays welcome message and navigation buttons.
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Creates fragment UI, sets up ViewModel, and initializes views and handlers.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        val btn: Button = binding.btnAboutUs

        btn.setOnClickListener {
            val intent = Intent(context, AboutUsActivity::class.java)
            startActivity(intent)
        }

        val btnWeather: Button = binding.btnWeather

        btnWeather.setOnClickListener {
            val intent = Intent(context, WeatherActivity::class.java)
            startActivity(intent)
        }

        val btnContact: Button = binding.btnContact

        btnContact.setOnClickListener {
            val intent = Intent(context, ContactActivity::class.java)
            startActivity(intent)
        }

        // Reading the temperature type from SharedPreferences
        var text = "test"
        activity?.let {
            val sp = it.getSharedPreferences("favoritesSP", Context.MODE_PRIVATE)
            text = "Welcome, " + sp.getString("username", "error_reading_username").toString() + "!"

        }
//        text = activity?.let {
//            PreferenceManager.getDefaultSharedPreferences(it.getApplicationContext())
//                .getString("tip_temperatura", null).toString()
//        }.toString()


        homeViewModel.text.observe(viewLifecycleOwner) {

            textView.text =
                if (text != "null") text else "Trebuie ales din setari un tip de temperatura"
        }
        return root
    }

    // Cleans up references when view is detached to avoid memory leaks.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}