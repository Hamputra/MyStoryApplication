package com.example.mystoryapplication.pref

import com.example.mystoryapplication.api.ApiService
import com.example.mystoryapplication.api.response.AddNewStoryResponse
import com.example.mystoryapplication.api.response.GetAllStoriesResponse
import com.example.mystoryapplication.api.response.GetDetailStoriesResponse
import com.example.mystoryapplication.api.response.ListStoryItem
import com.example.mystoryapplication.api.response.LoginResponse
import com.example.mystoryapplication.api.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserRepository(private val apiService: ApiService,
                     private val userPreferences: UserPreferences) {

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }

    suspend fun getStories(page: Int, size: Int): GetAllStoriesResponse {
        return apiService.getStories(page, size, 0)
    }

    suspend fun getDetailStory(id: String): GetDetailStoriesResponse {
        return apiService.getStorybyId(id)
    }

    suspend fun uploadStory(file: MultipartBody.Part, description: RequestBody): AddNewStoryResponse {
        return apiService.addStory(file, description)
    }

    suspend fun getStoriesWithLocation(): List<ListStoryItem> {
        val response = apiService.getStories(page = 1, size = 100, location = 1)
        return response.listStory
    }

}