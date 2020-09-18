package com.example.cemterycomplete.data.remote

import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.network.responses.CemeterySendResponse

import com.example.cemterycomplete.utils.NetworkCemeteryContainer

interface CemeteryRemoteDataSource {


    suspend fun sendNewCemeteriesToNetwork(cemeteryList: List<Cemetery>) : CemeterySendResponse

    suspend fun getCemeteryListFromNetwork() : NetworkCemeteryContainer
}