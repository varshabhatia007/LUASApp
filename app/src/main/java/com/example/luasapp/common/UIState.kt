package com.example.luasapp.common

import androidx.annotation.StringRes

sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(@StringRes val errorResourceId: Int? = null) : UIState<Nothing>()
}
