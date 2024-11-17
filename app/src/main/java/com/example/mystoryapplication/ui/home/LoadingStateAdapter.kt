package com.example.mystoryapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mystoryapplication.databinding.ItemLoadingBinding

class LoadingStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadingStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadingStateViewHolder {
        val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding, retry)
    }
}

class LoadingStateViewHolder(private val binding: ItemLoadingBinding, retry: () -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvError.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.tvError.isVisible = loadState is LoadState.Error
        binding.btnRetry.isVisible = loadState is LoadState.Error
    }
}