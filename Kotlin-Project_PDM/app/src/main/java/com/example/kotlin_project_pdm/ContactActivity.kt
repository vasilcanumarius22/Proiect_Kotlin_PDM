package com.example.kotlin_project_pdm

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_project_pdm.databinding.ActivityContactBinding
import com.example.kotlin_project_pdm.ui.contact.ContactViewModel

class ContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val contactViewModel =
            ViewModelProvider(this).get(ContactViewModel::class.java)

        val textView: TextView = binding.tvNameLabel
        contactViewModel.text.observe(this) {
            textView.text = it
        }

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            val etName = findViewById<EditText>(R.id.etName)
            val etEmail = findViewById<EditText>(R.id.etEmail)
            val etMessage = findViewById<EditText>(R.id.etMessage)

            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val message = etMessage.text.toString()

            val toastMessage = "Name: $name, Email: $email, Message: $message"
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()
        }
    }
}
