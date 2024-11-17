package com.example.mystoryapplication.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystoryapplication.pref.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(name: String, email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = userRepository.register(name, email, password)
                if (!response.error) {
                    _registerResult.value = true
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