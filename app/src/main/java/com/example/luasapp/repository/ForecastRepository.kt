package com.example.luasapp.repository

import com.example.luasapp.common.NetworkState
import com.example.luasapp.model.StopInfo

interface ForecastRepository {
    suspend fun tramsForecastInfo(stop: String): NetworkState<StopInfo>
}