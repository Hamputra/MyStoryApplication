package com.example.mystoryapplication.api.response

data class GetAllStoriesResponse(
    val listStory: List<ListStoryItem>,
    val message: String,
    val error: Boolean
)

data class ListStoryItem(
    val id: String,
    val name: String,
    val photoUrl: String,
    val createdAt: String,
    val description: String,
    val lon: Double? = null,
    val lat: Double? = null
)