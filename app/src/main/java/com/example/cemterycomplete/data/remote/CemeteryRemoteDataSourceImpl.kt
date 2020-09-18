package com.example.cemterycomplete.data.remote

import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.network.CemeteryServiceApi
import com.example.cemterycomplete.network.SafeApiRequest
import com.example.cemterycomplete.network.responses.CemeteryResponse
import com.example.cemterycomplete.network.responses.CemeterySendResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import timber.log.Timber
import javax.inject.Inject

class CemeteryRemoteDataSourceImpl @Inject constructor(
    private val cemeteryServiceApi: CemeteryServiceApi,
    private val moshi : Moshi
) : SafeApiRequest(), CemeteryRemoteDataSource {



    override suspend fun sendNewCemeteriesToNetwork(cemeteryList: List<Cemetery>) : CemeterySendResponse{
        Timber.i("send cems called from remote data source")

        val type = Types.newParameterizedType(List::class.java, Cemetery::class.java)
        val adapter = moshi.adapter<List<Cemetery>>(type)
        val cemsListJsonString = adapter.toJson(cemeteryList)
        Timber.i(cemsListJsonString)

        return  apiRequest { cemeteryServiceApi.sendNewCemeteryToNetwork(cemsListJsonString) }
    }

    override suspend fun getCemeteryListFromNetwork(): CemeteryResponse =  apiRequest { cemeteryServiceApi.getCemeteriesFromNetwork() }


}