package com.example.mystoryapplication.pref

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mystoryapplication.api.response.ListStoryItem
import kotlinx.coroutines.flow.Flow

class StoryRepository(private val userRepository: UserRepository) {

    fun getStoryPagingData(): Flow<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { StoryPagingSource(userRepository) }
        ).flow
    }
}