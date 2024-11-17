package com.example.mystoryapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mystoryapplication.api.response.ListStoryItem
import com.example.mystoryapplication.pref.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    val storyPagingData: Flow<PagingData<ListStoryItem>> =
        storyRepository.getStoryPagingData().cachedIn(viewModelScope)

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun getStories() {
        viewModelScope.launch {
            try {
                storyRepository.getStoryPagingData().collect {
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}