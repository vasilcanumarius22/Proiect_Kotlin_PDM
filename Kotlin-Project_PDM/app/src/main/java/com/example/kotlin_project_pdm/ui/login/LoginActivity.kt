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

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val emailEditText = findViewById<EditText>(R.id.loginEtEmail)
        val passwordEditText = findViewById<EditText>(R.id.loginEtPassword)
        val showPasswordImageView = findViewById<ImageView>(R.id.loginImgPassword)
        val buttonLogin=findViewById<Button>(R.id.buttonLogin)

        var isPasswordVisible = false
        passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        showPasswordImageView.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                showPasswordImageView.setImageResource(R.drawable.baseline_visibility_24)
            } else {
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPasswordImageView.setImageResource(R.drawable.baseline_visibility_off_24)
            }
            passwordEditText.setSelection(passwordEditText.text.length)
        }

        fun isEmailValid(email: String): Boolean {
            return email.isNotBlank()
        }
        fun isPasswordValid(password: String): Boolean {
            return password.isNotBlank()
        }

        val dbHelper = DatabaseHelper(applicationContext)

        buttonLogin.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (!isEmailValid(email)) {
                emailEditText.error = "Enter an email address"
                return@setOnClickListener
            } else if (!isPasswordValid(password)) {
                passwordEditText.error = "Enter a password"
                return@setOnClickListener
            } else{
                if (dbHelper.checkUser(email, password)) {
                    Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
                    val username = dbHelper.getUsernameByEmailAndPassword(email, password)
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)

                } else {
                    Toast.makeText(applicationContext, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val textView = findViewById<TextView>(R.id.tvSignUp)

        textView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}