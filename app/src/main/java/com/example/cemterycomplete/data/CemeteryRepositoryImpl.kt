package com.example.cemterycomplete.data

import androidx.lifecycle.LiveData
import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.entities.Grave
import com.example.cemterycomplete.data.local.CemeteryDataSource
import com.example.cemterycomplete.data.local.CemeteryLocalDataSourceImpl
import com.example.cemterycomplete.data.remote.CemeteryRemoteDataSource
import com.example.cemterycomplete.data.remote.CemeteryRemoteDataSourceImpl
import com.example.cemterycomplete.utils.asDatabaseModel
import java.lang.Exception
import javax.inject.Inject

class CemeteryRepositoryImpl @Inject constructor(
    private val cemeteryLocalDataSourceImpl: CemeteryDataSource,
    private val cemeteryRemoteDataSourceImpl: CemeteryRemoteDataSource
): CemeteryRepository {
    override suspend fun refreshCemeteryList() {
        try {
            val cemeteryResponse = cemeteryRemoteDataSourceImpl.getCemeteryListFromNetwork()

            cemeteryLocalDataSourceImpl.insertAllCemeteriesFromNetwork(cemeteryResponse.cemeteryNetworkCemeteryContainer!!)

        }catch (e: Exception){
            e.printStackTrace() //should catche the IOException
        }
    }

    override suspend fun sendNewCemeteriesToNetwork(cemeteryList: List<Cemetery>) {

    }

    override suspend fun getNewCemeteriesForNetwork(): List<Cemetery> {
        return cemeteryLocalDataSourceImpl.getNewCemeteriesForNetwork()
    }

    override fun getAllCemeteries(): LiveData<List<Cemetery>> {
        return cemeteryLocalDataSourceImpl.getAllCemeteries()
    }

    override suspend fun insertCemetery(cemetery: Cemetery) {
        cemeteryLocalDataSourceImpl.insertCemetery(cemetery)
    }

    override suspend fun insertGrave(grave: Grave) {
        cemeteryLocalDataSourceImpl.insertGrave(grave)
    }

    override suspend fun deleteGrave(graveRowId: Int) {
        cemeteryLocalDataSourceImpl.deleteGrave(graveRowId)
    }

    override fun getGraveWithRowId(graveRowId: Int): LiveData<Grave> {
        return cemeteryLocalDataSourceImpl.getGraveWithRowId(graveRowId)
    }

    override fun getCemeteryWithRowId(cemeteryRowId: Int): LiveData<Cemetery> {
        return cemeteryLocalDataSourceImpl.getCemeteryWithRowId(cemeteryRowId)
    }

    override fun getGravesWithCemeteryId(cemeteryId: Int): LiveData<List<Grave>> {
        return cemeteryLocalDataSourceImpl.getGravesWithCemeteryId(cemeteryId)
    }

    override suspend fun getMaxCemeteryRowId(): Int? {
        return cemeteryLocalDataSourceImpl.getMaxCemeteryRowId()
    }

    override suspend fun getMaxGraveRowId(): Int? {
        return cemeteryLocalDataSourceImpl.getMaxGraveRowId()
    }
}