package com.example.mystoryapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mystoryapplication.HomeViewModelFactory
import com.example.mystoryapplication.R
import com.example.mystoryapplication.api.Injection
import com.example.mystoryapplication.api.response.ListStoryItem
import com.example.mystoryapplication.databinding.FragmentHomeBinding
import com.example.mystoryapplication.ui.home.DetailActivity.Companion.EXTRA_STORY_ID
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(Injection.provideStoryRepository(requireContext()))
    }

    private var isDataLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvStory.setHasFixedSize(true)

        val storyAdapter = StoryAdapter()
        storyAdapter.onItemClickCallback = object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(story: ListStoryItem) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(EXTRA_STORY_ID, story.id)
                startActivity(intent)
            }
        }
        binding.rvStory.adapter = storyAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                storyAdapter.retry()
            }
        )

        lifecycleScope.launch {
            viewModel.storyPagingData.collectLatest {
                storyAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            storyAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
            }
        }

        binding.rvStory.layoutManager = LinearLayoutManager(context)

        binding.fabAddStory.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_post)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}