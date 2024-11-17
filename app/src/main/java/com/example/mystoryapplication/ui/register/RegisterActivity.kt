package com.example.mystoryapplication.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mystoryapplication.MainActivity
import com.example.mystoryapplication.R
import com.example.mystoryapplication.RegisterViewModelFactory
import com.example.mystoryapplication.api.Injection
import com.example.mystoryapplication.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        RegisterViewModelFactory(Injection.provideRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupAction()


        val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        binding.appLogo.startAnimation(bounceAnimation)
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            viewModel.register(name, email, password)
        }

        viewModel.registerResult.observe(this) { isSuccess ->
            if (isSuccess) {
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
            binding.signupButton.isEnabled = !isLoading
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}