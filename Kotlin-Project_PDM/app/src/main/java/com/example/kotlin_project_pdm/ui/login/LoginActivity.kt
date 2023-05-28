package com.example.kotlin_project_pdm.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.kotlin_project_pdm.MainActivity
import com.example.kotlin_project_pdm.R
import com.example.kotlin_project_pdm.database.DatabaseHelper
import com.example.kotlin_project_pdm.ui.register.RegisterActivity

// The LoginActivity is responsible for providing the user interface for user authentication
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initializing UI elements
        val emailEditText = findViewById<EditText>(R.id.loginEtEmail)
        val passwordEditText = findViewById<EditText>(R.id.loginEtPassword)
        val showPasswordImageView = findViewById<ImageView>(R.id.loginImgPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        var isPasswordVisible = false

        // Setting input type for password field
        passwordEditText.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        // Listens to password visibility toggling
        showPasswordImageView.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                showPasswordImageView.setImageResource(R.drawable.baseline_visibility_24)
            } else {
                passwordEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPasswordImageView.setImageResource(R.drawable.baseline_visibility_off_24)
            }
            passwordEditText.setSelection(passwordEditText.text.length)
        }

        // Checks if email is valid
        fun isEmailValid(email: String): Boolean {
            return email.isNotBlank()
        }

        // Checks if password is valid
        fun isPasswordValid(password: String): Boolean {
            return password.isNotBlank()
        }

        // Instance of DatabaseHelper for database operations
        val dbHelper = DatabaseHelper(applicationContext)

        // Defines the action when login button is clicked
        buttonLogin.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (!isEmailValid(email)) {
                emailEditText.error = "Enter an email address"
                return@setOnClickListener
            } else if (!isPasswordValid(password)) {
                passwordEditText.error = "Enter a password"
                return@setOnClickListener
            } else {
                if (dbHelper.checkUser(email, password)) {
                    Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT)
                        .show()
                    val username = dbHelper.getUsernameByEmailAndPassword(email, password)
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)


                } else {
                    Toast.makeText(applicationContext, "Invalid Credentials", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        // Sets click listener for sign-up TextView to navigate to RegisterActivity
        val textView = findViewById<TextView>(R.id.tvSignUp)
        textView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}