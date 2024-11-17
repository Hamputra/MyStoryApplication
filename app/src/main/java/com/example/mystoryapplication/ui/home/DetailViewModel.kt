package com.example.mystoryapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystoryapplication.api.response.GetDetailStoriesResponse
import com.example.mystoryapplication.pref.UserRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _detailStory = MutableLiveData<GetDetailStoriesResponse>()
    val detailStory: LiveData<GetDetailStoriesResponse> = _detailStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchDetailStory(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _detailStory.value = userRepository.getDetailStory(id)
            } catch (e: Exception) {
                // Handle error
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}