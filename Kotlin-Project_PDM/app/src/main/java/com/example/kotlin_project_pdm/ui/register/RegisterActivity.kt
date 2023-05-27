package com.example.kotlin_project_pdm.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.kotlin_project_pdm.R
import com.example.kotlin_project_pdm.database.DatabaseHelper
import com.example.kotlin_project_pdm.models.User
import com.example.kotlin_project_pdm.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val passwordEditText = findViewById<EditText>(R.id.registerEtPassword)
        val confirmPasswordEditText = findViewById<EditText>(R.id.registerEtConfirmPassword)
        val showPasswordImageView = findViewById<ImageView>(R.id.registerImgPassword)
        val showConfirmPasswordImageView = findViewById<ImageView>(R.id.registerConfirmImgPassword)
        val buttonRegister=findViewById<Button>(R.id.buttonRegister);

        var isPasswordVisible = false
        var isConfirmPasswordVisible = false
        passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        confirmPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

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

        showConfirmPasswordImageView.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible

            if (isConfirmPasswordVisible) {
                confirmPasswordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                showConfirmPasswordImageView.setImageResource(R.drawable.baseline_visibility_24)
            } else {
                confirmPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showConfirmPasswordImageView.setImageResource(R.drawable.baseline_visibility_off_24)
            }
            confirmPasswordEditText.setSelection(confirmPasswordEditText.text.length)
        }


//        databaseHelper = DatabaseHelper(requireContext())

        val editTextUsername = findViewById<EditText>(R.id.registerEtUsername)
        val editTextEmail = findViewById<EditText>(R.id.registerEtEmail)

        fun isUsernameValid(username: String): Boolean {
            return username.isNotBlank() && username.length >= 4
        }

        fun isEmailValid(email: String): Boolean {
            val pattern = Patterns.EMAIL_ADDRESS
            return pattern.matcher(email).matches()
        }


        fun isPasswordValid(password: String): Boolean {
            val regex = """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#${'$'}%!\-_?&])(?=\S+${'$'}).{5,}""".toRegex()
            return regex.matches(password)
        }

        fun isConfirmPasswordValid(password: String, confirmPassword: String): Boolean {
            return password == confirmPassword
        }

        buttonRegister.setOnClickListener{
            val username = editTextUsername.text.toString()
            val email = editTextEmail.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (!isUsernameValid(username)) {
                editTextUsername.error = "Invalid username"
                return@setOnClickListener
            }else if (!isEmailValid(email)) {
                editTextEmail.error = "Invalid email address"
                return@setOnClickListener
            }else if (!isPasswordValid(password)) {
                passwordEditText.error = "Invalid password (minimum 6 characters)"
                return@setOnClickListener
            }else if (!isConfirmPasswordValid(password, confirmPassword)) {
                confirmPasswordEditText.error = "Passwords do not match"
                return@setOnClickListener
            } else {
                val user = User( username, email, password)
                val dbHelper = DatabaseHelper(applicationContext)
                dbHelper.addUser(user)
                Toast.makeText(applicationContext, "User Registered Successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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