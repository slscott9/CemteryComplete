package com.example.cemterycomplete.data

import androidx.lifecycle.LiveData
import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.entities.Grave
import com.example.cemterycomplete.network.responses.CemeterySendResponse
import com.example.cemterycomplete.utils.NetworkCemeteryContainer
import com.example.cemterycomplete.utils.Resource

interface CemeteryRepository {

    suspend fun refreshCemeteryList() : Resource<String>

    suspend fun insertNetworkCemeteryList(cemeteryContainer: NetworkCemeteryContainer)

    suspend fun sendNewCemeteriesToNetwork(cemeteryList: List<Cemetery>): CemeterySendResponse


    suspend fun getNewCemeteriesForNetwork(): List<Cemetery>

    fun getAllCemeteries(): LiveData<List<Cemetery>>

    suspend fun insertCemetery(cemetery: Cemetery)

    suspend fun insertGrave(grave: Grave)

    suspend fun deleteGrave(graveRowId: Int)

    fun getGraveWithRowId(graveRowId: Int): LiveData<Grave>

    fun getCemeteryWithRowId(cemeteryRowId: Int): LiveData<Cemetery>

    fun getGravesWithCemeteryId(cemeteryId: Int): LiveData<List<Grave>>

    suspend fun getMaxCemeteryRowId(): Int?

    suspend fun getMaxGraveRowId(): Int?
}