package com.example.cemterycomplete.data

import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.local.CemeteryDataSource
import com.example.cemterycomplete.data.local.CemeteryLocalDataSourceImpl
import com.example.cemterycomplete.data.remote.CemeteryRemoteDataSource
import com.example.cemterycomplete.utils.NetworkCemetery
import com.example.cemterycomplete.utils.NetworkCemeteryContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

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
    val cemeterContainer = NetworkCemeteryContainer(remoteCemteryList)

    val localCemeteryList = listOf<Cemetery>(cemetery3)
    private lateinit var cemeteryLocalDataSource: CemeteryDataSource
    private lateinit var cemeteryRemoteDataSource : CemeteryRemoteDataSource

    private lateinit var cemeteryRepository: CemeteryRepository

    @Before
    fun setup() {
        cemeteryLocalDataSource = FakeLocalDataSource(localCemeteryList)
        cemeteryRemoteDataSource = FakeRemoteDataSourceTest(cemeterContainer)

        cemeteryRepository = CemeteryRepositoryImpl(cemeteryLocalDataSource, cemeteryRemoteDataSource)
    }

    @Test
    fun get_cemeteries_from_network() = runBlockingTest {

        cemeteryRepository.getcemet
    }
}