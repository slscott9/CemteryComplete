package com.example.cemterycomplete.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.entities.Grave
import com.example.cemterycomplete.network.responses.CemeterySendResponse
import com.example.cemterycomplete.utils.NetworkCemetery
import com.example.cemterycomplete.utils.NetworkCemeteryContainer
import com.example.cemterycomplete.utils.Resource

/*
    We want this fake repo to pass to the view model in order to test the view model.
    This fake should simulate the real behavior of our real repository but not in the same way as the real repository.

    It should simulate the real repository in a way that we can properly test our view model.

    1. we know the real repository will make a network call and return a cemetery container(response)
    2. the real repository will return a list of cemetery object, cemetery object, and grave objects.
 */
class FakeCemeteryRepository: CemeteryRepository {

    var errorResponse = false
    val errorMessage = "error"
    val successMessage = "success"



    override suspend fun refreshCemeteryList(): Resource<String> {
            return Resource.success("error getting cemeteries" )

    }

    override suspend fun insertNetworkCemeteryList(cemeteryContainer: NetworkCemeteryContainer) {

    }

    override suspend fun sendNewCemeteriesToNetwork(cemeteryList: List<Cemetery>): CemeterySendResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getNewCemeteriesForNetwork(): List<Cemetery> {
        TODO("Not yet implemented")
    }

    override fun getAllCemeteries(): LiveData<List<Cemetery>> {

        val cemlist = listOf<Cemetery>(
            Cemetery(
                firstYear = "123",
                cemeteryName = "Thorsby cem",
                cemeteryLocation = "Thorsny",
                cemeteryState = "Al",
                cemeteryCounty = "Cjilton",
                cemeteryRowId = 1,
                spot = "S",
                section = "Sect",
                range = "R",
                township = "T"
            ))
        val cemeteryList = MutableLiveData(cemlist)

        return cemeteryList

    }

    override suspend fun insertCemetery(cemetery: Cemetery) {
        TODO("Not yet implemented")
    }

    override suspend fun insertGrave(grave: Grave) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGrave(graveRowId: Int) {
        TODO("Not yet implemented")
    }

    override fun getGraveWithRowId(graveRowId: Int): LiveData<Grave> {
        TODO("Not yet implemented")
    }

    override fun getCemeteryWithRowId(cemeteryRowId: Int): LiveData<Cemetery> {
        TODO("Not yet implemented")
    }

    override fun getGravesWithCemeteryId(cemeteryId: Int): LiveData<List<Grave>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMaxCemeteryRowId(): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun getMaxGraveRowId(): Int? {
        TODO("Not yet implemented")
    }
}