package com.kevin.publicapis.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.kevin.publicapis.model.APiRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response


class PublicApisViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PublicApisViewModel
    private lateinit var mockRepository: APiRepository
    private val testDispatcher = StandardTestDispatcher()
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockRepository = mockk()
        viewModel = PublicApisViewModel(mockRepository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test live data value when error from repo`() {
        runBlocking {

            // arrange
            // Mock repository response
            coEvery { mockRepository.getEntries() } returns Response.error(
                404,
                "Not found".toResponseBody()
            )
            // Observe LiveData
            val observer = mockk<Observer<String>>(relaxed = true)
            viewModel.error.observeForever(observer)
//
            // Trigger action that changes LiveData
            viewModel.getEntries()
//
//            // Verify LiveData value
            coVerify { observer.onChanged("Some error") }
//
//            // Clean up
            viewModel.error.removeObserver(observer)
        }

    }

}