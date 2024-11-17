package com.example.mystoryapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mystoryapplication.api.response.ListStoryItem
import com.example.mystoryapplication.databinding.ItemRowStoryBinding

class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickCallback {
        fun onItemClicked(story: ListStoryItem)
    }

    var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding, onItemClickCallback)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class StoryViewHolder(private val binding: ItemRowStoryBinding, private val onItemClickCallback: OnItemClickCallback?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            binding.tvItemName.text = story.name
            binding.tvItemDescription.text = story.description
            Glide.with(itemView)
                .load(story.photoUrl)
                .into(binding.ivStory)

            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(story)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}