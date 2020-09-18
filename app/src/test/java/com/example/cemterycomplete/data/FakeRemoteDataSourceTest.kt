package com.example.cemterycomplete.data

import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.remote.CemeteryRemoteDataSource
import com.example.cemterycomplete.network.responses.CemeteryResponse
import com.example.cemterycomplete.network.responses.CemeterySendResponse
import com.example.cemterycomplete.utils.NetworkCemeteryContainer

class FakeRemoteDataSourceTest(var cemeteryRemoteContainer: NetworkCemeteryContainer): CemeteryRemoteDataSource {


    override suspend fun sendNewCemeteriesToNetwork(cemeteryList: List<Cemetery>): CemeterySendResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getCemeteryListFromNetwork(): CemeteryResponse {
        return CemeteryResponse(
            isSuccessful = true,
            message = "good send",
            cemeteryNetworkCemeteryContainer = cemeteryRemoteContainer
        )
    }

}