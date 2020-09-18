package com.example.cemterycomplete.data

import androidx.lifecycle.LiveData
import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.entities.Grave

class FakeTestRepository: CemeteryRepository {
    override suspend fun refreshCemeteryList() {
        TODO("Not yet implemented")
    }

    override suspend fun sendNewCemeteriesToNetwork(cemeteryList: List<Cemetery>) {
        TODO("Not yet implemented")
    }

    override suspend fun getNewCemeteriesForNetwork(): List<Cemetery> {
        TODO("Not yet implemented")
    }

    override fun getAllCemeteries(): LiveData<List<Cemetery>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertCemetery(cemetery: Cemetery) {
        TODO("Not yet implemented")
    }

    override suspend fun insertGrave(grave: Grave) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGrave(graveRowId: Int) {
        TODO("Not yet implemented")
    }

    override fun getGraveWithRowId(graveRowId: Int): LiveData<Grave> {
        TODO("Not yet implemented")
    }

    override fun getCemeteryWithRowId(cemeteryRowId: Int): LiveData<Cemetery> {
        TODO("Not yet implemented")
    }

    override fun getGravesWithCemeteryId(cemeteryId: Int): LiveData<List<Grave>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMaxCemeteryRowId(): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun getMaxGraveRowId(): Int? {
        TODO("Not yet implemented")
    }
}