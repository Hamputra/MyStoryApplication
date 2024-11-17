package com.example.mystoryapplication.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mystoryapplication.DetailViewModelFactory
import com.example.mystoryapplication.api.Injection
import com.example.mystoryapplication.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_STORY_ID = "extra_story_id"
    }

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory(Injection.provideRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storyId = intent.getStringExtra(EXTRA_STORY_ID)
        if (storyId != null) {
            viewModel.fetchDetailStory(storyId)
        }

        viewModel.detailStory.observe(this) { detailStory ->
            binding.tvDetailName.text = detailStory.story.name
            binding.tvDetailDescription.text = detailStory.story.description
            Glide.with(this)
                .load(detailStory.story.photoUrl)
                .into(binding.ivDetailPhoto)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}