package com.example.cemterycomplete.data

import androidx.lifecycle.LiveData
import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.entities.Grave
import com.example.cemterycomplete.data.local.CemeteryDataSource
import com.example.cemterycomplete.data.local.CemeteryLocalDataSourceImpl
import com.example.cemterycomplete.data.remote.CemeteryRemoteDataSource
import com.example.cemterycomplete.data.remote.CemeteryRemoteDataSourceImpl
import com.example.cemterycomplete.utils.asDatabaseModel
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

/*
    The repo injects interfaces which are defined in the Di module as the LocalDataSourceImpl and remoteDataSourceImpl.

    Injecting the interfaces allows for testing where a repo can be constructed with fake local and remote classes.

    We can define our own test interface methods now.
 */
class CemeteryRepositoryImpl @Inject constructor(
    private val cemeteryLocalDataSourceImpl: CemeteryDataSource,
    private val cemeteryRemoteDataSourceImpl: CemeteryRemoteDataSource
): CemeteryRepository {

    //break this up as just sending the view model a Response
    //If the reponse is successful insert it into the database using a repostiory insert cemetery list into database method
    //in the worker do the same
    //This makes our function more testable
    override suspend fun refreshCemeteryList() {
        try {
            val cemeteryResponse = cemeteryRemoteDataSourceImpl.getCemeteryListFromNetwork()
            Timber.i(cemeteryResponse.isSuccessful.toString())
            if(cemeteryResponse.isSuccessful == 1){
                cemeteryLocalDataSourceImpl.insertAllCemeteriesFromNetwork(cemeteryResponse)
            }

        }catch (e: Exception){
            e.printStackTrace() //should catche the IOException

        }
    }

    override suspend fun sendNewCemeteriesToNetwork(cemeteryList: List<Cemetery>) {
        val cemResponse = cemeteryRemoteDataSourceImpl.sendNewCemeteriesToNetwork(cemeteryList)
        if(!cemResponse.message.isNullOrEmpty()){
            Timber.i(cemResponse.message)
            Timber.i(cemResponse.isSuccessful.toString())
        }
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