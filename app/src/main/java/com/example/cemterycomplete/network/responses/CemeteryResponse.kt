package com.example.cemterycomplete.network.responses

import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.utils.NetworkCemeteryContainer

data class CemeteryResponse(
    val isSuccessful: Int?,
    val message: String?,
    val cemeteryNetworkCemeteryContainer: NetworkCemeteryContainer?
) {
}

data class CemeterySendResponse(
    val isSuccessful: Int?,
    val message: String?,
    val cemetery: Cemetery?
)