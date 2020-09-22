package com.example.cemterycomplete.data

import androidx.lifecycle.LiveData
import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.entities.Grave
import com.example.cemterycomplete.data.local.CemeteryDataSource
import com.example.cemterycomplete.data.local.CemeteryLocalDataSourceImpl
import com.example.cemterycomplete.data.remote.CemeteryRemoteDataSource
import com.example.cemterycomplete.data.remote.CemeteryRemoteDataSourceImpl
import com.example.cemterycomplete.network.responses.CemeterySendResponse
import com.example.cemterycomplete.utils.ApiException
import com.example.cemterycomplete.utils.NetworkCemeteryContainer
import com.example.cemterycomplete.utils.Resource
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
    override suspend fun refreshCemeteryList() : Resource<String>{
            return try{
                val cemeteryListResponse = cemeteryRemoteDataSourceImpl.getCemeteryListFromNetwork()
                cemeteryLocalDataSourceImpl.insertAllCemeteriesFromNetwork(cemeteryListResponse)
                Timber.i("Successfullly refreshed ")

                Resource.success("Successfully refreshed cemetery list")

            }catch (e: Exception){
                e.printStackTrace()
                Resource.error("Unknown error", e.message)
            }catch (apiException: ApiException){
                Resource.error("Check Network connection", apiException.message)
            }
    }

    override suspend fun insertNetworkCemeteryList(cemeteryContainer: NetworkCemeteryContainer) {
        cemeteryLocalDataSourceImpl.insertAllCemeteriesFromNetwork(cemeteryContainer)
    }

    override suspend fun sendNewCemeteriesToNetwork(cemeteryList: List<Cemetery>) : CemeterySendResponse{
        return  cemeteryRemoteDataSourceImpl.sendNewCemeteriesToNetwork(cemeteryList)

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