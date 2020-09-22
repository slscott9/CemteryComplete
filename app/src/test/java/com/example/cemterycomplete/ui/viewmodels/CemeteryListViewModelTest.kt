package com.example.cemterycomplete.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cemterycomplete.MainCoroutineRule
import com.example.cemterycomplete.data.FakeCemeteryRepository
import com.example.cemterycomplete.getOrAwaitValueTest
import com.example.cemterycomplete.utils.Status
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CemeteryListViewModelTest {
    private lateinit var viewModel: CemeteryListViewModel


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    //ViewModels has methods that run inside of coroutines, these use the main dispatcher. Inside of test we do not have access to this main dispatcher
    //In androidTest folder we have access to the main dispatcher because we use a real device. Unit tests do not have access so we make a rule




    @Before
    fun setup() {
        viewModel = CemeteryListViewModel(FakeCemeteryRepository())
    }


    @ExperimentalCoroutinesApi
    @Test
    fun networkSuccess() = runBlockingTest {



        viewModel.refreshCemeteryList()

        val response = viewModel.cemeteryResponse.getOrAwaitValueTest()
        val cemList = viewModel.cemeteryList.getOrAwaitValueTest()

        Truth.assertThat(response.status).isEqualTo(Status.SUCCESS )
        Truth.assertThat(cemList).isNotEmpty()
    }
}