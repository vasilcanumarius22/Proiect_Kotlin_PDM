package com.example.kotlin_project_pdm.ui.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Contact Fragment"
    }
    val text: LiveData<String> = _text
}