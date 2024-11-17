package com.example.mystoryapplication.ui.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystoryapplication.api.response.ListStoryItem
import com.example.mystoryapplication.pref.UserRepository
import kotlinx.coroutines.launch

class MapsViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _storiesWithLocation = MutableLiveData<List<ListStoryItem>>()
    val storiesWithLocation: LiveData<List<ListStoryItem>> = _storiesWithLocation

    init {
        viewModelScope.launch {
            try {
                val stories = userRepository.getStoriesWithLocation()
                _storiesWithLocation.value = stories
            } catch (e: Exception) {
                Log.e(TAG, "Error getting stories with location: ${e.message}")
                _storiesWithLocation.value = emptyList()
            }
        }
    }

    companion object {
        private const val TAG = "MapsViewModel"
    }
}