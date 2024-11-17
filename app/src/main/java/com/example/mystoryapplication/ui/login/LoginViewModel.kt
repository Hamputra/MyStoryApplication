package com.example.mystoryapplication.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystoryapplication.api.response.LoginResult
import com.example.mystoryapplication.pref.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult?>()
    val loginResult: LiveData<LoginResult?> = _loginResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = userRepository.login(email, password)
                if (!response.error) {
                    _loginResult.value = response.loginResult
                } else {
                    _errorMessage.value = response.message
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}