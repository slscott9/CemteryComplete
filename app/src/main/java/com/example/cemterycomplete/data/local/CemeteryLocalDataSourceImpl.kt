package com.example.cemterycomplete.data.local

import androidx.lifecycle.LiveData
import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.entities.Grave
import com.example.cemterycomplete.utils.NetworkCemeteryContainer
import com.example.cemterycomplete.utils.asDatabaseModel
import javax.inject.Inject

class CemeteryLocalDataSourceImpl @Inject constructor(private val cemeteryDao: CemeteryDao) :
    CemeteryDataSource {

    override suspend fun insertAllCemeteriesFromNetwork(cemeteryList : NetworkCemeteryContainer) {
        cemeteryDao.insertAllCemsFromNetwork(*cemeteryList.asDatabaseModel())
    }


    override suspend fun getNewCemeteriesForNetwork(): List<Cemetery> {
        return cemeteryDao.getAllCemsForNetwork()
    }


    override fun getAllCemeteries(): LiveData<List<Cemetery>> {
        return cemeteryDao.getAllCemeteries()
    }

    override suspend fun insertCemetery(cemetery: Cemetery) {
        cemeteryDao.insertCemetery(cemetery)
    }

    override suspend fun insertGrave(grave: Grave) {
        cemeteryDao.insertGrave(grave)
    }

    override suspend fun deleteGrave(graveRowId: Int) {
        cemeteryDao.deleteGrave(graveRowId)
    }


    override fun getGraveWithRowId(graveRowId: Int): LiveData<Grave> {
        return cemeteryDao.getGraveWithRowid(graveRowId)
    }

    override fun getCemeteryWithRowId(cemeteryRowId: Int): LiveData<Cemetery> {
        return cemeteryDao.getCemeteryWithId(cemeteryRowId)
    }

    override fun getGravesWithCemeteryId(cemeteryId: Int): LiveData<List<Grave>> {
        return cemeteryDao.getGravesWithCemeteryId(cemeteryId)
    }

    override suspend fun getMaxCemeteryRowId(): Int? {
        return cemeteryDao.getMaxCemeteryRowNum()
    }

    override suspend fun getMaxGraveRowId(): Int? {
        return cemeteryDao.getMaxGraveRowNum()
    }


}