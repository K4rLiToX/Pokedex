package com.carlosdiestro.features.home.models

sealed interface UiResult<out T> {
    data object Loading : UiResult<Nothing>
    data class Failure(val exception: Throwable) : UiResult<Nothing>
    data class Success<out T>(val value: T) : UiResult<T>
}