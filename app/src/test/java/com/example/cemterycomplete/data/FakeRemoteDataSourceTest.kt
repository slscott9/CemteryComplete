package com.example.cemterycomplete.data

import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.remote.CemeteryRemoteDataSource
import com.example.cemterycomplete.network.responses.CemeterySendResponse
import com.example.cemterycomplete.utils.NetworkCemetery
import com.example.cemterycomplete.utils.NetworkCemeteryContainer

class FakeRemoteDataSourceTest(var cemeteryRemoteContainer: List<NetworkCemetery>): CemeteryRemoteDataSource {


    override suspend fun sendNewCemeteriesToNetwork(cemeteryList: List<Cemetery>): CemeterySendResponse{
        cemeteryRemoteContainer.let {
            return CemeterySendResponse(1, message = "good send",

                Cemetery(
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
            )
        }
    }

    override suspend fun getCemeteryListFromNetwork(): NetworkCemeteryContainer {
        return NetworkCemeteryContainer(
            isSuccessful = 1,
            records = cemeteryRemoteContainer
        )
    }

}