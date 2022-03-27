package com.example.luasapp.repository

import com.example.luasapp.common.APIProvider.performRequest
import com.example.luasapp.common.LUASForecasting
import com.example.luasapp.common.NetworkState
import com.example.luasapp.model.StopInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ForecastRepositoryImpl(
    private val apiService: LUASForecasting,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ForecastRepository {

    override suspend fun tramsForecastInfo(stop: String): NetworkState<StopInfo> {
        return performRequest(ioDispatcher) {
            apiService.tramsForecast(stopName = stop)
        }
    }
}