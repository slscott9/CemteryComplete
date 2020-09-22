package com.example.cemterycomplete.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.local.CemeteryDataSource
import com.example.cemterycomplete.data.remote.CemeteryRemoteDataSource
import com.example.cemterycomplete.utils.NetworkCemetery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import org.apache.maven.model.Repository


@ExperimentalCoroutinesApi
class CemeteryRepositoryImplTest {



    private lateinit var cemeteryLocalDataSource: CemeteryDataSource
    private lateinit var cemeteryRemoteDataSource : CemeteryRemoteDataSource
    private lateinit var cemeteryRepository: CemeteryRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule() //runs test synchronously

    @Before
    fun setup() {

    }

    @Test
    fun getCemsFromNetworkFailure() = runBlockingTest {

        val cemNetworkList = cemeteryRemoteDataSource.getCemeteryListFromNetwork()

        assertThat(cemNetworkList.isSuccessful).isEqualTo(0)
        assertThat(cemNetworkList.records).isNull()

    }


}