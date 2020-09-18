package com.example.cemterycomplete.data.remote

import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.network.responses.CemeteryResponse
import com.example.cemterycomplete.network.responses.CemeterySendResponse

interface CemeteryRemoteDataSource {


    suspend fun sendNewCemeteriesToNetwork(cemeteryList: List<Cemetery>) : CemeterySendResponse

    suspend fun getCemeteryListFromNetwork() : CemeteryResponse
}