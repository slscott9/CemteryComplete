package com.example.cemterycomplete.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.entities.Grave
import com.example.cemterycomplete.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//Instrumentation tests need Android framework to run and go in androidTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CemeteryDaoTest {

    private lateinit var database: CemeteryRoomDatabase
    private lateinit var dao: CemeteryDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule() //runs test synchronously

    @Before
    fun setup() { //in memory database new databases are not created locally
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CemeteryRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        dao = database.cemDao()
    }

    @After
    fun tearDown() {
        database.close() //close database every time so tests are not using the same database
    }

    @Test
    fun insert_cem_list_then_get_all_cems() = runBlockingTest{

        val cemeteryList = arrayOf(
            Cemetery(
                cemeteryRowId = 1,
                cemeteryLocation = "Thorsby",
                cemeteryState = "Al",
                cemeteryCounty = "Chilton",
                cemeteryName = "Thorsby cemetery",
                section = "Thorsby section",
                firstYear = "1234",
                range = "Thorsby range",
                spot = "Spot",
                township = "township"
            ),
            Cemetery(
                cemeteryRowId = 2,
                cemeteryLocation = "Thorsby",
                cemeteryState = "Al",
                cemeteryCounty = "Chilton",
                cemeteryName = "Thorsby cemetery",
                section = "Thorsby section",
                firstYear = "1234",
                range = "Thorsby range",
                spot = "Spot",
                township = "township"
            )
        )

        dao.insertAllCemsFromNetwork(*cemeteryList)
        val allCems = dao.getAllCemeteries()
        assertThat(allCems).isNotNull()
    }

    @Test
    fun insertCemetery() = runBlockingTest {

        val cemetery = Cemetery(
            cemeteryRowId = 2,
            cemeteryLocation = "Thorsby",
            cemeteryState = "Al",
            cemeteryCounty = "Chilton",
            cemeteryName = "Thorsby cemetery",
            section = "Thorsby section",
            firstYear = "1234",
            range = "Thorsby range",
            spot = "Spot",
            township = "township"
        )

        dao.insertCemetery(cemetery)
        val cemList = dao.getAllCemeteries().getOrAwaitValue()
        assertThat(cemList).contains(cemetery)
    }

    @Test
    fun getCemeteryWithId() = runBlockingTest{
        val cemetery = Cemetery(
            cemeteryRowId = 2,
            cemeteryLocation = "Thorsby",
            cemeteryState = "Al",
            cemeteryCounty = "Chilton",
            cemeteryName = "Thorsby cemetery",
            section = "Thorsby section",
            firstYear = "1234",
            range = "Thorsby range",
            spot = "Spot",
            township = "township"
        )
        dao.insertCemetery(cemetery)
        val cemeteryWithId = dao.getCemeteryWithId(2).getOrAwaitValue()
        assertThat(cemeteryWithId.cemeteryRowId).isEqualTo(2)
    }

    @Test
    fun insertGrave() = runBlockingTest{
        val grave = Grave(
            firstName = "Stuart",
            lastName = "Scott",
            birthDate = "08/27/1994",
            deathDate = "08/1/22",
            marriageYear = "02/21/19",
            cemeteryId = 1,
            comment = "new cemetery",
            graveNumber = "1",
            graveRowId = 1
        )

        dao.insertGrave(grave)
        val allGraves = dao.getGravesWithCemeteryId(1).getOrAwaitValue()
        assertThat(allGraves).contains(grave)

    }

    @Test
    fun deleteGrave() = runBlockingTest{
        val grave = Grave(
            firstName = "Stuart",
            lastName = "Scott",
            birthDate = "08/27/1994",
            deathDate = "08/1/22",
            marriageYear = "02/21/19",
            cemeteryId = 1,
            comment = "new cemetery",
            graveNumber = "1",
            graveRowId = 1
        )

        dao.insertGrave(grave)
        dao.deleteGrave(1)
        val allGrave = dao.getGraveWithRowid(1).getOrAwaitValue()
        assertThat(allGrave).isNull()
    }

    @Test
    fun getGravesWithCemeteryId() = runBlockingTest{
        for(i in 0 until 10){
            dao.insertGrave(
                Grave(
                    firstName = "Stuart",
                    lastName = "Scott",
                    birthDate = "08/27/1994",
                    deathDate = "08/1/22",
                    marriageYear = "02/21/19",
                    cemeteryId = 1,
                    comment = "new cemetery",
                    graveNumber = "1",
                    graveRowId = i
                )
            )
        }

        val allGraves = dao.getGravesWithCemeteryId(1).getOrAwaitValue()
        assertThat(allGraves.size).isEqualTo(10)
    }

    @Test
    fun getMaxCemeteryRowNum() = runBlockingTest{
        for(i in 0 until 10){
            dao.insertCemetery(
                Cemetery(
                    cemeteryRowId = i,
                    cemeteryLocation = "Thorsby",
                    cemeteryState = "Al",
                    cemeteryCounty = "Chilton",
                    cemeteryName = "Thorsby cemetery",
                    section = "Thorsby section",
                    firstYear = "1234",
                    range = "Thorsby range",
                    spot = "Spot",
                    township = "township"
                )
            )
        }

        val maxCemRowNum = dao.getMaxCemeteryRowNum()
        assertThat(maxCemRowNum).isEqualTo(9)
    }

    @Test
    fun getMaxGraveRowNum() = runBlockingTest {
        for(i in 0 until 10){
            dao.insertGrave(
                Grave(
                    firstName = "Stuart",
                    lastName = "Scott",
                    birthDate = "08/27/1994",
                    deathDate = "08/1/22",
                    marriageYear = "02/21/19",
                    cemeteryId = 1,
                    comment = "new cemetery",
                    graveNumber = "1",
                    graveRowId = i
                )
            )
        }

        val maxGraveRowNum = dao.getMaxGraveRowNum()
        assertThat(maxGraveRowNum).isEqualTo(9)
    }


    @Test
    fun getAllCemsForNetwork() = runBlockingTest{
        for(i in 0 until 10){
            dao.insertCemetery(
                Cemetery(
                    cemeteryRowId = i,
                    cemeteryLocation = "Thorsby",
                    cemeteryState = "Al",
                    cemeteryCounty = "Chilton",
                    cemeteryName = "Thorsby cemetery",
                    section = "Thorsby section",
                    firstYear = "1234",
                    range = "Thorsby range",
                    spot = "Spot",
                    township = "township"
                )
            )
        }

        val cemList = dao.getAllCemsForNetwork()
        assertThat(cemList.size).isEqualTo(10)
    }








}