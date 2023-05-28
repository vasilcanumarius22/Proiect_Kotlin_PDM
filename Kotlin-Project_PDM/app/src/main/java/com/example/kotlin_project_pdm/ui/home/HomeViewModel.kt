package com.example.kotlin_project_pdm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// ViewModel for HomeFragment, stores fragment text.
class HomeViewModel : ViewModel() {

    // MutableLiveData for fragment text, private to avoid public modification.
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    // Publicly accessible LiveData for observing fragment text.
    val text: LiveData<String> = _text
}