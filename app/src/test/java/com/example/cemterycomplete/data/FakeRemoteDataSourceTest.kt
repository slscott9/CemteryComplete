package com.example.cemterycomplete.data

import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.remote.CemeteryRemoteDataSource
import com.example.cemterycomplete.network.responses.CemeterySendResponse
import com.example.cemterycomplete.utils.NetworkCemetery
import com.example.cemterycomplete.utils.NetworkCemeteryContainer

class FakeRemoteDataSourceTest(var cemeteryRemoteContainer: List<NetworkCemetery>): CemeteryRemoteDataSource {


    override suspend fun sendNewCemeteriesToNetwork(cemeteryList: List<Cemetery>): CemeterySendResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getCemeteryListFromNetwork(): NetworkCemeteryContainer {
        return NetworkCemeteryContainer(
            isSuccessful = 1,
            records = cemeteryRemoteContainer
        )
    }

}