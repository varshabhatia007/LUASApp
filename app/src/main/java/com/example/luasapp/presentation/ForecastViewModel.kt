package com.example.luasapp.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luasapp.common.JsonApiException
import com.example.luasapp.common.NetworkState
import com.example.luasapp.common.UIState
import com.example.luasapp.model.DirectionInfo

import com.example.luasapp.model.StopInfo
import com.example.luasapp.repository.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: ForecastRepository
) : ViewModel() {

    private val _stopInfoState = MutableLiveData<UIState<StopInfo>>()
    val stopInfoState: LiveData<UIState<StopInfo>> = _stopInfoState

    @SuppressLint("NewApi")
    fun fetchTramsForecastInfo() = viewModelScope.launch {
        _stopInfoState.postValue(UIState.Loading)
        val stopName = getStopName()
        when (val state = repository.tramsForecastInfo(stop = stopName)) {
            is NetworkState.Success -> {
                setupBoundDirection(stopName, state.data)
                _stopInfoState.postValue(
                    UIState.Success(state.data)
                )
            }
            is NetworkState.Error -> {
                _stopInfoState.postValue(
                    when (state.error) {
                        is JsonApiException.NoDataInResponse -> UIState.Error(0)
                        else -> UIState.Error(null)
                    }
                )
            }
        }
    }

    private fun setupBoundDirection(stop: String, data: StopInfo) {
        if (stop.contentEquals("MAR", true)) {
            removeDirection(INBOUND, data.directionInfos)
        } else {
            removeDirection(OUTBOUND, data.directionInfos)
        }
    }

    private fun removeDirection(bound: String, items: MutableList<DirectionInfo>) {
        items.indexOfFirst { directionInfo ->
            directionInfo.name.equals(bound, true)
        }.also { index ->
            if (index != -1)
                items.removeAt(index)
        }
    }

    @SuppressLint("NewApi")
    private fun getStopName() = when {
        LocalTime.now().isAfter(LocalTime.MIDNIGHT) && LocalTime.now().isBefore(LocalTime.NOON) ->
            StopAbvEnum.MARLBOROUGH.abv
        else ->
            StopAbvEnum.STILLORGAN.abv
    }

    companion object {
        const val INBOUND = "inbound"
        const val OUTBOUND = "outbound"
    }
}

enum class StopAbvEnum(val abv: String) {
    MARLBOROUGH("MAR"),
    STILLORGAN("STI")
}