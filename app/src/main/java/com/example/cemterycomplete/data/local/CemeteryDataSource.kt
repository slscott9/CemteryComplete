package com.example.cemterycomplete.data.local

import androidx.lifecycle.LiveData
import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.entities.Grave
import com.example.cemterycomplete.utils.NetworkCemeteryContainer

interface CemeteryDataSource {


    suspend fun insertAllCemeteriesFromNetwork(  cemeteryList: NetworkCemeteryContainer)

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