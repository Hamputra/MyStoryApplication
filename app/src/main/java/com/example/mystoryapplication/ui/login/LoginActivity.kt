package com.example.mystoryapplication.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mystoryapplication.LoginViewModelFactory
import com.example.mystoryapplication.MainActivity
import com.example.mystoryapplication.R
import com.example.mystoryapplication.api.Injection
import com.example.mystoryapplication.databinding.ActivityLoginBinding
import com.example.mystoryapplication.pref.UserPreferences

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPreferences: UserPreferences
    private val viewModel by viewModels<LoginViewModel> {
        LoginViewModelFactory(Injection.provideRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)

        setupAction()

        val logoImageView = findViewById<ImageView>(R.id.app_logo)
        logoImageView.animate().rotationBy(360f).setDuration(2000).start()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            viewModel.login(email, password)
        }

        viewModel.loginResult.observe(this) { loginResult ->
            if (loginResult != null) {
                userPreferences.saveSession(loginResult.token, true, loginResult.name)
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.loginButton.isEnabled = !isLoading
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}