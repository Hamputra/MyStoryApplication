package com.example.mystoryapplication

import com.example.mystoryapplication.api.response.ListStoryItem

object DataDummy {
    fun generateDummyStoryEntity(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                "story-FvU4u0Vp2S3PMsFg",
                "https://story-api.dicoding.dev/images/stories/photos-1686147024137_MmqkQpKY.jpg",
                "2023-06-07T08:17:04.137Z",
                "Dimas",
                "Lorem Ipsum",
                -6.914744,
                107.60981
            )
            items.add(story)
        }
        return items
    }
}