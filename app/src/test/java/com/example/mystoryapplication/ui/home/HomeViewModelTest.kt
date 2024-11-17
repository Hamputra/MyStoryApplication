package com.example.mystoryapplication.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.mystoryapplication.DataDummy
import com.example.mystoryapplication.MainDispatcherRule
import com.example.mystoryapplication.api.response.ListStoryItem
import com.example.mystoryapplication.pref.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var storyRepository: StoryRepository

    @Before
    fun setUp() {
        storyRepository = mock(StoryRepository::class.java)
    }

    @Test
    fun `when Get Story Paging Data Should Not Null`() = runTest {
        val dummyStory = DataDummy.generateDummyStoryEntity()
        val data: PagingData<ListStoryItem> = PagingData.from(dummyStory)
        val expectedStory = flowOf(data)
        `when`(storyRepository.getStoryPagingData()).thenReturn(expectedStory)

        val homeViewModel = HomeViewModel(storyRepository)
        val actualStory: Flow<PagingData<ListStoryItem>> = homeViewModel.storyPagingData

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        actualStory.take(1).collect {
            differ.submitData(it)
            assertNotNull(differ.snapshot())
        }
    }

    @Test
    fun `when Get Story Paging Data Should Return The Right Size`() = runTest {
        val dummyStory = DataDummy.generateDummyStoryEntity()
        val data: PagingData<ListStoryItem> = PagingData.from(dummyStory)
        val expectedStory = flowOf(data)
        `when`(storyRepository.getStoryPagingData()).thenReturn(expectedStory)

        val homeViewModel = HomeViewModel(storyRepository)
        val actualStory: Flow<PagingData<ListStoryItem>> = homeViewModel.storyPagingData

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        actualStory.take(1).collect {
            differ.submitData(it)
            assertEquals(dummyStory.size, differ.snapshot().size)
        }
    }

    @Test
    fun `when Get Story Paging Data Should Return The First Item`() = runTest {
        val dummyStory = DataDummy.generateDummyStoryEntity()
        val data: PagingData<ListStoryItem> = PagingData.from(dummyStory)
        val expectedStory = flowOf(data)
        `when`(storyRepository.getStoryPagingData()).thenReturn(expectedStory)

        val homeViewModel = HomeViewModel(storyRepository)
        val actualStory: Flow<PagingData<ListStoryItem>> = homeViewModel.storyPagingData

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        actualStory.take(1).collect {
            differ.submitData(it)
            assertNotNull(differ.snapshot()[0])
            assertEquals(dummyStory[0], differ.snapshot()[0])
        }
    }

    @Test
    fun `when Get Story Paging Data Should Return Zero When No Data`() = runTest {
        val data: PagingData<ListStoryItem> = PagingData.empty()
        val expectedStory = flowOf(data)
        `when`(storyRepository.getStoryPagingData()).thenReturn(expectedStory)

        val homeViewModel = HomeViewModel(storyRepository)
        val actualStory: Flow<PagingData<ListStoryItem>> = homeViewModel.storyPagingData

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        actualStory.take(1).collect {
            differ.submitData(it)
            assertEquals(0, differ.snapshot().size)
        }
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}