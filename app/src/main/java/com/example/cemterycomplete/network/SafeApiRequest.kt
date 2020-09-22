package com.example.cemterycomplete.network

import com.example.cemterycomplete.utils.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message")) //target the "message" json string from response from dad append it to message variable
                } catch (e: JSONException) {

                }
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }

}

/*
    This function can deal with type Any classes

    This functions receives a lambda names call of type suspend and returns a retrofit Response object of type T

    The function returns a T generic

    1. We invoke the lambda suspend function that the function receives and its return value Response object is set to response variable

        The Response class contains methods

            isSuccessful() which returns true if code() is in range of 200 - 300
            body() - the deserialized response body of a successful response - our json response from dad
            errorBody() - the raw response body of an unsuccessful response - the error json response from dad

    2. we check if the response object isSuccessful, if true return the response body() - the success json response from dad

            else -> get the error response body

                if its not null get append to message variable the json string
                else append nothing if its not json

            append what we have to message variable
            throw the ApiException with the message

            convert error object to

 */
