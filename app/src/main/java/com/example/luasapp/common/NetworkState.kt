package com.example.luasapp.common

sealed class NetworkState<out T> {
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Error(val error: JsonApiException) : NetworkState<Nothing>()
}
