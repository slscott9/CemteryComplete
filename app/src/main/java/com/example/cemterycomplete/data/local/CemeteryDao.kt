package com.example.cemterycomplete.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.entities.Grave

@Dao
interface CemeteryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCemsFromNetwork(vararg cemetery: Cemetery)//tested

    @Insert
    suspend fun insertCemetery(cemetery: Cemetery) // tested

    @Query("select * from complete_cemetery_table") //tested
    fun getAllCemeteries(): LiveData<List<Cemetery>>

    @Query("select * from complete_cemetery_table where cemeteryRowId= :cemeteryId") //tested
    fun getCemeteryWithId(cemeteryId: Int): LiveData<Cemetery>

    @Insert(onConflict = OnConflictStrategy.REPLACE) //tested
    suspend fun insertGrave(grave: Grave)

    @Query("delete from complete_graves_table where graveRowId= :rowId") //tested
    suspend fun deleteGrave(rowId: Int)

    @Query("select * from complete_graves_table where graveRowId=  :rowId") //tested
    fun getGraveWithRowid(rowId: Int): LiveData<Grave>


    @Query("select * from complete_graves_table where cemeteryId= :cemeteryId") //tested
    fun getGravesWithCemeteryId(cemeteryId: Int) : LiveData<List<Grave>>


    @Query("select max(cemeteryRowId) from complete_cemetery_table") //tested
    suspend fun getMaxCemeteryRowNum(): Int?

    @Query("select max(graveRowId) from complete_graves_table") //tested
    suspend fun getMaxGraveRowNum(): Int?

    @Query("select * from complete_cemetery_table")
    suspend fun getAllCemsForNetwork() : List<Cemetery>
}