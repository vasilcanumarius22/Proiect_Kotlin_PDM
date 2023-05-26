package com.example.kotlin_project_pdm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.kotlin_project_pdm.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

        // Reading the temperature type from SharedPreferences
        var text = "test"
        text = activity?.let {
            PreferenceManager.getDefaultSharedPreferences(it.getApplicationContext())
                .getString("tip_temperatura", null).toString()
        }.toString()



        homeViewModel.text.observe(viewLifecycleOwner) {

            textView.text = if (text != "null") text else "Trebuie ales din setari un tip de temperatura"
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}