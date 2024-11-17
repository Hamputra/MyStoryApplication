package com.example.mystoryapplication.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.mystoryapplication.R
import com.example.mystoryapplication.databinding.ActivityWelcomeBinding
import com.example.mystoryapplication.ui.login.LoginActivity
import com.example.mystoryapplication.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signupButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val logoImageView = findViewById<ImageView>(R.id.app_logo)
        if (logoImageView != null) {
            logoImageView.animate().alpha(1f).setDuration(3000).start()
        } else {
            Log.e("WelcomeActivity", "logoImageView is null")
        }

    }
}