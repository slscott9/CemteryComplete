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

    private val cemetery1 = NetworkCemetery(
        cemetery_id = "1",
        name = "Stuart",
        location = "Thorsby",
        state = "AL",
        county = "CHilton",
        T = "t",
        R = "R",
        spot = "s",
        first_year = "1234",
        S = "S"

    )
    private val cemetery2 = NetworkCemetery(
        cemetery_id = "2",
        name = "John",
        location = "Jemison",
        state = "AL",
        county = "CHilton",
        T = "t",
        R = "R",
        spot = "s",
        first_year = "1456",
        S = "S"
    )

    private val cemetery3 = Cemetery(
        cemeteryRowId = 1,
        cemeteryLocation = "local",
        cemeteryState = "Al",
        cemeteryCounty = "Chilton",
        cemeteryName = "Jemison cemetery",
        section = "Jemison section",
        firstYear = "1234",
        range = "Jemison range",
        spot = "Spot",
        township = "township"
    )

    /*
        1. get all cemteries from the network which will be remote data sources cemetery list
     */
    val remoteCemteryList = listOf<NetworkCemetery>(cemetery1, cemetery2)

    val localCemeteryList = listOf<Cemetery>(cemetery3)
    val nullLocalCemeteryList: List<Cemetery>? = null
    val nullRemoteCemeteryList: List<NetworkCemetery>? = null

    private lateinit var cemeteryLocalDataSource: CemeteryDataSource
    private lateinit var cemeteryRemoteDataSource : CemeteryRemoteDataSource
    private lateinit var cemeteryRepository: CemeteryRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule() //runs test synchronously

    @Before
    fun setup() {
        cemeteryLocalDataSource = FakeLocalDataSource(nullLocalCemeteryList)
        cemeteryRemoteDataSource = FakeRemoteDataSourceTest(nullRemoteCemeteryList)
        cemeteryRepository = CemeteryRepositoryImpl(cemeteryLocalDataSource, cemeteryRemoteDataSource)
    }

    @Test
    fun getCemsFromNetworkFailure() = runBlockingTest {

        val cemNetworkList = cemeteryRemoteDataSource.getCemeteryListFromNetwork()

        assertThat(cemNetworkList.isSuccessful).isEqualTo(0)
        assertThat(cemNetworkList.records).isNull()

    }

    @Test
    fun sendCemListToNetworkFailure() = runBlockingTest {

        val cemResponse = cemeteryRepository.sendNewCemeteriesToNetwork(localCemeteryList)

        assertThat(cemResponse.isSuccessful).isEqualTo(0)
        assertThat(cemResponse.cemetery).isNull()
        assertThat(cemResponse.message).isNotEmpty()
    }

    @Test
    fun getCemsFromNetworkSuccess() = runBlockingTest {
        cemeteryLocalDataSource = FakeLocalDataSource(localCemeteryList)
        cemeteryRemoteDataSource = FakeRemoteDataSourceTest(remoteCemteryList)
        cemeteryRepository = CemeteryRepositoryImpl(cemeteryLocalDataSource, cemeteryRemoteDataSource)

        val cemeteryResponse = cemeteryRepository.getCemeteryListFromNetwork()
        assertThat(cemeteryResponse.isSuccessful).isEqualTo(1)
        assertThat(cemeteryResponse.records).isNotNull()
    }

    @Test
    fun sendCemeteryListToNetworkSuccess()  = runBlockingTest{

        cemeteryLocalDataSource = FakeLocalDataSource(localCemeteryList)
        cemeteryRemoteDataSource = FakeRemoteDataSourceTest(remoteCemteryList)
        cemeteryRepository = CemeteryRepositoryImpl(cemeteryLocalDataSource, cemeteryRemoteDataSource)

        val cemeterySendResponse = cemeteryRepository.sendNewCemeteriesToNetwork(localCemeteryList)
        assertThat(cemeterySendResponse.isSuccessful).isEqualTo(1)
        assertThat(cemeterySendResponse.message).isNotEmpty()
        assertThat(cemeterySendResponse.cemetery).isNotNull()
    }
}