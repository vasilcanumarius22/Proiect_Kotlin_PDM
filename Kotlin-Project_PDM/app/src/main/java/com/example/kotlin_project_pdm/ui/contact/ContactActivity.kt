package com.example.kotlin_project_pdm.ui.contact

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_project_pdm.R

// The ContactActivity class/activity collects and sends the user's contact information via email
class ContactActivity : AppCompatActivity() {

    // This method is called when the activity is created, sets the layout of the activity
    // and implements a click listener for the submit button which collects user's contact details
    // and sends an email containing this information
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            // Handle the Submit button click event
            val etName = findViewById<EditText>(R.id.etName)
            val etEmail = findViewById<EditText>(R.id.etEmail)
            val etMessage = findViewById<EditText>(R.id.etMessage)

            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val message = etMessage.text.toString()

            sendEmail(name, email, message)

            val toastMessage = "Name: $name, Email: $email, Message: $message"
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()
        }

        // enable support action bar (used for back button)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Handles menu menu item (back button, actually)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    // Prepares an email intent with user's contact information and sends it via email
    private fun sendEmail(name: String, senderEmail: String, message: String) {
        val recipientEmail = "vasilcanumarius22@stud.ase.ro" // the email that is going to receive the mail
        val emailSubject = "Contact from $name"
        val emailBody = """
        Sender Name: $name
        Sender Email: $senderEmail
        Message: $message
    """.trimIndent()

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
            putExtra(Intent.EXTRA_SUBJECT, emailSubject)
            putExtra(Intent.EXTRA_TEXT, emailBody)
            type = "message/rfc822"
        }

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }

}
