package com.example.cemterycomplete.network

import com.example.cemterycomplete.network.responses.CemeterySendResponse
import com.example.cemterycomplete.utils.NetworkCemeteryContainer
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface CemeteryServiceApi {

    @GET("/cgi-bin/getCems.pl")
    suspend fun getCemeteriesFromNetwork(): Response<NetworkCemeteryContainer>


    @FormUrlEncoded
    @POST("/cgi-bin/addCems.pl")
    suspend fun sendNewCemeteryToNetwork(@Field("json") json: String): Response<CemeterySendResponse>
}