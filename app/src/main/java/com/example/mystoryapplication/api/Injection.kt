package com.example.mystoryapplication.api

import android.content.Context
import com.example.mystoryapplication.pref.StoryRepository
import com.example.mystoryapplication.pref.UserPreferences
import com.example.mystoryapplication.pref.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val userPreferences = UserPreferences(context)
        val token = userPreferences.getSession().token
        val apiService = ApiConfig.getApiService(token)
        return UserRepository(apiService, userPreferences)
    }

    fun provideStoryRepository(context: Context): StoryRepository {
        val userRepository = provideRepository(context)
        return StoryRepository(userRepository)
    }
}