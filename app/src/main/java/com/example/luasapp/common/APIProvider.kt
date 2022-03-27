package com.example.luasapp.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.ExecutionException

object APIProvider {
    suspend fun <T> performRequest(dispatcher: CoroutineDispatcher, networkCall: suspend () -> T): NetworkState<T> {
        return withContext(dispatcher) {
            try {
                NetworkState.Success(networkCall.invoke())
            } catch (throwable: Exception) {
                errorHandler(throwable)
            }
        }
    }

    private fun errorHandler(throwable: Throwable): NetworkState.Error {
        return when (throwable) {
            is HttpException -> NetworkState.Error(JsonApiException.ServerError(throwable.code()))
            is SocketTimeoutException -> NetworkState.Error(JsonApiException.ServerError(500))
            is ConnectException -> NetworkState.Error(JsonApiException.ServerError(400))
            is ExecutionException -> NetworkState.Error(JsonApiException.NoDataInResponse)
            else -> NetworkState.Error(JsonApiException.NoResponseReceived)
        }
    }
}