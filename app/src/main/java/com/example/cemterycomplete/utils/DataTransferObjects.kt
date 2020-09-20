package com.example.cemterycomplete.utils

import com.example.cemterycomplete.data.entities.Cemetery
import com.squareup.moshi.JsonClass

/*
    Has to match what you get back from the GET call
    We get back a isSuccessful integer and a json array of NetworkCemtery objects.

    Use NetworkCemetery to deserialize the json array into usable objects. The Get request returns this and based off of isSuccessful or records.isEmpty
    We can insert into database
 */

@JsonClass(generateAdapter = true)
data class NetworkCemeteryContainer(val records: List<NetworkCemetery>?, val isSuccessful: Int)



@JsonClass(generateAdapter = true)
data class NetworkCemetery(
    val cemetery_id: String,
    val name: String,
    val location: String,
    val state: String,
    val county: String,
    val T: String,
    val R: String,
    val spot: String,
    val first_year: String,
    val S: String
)





//Convert our Network objects into database models
fun NetworkCemeteryContainer.asDatabaseModel(): Array<Cemetery> {
    return records!!.map {
        Cemetery(
            cemeteryRowId = it.cemetery_id.toInt(),
            cemeteryName = it.name,
            cemeteryLocation = it.location,
            cemeteryState = it.state,
            cemeteryCounty = it.county,
            township = it.T,
            range = it.R,
            spot = it.spot,
            firstYear = it.first_year,
            section = it.S
        )
    }.toTypedArray()
}


