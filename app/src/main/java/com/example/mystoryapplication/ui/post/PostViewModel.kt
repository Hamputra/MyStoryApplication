package com.example.mystoryapplication.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystoryapplication.api.response.AddNewStoryResponse
import com.example.mystoryapplication.pref.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PostViewModel(private val storyRepository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _uploadResponse = MutableLiveData<AddNewStoryResponse>()
    val uploadResponse: LiveData<AddNewStoryResponse> = _uploadResponse

    fun uploadStory(file: MultipartBody.Part, description: RequestBody): LiveData<AddNewStoryResponse> {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = storyRepository.uploadStory(file, description)
                _uploadResponse.value = response
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
        return uploadResponse
    }
}